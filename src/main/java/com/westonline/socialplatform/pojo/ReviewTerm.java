package com.westonline.socialplatform.pojo;


import java.util.Arrays;
import java.util.Objects;




public class ReviewTerm {
    private int reviewId;
    private String userName;
    private byte[] image;
    private String reviewContent;

    public int getReviewId() {
        return reviewId;
    }

    @Override
    public String toString() {
        return "ReviewTerm{" +
                "reviewId=" + reviewId +
                ", userName='" + userName + '\'' +
                ", image=" + Arrays.toString(image) +
                ", reviewContent='" + reviewContent + '\'' +
                '}';
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReviewTerm that = (ReviewTerm) o;
        return reviewId == that.reviewId && Objects.equals(userName, that.userName) && Objects.deepEquals(image, that.image) && Objects.equals(reviewContent, that.reviewContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId, userName, Arrays.hashCode(image), reviewContent);
    }

    public ReviewTerm() {
    }

    public ReviewTerm(int reviewId, String userName, byte[] image, String reviewContent) {
        this.reviewId = reviewId;
        this.userName = userName;
        this.image = image;
        this.reviewContent = reviewContent;
    }

}
