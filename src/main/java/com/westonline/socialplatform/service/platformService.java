package com.westonline.socialplatform.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.westonline.socialplatform.pojo.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public interface platformService {
    ArrayList<LatestTerm> getLatestTerm();

    ArrayList<TopTerm> getTopTerm() throws IOException;

    ArrayList<LatestTerm> getLatestTermOrdered();

    //展示个人信息
    User getUser(int id) throws IOException;

    //展示喜欢的文章
    ArrayList<LatestTerm> getLikedLatestTerm(int id) throws IOException;

    //展示写的文章
    ArrayList<LatestTerm> getWrittenLatestTerm(int id) throws IOException;

    //展示文章详情
    DetailTerm getDetailTerm(int id) throws IOException;

    //评论区
    Map<ReviewTerm,ArrayList<ReviewTerm>> getReviews(int articleId) throws IOException;

    //写文章
    int insertArticle(Article article,int userId) throws IOException;

    void insetToRedis(int articleId) throws JsonProcessingException;

    //登录
   // ArrayList<String> getPassword(String userName) throws IOException;

    //注册
    String insertUser(String userName,String password, MultipartFile image) throws Exception;

    //点赞
    Integer putLikes(int articleId,int userId) throws IOException;

    //更新浏览量
    void putViews(Integer articleId) throws IOException;

    //写主评论
    int postReview(PostReview postReview) throws IOException;

    //写子评论
    int postSubReview(PostSubReview postSubReview) throws IOException;

    //修改用户信息
    int updateUserInfo(String userName,String password,MultipartFile image,int userId) throws IOException;

}
