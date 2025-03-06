package com.westonline.socialplatform.mapper;

import com.westonline.socialplatform.pojo.Review;
import com.westonline.socialplatform.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
@Mapper
public interface ReviewMapper {

    List<Review> selectByArticleId(int articleId);

    ArrayList<Review> selectByReviewId(int domComment);

    User selectUserByReviewId(int reviewId);

    int insertReview(Review review);

    int insertReviewUser(Long userId, int reviewId);

    int insertContain(int articleId, int reviewId);

    void insertSubComment(int domReview, int subReview);
}
