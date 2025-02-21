package com.westonline.socialplatform.pojo;

public class TopTerm {
    private int articleId;
    private String articleName;
    private int score;

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "TopTerm{" +
                "articleId=" + articleId +
                ", articleName='" + articleName + '\'' +
                ", score=" + score +
                '}';
    }

    public void setScore(int score) {
        this.score = score;
    }

    public TopTerm(int articleId, String articleName, int score) {
        this.articleId = articleId;
        this.articleName = articleName;
        this.score = score;
    }

    public TopTerm() {
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

}
