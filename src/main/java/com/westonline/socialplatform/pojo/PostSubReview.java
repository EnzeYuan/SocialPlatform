package com.westonline.socialplatform.pojo;

public class PostSubReview {
    Review review;
    Long userId;
    int domReview;

    @Override
    public String toString() {
        return "PostSubReview{" +
                "review=" + review +
                ", userId=" + userId +
                ", domReview=" + domReview +
                '}';
    }

    public PostSubReview() {
    }

    public PostSubReview(Review review, Long userId, int domReview) {
        this.review = review;
        this.userId = userId;
        this.domReview = domReview;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getDomReview() {
        return domReview;
    }

    public void setDomReview(int domReview) {
        this.domReview = domReview;
    }
}
