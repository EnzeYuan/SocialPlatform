package com.westonline.socialplatform.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.westonline.socialplatform.mapper.ArticleMapper;
import com.westonline.socialplatform.mapper.ReviewMapper;
import com.westonline.socialplatform.mapper.UserMapper;
import com.westonline.socialplatform.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author 袁同学
 */
@Service
public class platformServiceImpl implements platformService{
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static final String LEADERBOARD_KEY = "Top";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    PasswordEncoder passwordEncoder;



    //时间排序
    @Override
    public ArrayList<LatestTerm> getLatestTerm() {
        List<Article> articles = articleMapper.selectAllArticle();
        return getLatestTerms(articles);
    }


    //榜单(redis)
    @Override
    public ArrayList<TopTerm> getTopTerm() throws IOException {
        Set<String> strings = stringRedisTemplate.opsForZSet().reverseRange(LEADERBOARD_KEY, 0, -1);
        ArrayList<TopTerm> topTerms = new ArrayList<>();
        if (strings != null) {
            for (String jsonString : strings) {
                TopTerm topTerm = OBJECT_MAPPER.readValue(jsonString, TopTerm.class);
                topTerms.add(topTerm);
            }
        }
        return topTerms;
    }


    //榜单详情
    @Override
    public ArrayList<LatestTerm> getLatestTermOrdered() {
        List<Article> articles = articleMapper.selectAllArticleOrdered();
        return getLatestTerms(articles);
    }


    //个人主页
    @Override
    public User getUser(int userId) throws IOException {
        User user = userMapper.selectOne(userId);
        user.setPassword(null);
        return user;
    }
    //获取喜欢的文章，封装成LatestTerm
    @Override
    public ArrayList<LatestTerm> getLikedLatestTerm(int userId) throws IOException {
        List<Article> articles = userMapper.selectLikes(userId);
        return getLatestTerms(articles);
    }
    //获取写的文章，封装成LatestTerm
    @Override
    public ArrayList<LatestTerm> getWrittenLatestTerm(int userId) throws IOException {
        List<Article> articles = userMapper.selectWritten(userId);
        return getLatestTerms(articles);
    }

    //文章详情页
    @Override
    public DetailTerm getDetailTerm(int articleId) throws IOException {
        Article article = articleMapper.selectArticleById(articleId);
        String articleName = article.getArticleName();
        String writerName = userMapper.selectOneUser(articleMapper.getAuthor(articleId));
        String body = article.getBody();
        int likes = articleMapper.getLikes(articleId);
        Integer numOfViews = article.getNumOfViews();
        Timestamp timestamp = article.getReleaseTime();
        return new DetailTerm(articleId,articleName,writerName,body,likes,numOfViews,timestamp);
    }



    //todo:评论区
    @Override
    public Map<ReviewTerm, ArrayList<ReviewTerm>> getReviews(int articleId) throws IOException {
        if(reviewMapper.selectByArticleId(articleId)!=null){
            List<Review> reviews = reviewMapper.selectByArticleId(articleId);

            Map<ReviewTerm, ArrayList<ReviewTerm>> reviewMap = new HashMap<>();
            for (Review review : reviews) {
                //todo:把review转成reviewTerm
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
            return reviewMap;
        }
        return null;
    }


    //todo: 写文章("/write")
    @Transactional
    @Override
    public int insertArticle(Article article,int userId) throws IOException {

        int i =articleMapper.insertArticle(article);
        if(i>0){
            int articleId = article.getArticleId();
            int i1 = articleMapper.insertWriting(articleId, userId);
            insetToRedis(article.getArticleId());
            if(i1>0){
                return i+i1;
            }
        }
        return i;


    }

    //添加到redis
    @Override
    public void insetToRedis(int articleId) throws JsonProcessingException {
        TopTerm topTerm = new TopTerm(articleId,articleMapper.selectArticleById(articleId).getArticleName(),0);
        String json = OBJECT_MAPPER.writeValueAsString(topTerm);
        stringRedisTemplate.opsForZSet().add(LEADERBOARD_KEY,json,topTerm.getScore());
    }

//    //登录
//    @Override
//    public ArrayList<String> getPassword(String userName) throws IOException {
//        ArrayList<String> info = new ArrayList<>();
//        info.add(userMapper.getPassword(userName));
//        info.add(userMapper.getUserId(userName));
//        return info;
//    }


    //注册
    @Transactional
    @Override
    public String insertUser(String userName,String password, MultipartFile image) throws Exception {
        byte[] bytes;
        if (!image.isEmpty()) {
            bytes = image.getBytes();
        }else {
            bytes = new byte[0];
        }
        User user = new User();
        user.setUserName(userName);
        user.setPassword(passwordEncoder.encode(password));
        user.setImage(image.getBytes());

        if (userMapper.selectUserByUserName(userName)!=null) {
            throw new Exception("Username already exists!");
        }
        int i = userMapper.insertUser(user);
        //返回userId
        return String.valueOf(i);
    }

    //点赞
    @Transactional
    @Override
    public Integer putLikes(int articleId, int userId) throws IOException {
        return articleMapper.putToLike(articleId, userId);
    }

    //更新浏览量
    @Override
    public void putViews(Integer articleId) throws IOException {
        //article表要更新
        articleMapper.updateViews(articleId);

        //redis更新
        Set<String> jsonSet = stringRedisTemplate.opsForZSet().reverseRange(LEADERBOARD_KEY, 0, -1);
        //找到修改的对象
        TopTerm aim=null;
        if (jsonSet != null) {
            for (String jsonString : jsonSet) {
                TopTerm topTerm = OBJECT_MAPPER.readValue(jsonString, TopTerm.class);
                if (topTerm.getArticleId()==(articleId)) {
                    aim = topTerm;
                    break;
                }
            }
        }
        if (aim != null) {
            String aim0 = OBJECT_MAPPER.writeValueAsString(aim);
            aim.setScore(aim.getScore()+1);
            String aimJson = OBJECT_MAPPER.writeValueAsString(aim);
            //删除原来的
            stringRedisTemplate.opsForZSet().remove(LEADERBOARD_KEY,aim0);
            //添加新的
            stringRedisTemplate.opsForZSet().add(LEADERBOARD_KEY,aimJson,aim.getScore());
        }
    }



    //写主评论
    @Override
    public int postReview(PostReview postReview) throws IOException {
        Review review = postReview.getReview();
        int userId = postReview.getUserId();
        int articleId = postReview.getArticleId();
        System.out.println(review);
        reviewMapper.insertReview(review);
        int reviewId = review.getReviewId();
        reviewMapper.insertReviewUser(userId,reviewId);
        reviewMapper.insertContain(articleId,reviewId);
        return reviewId;
    }

    @Override
    public int postSubReview(PostSubReview postSubReview) throws IOException {
        Review review = postSubReview.getReview();
        int userId = postSubReview.getUserId();
        int domReview = postSubReview.getDomReview();
        reviewMapper.insertReview(review);
        int subReviewId = review.getReviewId();
        reviewMapper.insertReviewUser(userId,subReviewId);
        reviewMapper.insertSubComment(domReview,subReviewId);
        return subReviewId;
    }

    @Override
    public int updateUserInfo(String userName, String password, MultipartFile image,int userId) throws IOException {
        byte[] bytes = image.getBytes();
        return userMapper.updateUser(userName,password,bytes,userId);
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


    //Article转换成LatestTerm
    private ArrayList<LatestTerm> getLatestTerms(List<Article> articles) {
        ArrayList<LatestTerm> latestTerms = new ArrayList<>();
        for (Article article : articles) {
            int articleId = article.getArticleId();
            String articleName = article.getArticleName();
            String writerName = userMapper.selectOneUser(articleMapper.getAuthor(articleId));
            String body = article.getBody();
            int likes = articleMapper.getLikes(articleId);
            Integer numOfViews = article.getNumOfViews();
            latestTerms.add(new LatestTerm(articleId, articleName, writerName, body, likes, numOfViews));
        }
        return latestTerms;
    }








}
