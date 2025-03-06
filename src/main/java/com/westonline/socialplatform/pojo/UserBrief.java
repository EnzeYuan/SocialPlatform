package com.westonline.socialplatform.pojo;

import lombok.Getter;
import lombok.Setter;

public class UserBrief {
     Long userId;
     String userName;
     String cv;
     byte[] image;
     int numOfLikes;
     int numOfWriting;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getNumOfLikes() {
        return numOfLikes;
    }

    public void setNumOfLikes(int numOfLikes) {
        this.numOfLikes = numOfLikes;
    }

    public int getNumOfWriting() {
        return numOfWriting;
    }

    public void setNumOfWriting(int numOfWriting) {
        this.numOfWriting = numOfWriting;
    }

    public UserBrief(Long userId, String userName, String cv, byte[] image, int numOfLikes, int numOfWriting) {
        this.userId = userId;
        this.userName = userName;
        this.cv = cv;
        this.image = image;
        this.numOfLikes = numOfLikes;
        this.numOfWriting = numOfWriting;
    }

    public UserBrief() {
    }
}
