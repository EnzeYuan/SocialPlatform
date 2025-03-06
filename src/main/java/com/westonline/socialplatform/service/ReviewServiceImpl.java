package com.westonline.socialplatform.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.westonline.socialplatform.mapper.ReviewMapper;
import com.westonline.socialplatform.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewMapper reviewMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    //评论区
    @Override
    public Map<ReviewTerm, ArrayList<ReviewTerm>> getReviews(int articleId) throws JsonProcessingException {
        //todo:从redis中获取
        String reviewsFromRedis = stringRedisTemplate.opsForValue().get("reviewsFromRedis:" + articleId);
        if (reviewsFromRedis != null) {
            // 如果命中Redis缓存，反序列化并返回
            return deserializeReviews(reviewsFromRedis);
        }

        //如果未命中
            List<Review> reviews = reviewMapper.selectByArticleId(articleId);
            Map<ReviewTerm, ArrayList<ReviewTerm>> reviewMap = new HashMap<>();

            for (Review review : reviews) {
                //把review转成reviewTerm
                ReviewTerm reviewTerm = extracted(review);

                //子评论
                ArrayList<Review> subReviews = reviewMapper.selectByReviewId(review.getReviewId());

                ArrayList<ReviewTerm> subReviewTerms = new ArrayList<>();
                if(subReviews!=null){
                    for (Review subReview : subReviews) {

                        ReviewTerm subReviewTerm = extracted(subReview);
                        subReviewTerms.add(subReviewTerm);
                    }
                    reviewMap.put(reviewTerm, subReviewTerms);
                }
            }
            //todo:并添加到redis
            String serializedReviews = objectMapper.writeValueAsString(reviewMap);
            stringRedisTemplate.opsForValue().set("reviewsFromRedis", serializedReviews);
            stringRedisTemplate.expire("reviewsFromRedis",60, TimeUnit.SECONDS);

        return reviewMap;
    }

    //写主评论
    @Override
    public int postReview(PostReview postReview)  {
        Review review = postReview.getReview();
        Long userId = postReview.getUserId();
        int articleId = postReview.getArticleId();
        System.out.println(review);
        reviewMapper.insertReview(review);
        int reviewId = review.getReviewId();
        reviewMapper.insertReviewUser(userId,reviewId);
        reviewMapper.insertContain(articleId,reviewId);
        return reviewId;
    }

    @Override
    public int postSubReview(PostSubReview postSubReview) {
        Review review = postSubReview.getReview();
        Long userId = postSubReview.getUserId();
        int domReview = postSubReview.getDomReview();
        reviewMapper.insertReview(review);
        int subReview = review.getReviewId();
        reviewMapper.insertReviewUser(userId,subReview);
        reviewMapper.insertSubComment(domReview,subReview);
        return subReview;
    }

    private ReviewTerm extracted(Review review) {
        if(review.getReviewId()!=-1){
            User user = reviewMapper.selectUserByReviewId(review.getReviewId());
            ReviewTerm reviewTerm = new ReviewTerm();
            reviewTerm.setReviewId(review.getReviewId());
            reviewTerm.setUserName(user.getUserName());
            reviewTerm.setImage(user.getImage());
            reviewTerm.setReviewContent(review.getReviewContent());
            return reviewTerm;
        }
        return null;

    }

    //用来反序列化
    private Map<ReviewTerm, ArrayList<ReviewTerm>> deserializeReviews(String serializedReviews) {
        try {
            return objectMapper.readValue(serializedReviews, new TypeReference<>() {});
        } catch (Exception e) {
            return new HashMap<>();
        }
    }



}
