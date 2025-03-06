package com.westonline.socialplatform.service;


import com.westonline.socialplatform.pojo.Article;
import com.westonline.socialplatform.pojo.DetailTerm;
import com.westonline.socialplatform.pojo.LatestTerm;
import com.westonline.socialplatform.pojo.TopTerm;

import java.io.IOException;
import java.util.ArrayList;

public interface ArticleService {

    ArrayList<LatestTerm> getLatestTerm();

    ArrayList<TopTerm> getTopTerm() throws IOException;

    //ArrayList<LatestTerm> getLatestTermOrdered();

    //展示喜欢的文章
    ArrayList<LatestTerm> getLikedLatestTerm() throws IOException;

    //展示写的文章
    ArrayList<LatestTerm> getWrittenLatestTerm() throws IOException;

    //展示文章详情
    DetailTerm getDetailTerm(int id) throws IOException;

    //写文章
    int insertArticle(Article article) throws IOException;




}
