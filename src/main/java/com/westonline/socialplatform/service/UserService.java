package com.westonline.socialplatform.service;

import com.westonline.socialplatform.pojo.Result;
import com.westonline.socialplatform.pojo.User;
import com.westonline.socialplatform.pojo.WriterInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    //展示个人信息
    User getUser() throws IOException;

    //注册
    Result insertUser(String userName, String password, MultipartFile image) throws Exception;

    //修改用户信息
    int updateUserInfo(String userName,String password,MultipartFile image) throws IOException;

    //获取喜欢个数
    int getNumberOfLikes() throws IOException;

    //简介
    Result getInfo() throws IOException;

//    //获取作者信息
//    WriterInfo getWriterInfo(String userName) throws IOException;

}
