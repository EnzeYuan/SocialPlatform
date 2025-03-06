package com.westonline.socialplatform.controller;

import com.westonline.socialplatform.pojo.Result;
import com.westonline.socialplatform.pojo.User;
import com.westonline.socialplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    //个人信息
    @GetMapping("/getbrief")
    public Result getBrief() throws IOException {
        Result result = new Result();
        ArrayList<User> userArrayList = new ArrayList<>();
        userArrayList.add(userService.getUser());
        result.setData(userArrayList);
        return result;
    }

    //注册（图片是源文件）
    @PostMapping("/register")
    public Result register(String userName , String password, MultipartFile file) throws Exception {
        return userService.insertUser(userName, password, file);
    }

    //修改用户信息
    @PutMapping("/updateinfo")
    public Result updateUser(String userName, String password , MultipartFile image) throws IOException {
        Result result = new Result();
        int i = userService.updateUserInfo(userName, password, image);
        ArrayList<Integer> list = new ArrayList<>();
        list.add(i);
        result.setData(list);
        return result;
    }


//        //获取作者信息
//        @GetMapping("/getwriterinfo/{userName}")
//        public Result getWriterInfo(@PathVariable("userName") String userName) throws IOException {
//            Result result = new Result();
//            result.setData(List.of(userService.getWriterInfo(userName)));
//            return result;
//        }

}
