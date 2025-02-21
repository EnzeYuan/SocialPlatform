package com.westonline.socialplatform.pojo;

import java.sql.Timestamp;

public class DetailTerm extends LatestTerm{
    private Timestamp releaseTime;

    public DetailTerm() {
    }

    public DetailTerm(int articleId, String articleName, String writerName, String body, int numOfLikes, int numOfViews, Timestamp releaseTime) {
        super(articleId, articleName, writerName, body, numOfLikes, numOfViews);
        this.releaseTime = releaseTime;
    }

    public Timestamp getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Timestamp releaseTime) {
        this.releaseTime = releaseTime;
    }

}
