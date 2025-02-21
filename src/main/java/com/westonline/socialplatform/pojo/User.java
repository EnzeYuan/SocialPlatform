package com.westonline.socialplatform.pojo;

public class User {
    private Integer userId;
    private String userName;
    private String cv;
    // 使用 byte[] 来表示 BLOB 数据
    private byte[] image;
    private String password;

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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

    // toString 方法，方便调试
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", cv='" + cv + '\'' +
                ", image=" + (image != null ? image.length + " bytes" : "null") +
                ", password='" + password + '\'' +
                '}';
    }
}
