package com.westonline.socialplatform.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBrief {
     int userId;
     String userName;
     String cv;
     byte[] image;
     int numOfLikes;
     int numOfWriting;



    public UserBrief(int userId,String userName, String cv, byte[] image, int numOfLikes, int numOfWriting) {
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
