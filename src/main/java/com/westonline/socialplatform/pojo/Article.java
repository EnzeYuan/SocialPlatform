package com.westonline.socialplatform.pojo;
import java.sql.Timestamp;

public class Article {
    private Integer articleId;
    private String articleName;
    private String body;
    private Timestamp releaseTime;
    private Integer numOfViews;

    // Getters and Setters
    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Timestamp getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Timestamp releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Integer getNumOfViews() {
        return numOfViews;
    }

    public void setNumOfViews(Integer numOfViews) {
        this.numOfViews = numOfViews;
    }

    // toString 方法，方便调试
    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", articleName='" + articleName + '\'' +
                ", body='" + (body != null ? body.substring(0, Math.min(body.length(), 50)) + "..." : "null") + '\'' +
                ", releaseTime=" + releaseTime +
                ", numOfViews=" + numOfViews +
                '}';
    }
}