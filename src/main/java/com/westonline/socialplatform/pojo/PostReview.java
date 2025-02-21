package com.westonline.socialplatform.pojo;

public class PostReview {
    Review review;
    int articleId;
    int userId;

    @Override
    public String toString() {
        return "PostReview{" +
                "review=" + review +
                ", articleId=" + articleId +
                ", userId=" + userId +
                '}';
    }

    public PostReview() {
    }

    public PostReview(Review review, int articleId, int userId) {
        this.review = review;
        this.articleId = articleId;
        this.userId = userId;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
