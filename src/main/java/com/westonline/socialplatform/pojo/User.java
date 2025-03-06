package com.westonline.socialplatform.pojo;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private Long  userId;
    private String userName;
    private String cv;
    // 使用 byte[] 来表示 BLOB 数据
    private byte[] image;
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
