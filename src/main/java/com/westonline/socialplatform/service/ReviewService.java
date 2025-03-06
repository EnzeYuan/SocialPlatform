package com.westonline.socialplatform.service;

import com.westonline.socialplatform.pojo.PostReview;
import com.westonline.socialplatform.pojo.PostSubReview;
import com.westonline.socialplatform.pojo.ReviewTerm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public interface ReviewService {
    //评论区
    Map<ReviewTerm, ArrayList<ReviewTerm>> getReviews(int articleId) throws IOException;
    //写主评论
    int postReview(PostReview postReview) throws IOException;

    //写子评论
    int postSubReview(PostSubReview postSubReview) throws IOException;

}
