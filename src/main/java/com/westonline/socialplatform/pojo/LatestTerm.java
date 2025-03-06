package com.westonline.socialplatform.pojo;

public class LatestTerm {
    private int articleId;
    private String articleName;
    private String writerName;
    private String body;
    private int numOfLikes;
    private int numOfViews;

    public LatestTerm() {
    }

    public LatestTerm(int articleId, String articleName, String writerName, String body, int numOfLikes, int numOfViews) {
        this.articleId = articleId;
        this.articleName = articleName;
        this.writerName = writerName;
        this.body = body;
        this.numOfLikes = numOfLikes;
        this.numOfViews = numOfViews;
    }

    @Override
    public String toString() {
        return "LatestTerm{" +
                "articleId=" + articleId +
                ", articleName='" + articleName + '\'' +
                ", writerName='" + writerName + '\'' +
                ", body='" + body + '\'' +
                ", numOfLikes=" + numOfLikes +
                ", numOfViews=" + numOfViews +
                '}';
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getNumOfLikes() {
        return numOfLikes;
    }

    public void setNumOfLikes(int numOfLikes) {
        this.numOfLikes = numOfLikes;
    }

    public int getNumOfViews() {
        return numOfViews;
    }

    public void setNumOfViews(int numOfViews) {
        this.numOfViews = numOfViews;
    }
}
