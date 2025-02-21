package com.westonline.socialplatform.pojo;

public class Review {
    private Integer reviewId;
    private String reviewContent;

    // Getters and Setters
    public Integer getReviewId() {
        if(this.reviewId != null){
            return this.reviewId;
        }
        return null;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    // toString 方法，方便调试
    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", reviewContent='" + reviewContent + '\'' +
                '}';
    }
}
