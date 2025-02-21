package com.westonline.socialplatform.controller;

import com.westonline.socialplatform.mapper.UserMapper;
import com.westonline.socialplatform.pojo.*;
import com.westonline.socialplatform.service.platformServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;


@Transactional
@RestController
public class platformController {

    @Autowired
    private platformServiceImpl platformService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    //首页

        //时间榜
        @GetMapping("/getarticlehome")
        public Result getLatest() {
            Result result = new Result();
            ArrayList<LatestTerm> latestTerm = platformService.getLatestTerm();
            result.setData(latestTerm);
            return result;
        }

        //热度榜
        @GetMapping("/getaticlerank")
        public Result getTops() throws IOException {
            Result result = new Result();
            ArrayList<TopTerm> topTerms = platformService.getTopTerm();
            result.setData(topTerms);
            return result;
        }

        //热度帮详情
        @GetMapping("/getarticleranktopall")
        public Result getAllTops() {
            Result result = new Result();
            ArrayList<LatestTerm> latestTerm = platformService.getLatestTermOrdered();
            result.setData(latestTerm);
            return result;
        }


        //个人信息
        @GetMapping("/getbrief/{userId}")
        public Result getBrief(@PathVariable("userId") int userId) throws IOException {
            Result result = new Result();
            ArrayList<User> userArrayList = new ArrayList<>();
            userArrayList.add(platformService.getUser(userId));
            result.setData(userArrayList);
            return result;
        }
        //喜欢的文章
        @GetMapping("/getlikes/{userId}")
        public Result getLikes(@PathVariable("userId") int userId) throws IOException {
            Result result = new Result();
            result.setData( platformService.getLikedLatestTerm(userId));
            return result;
        }
        //写的文章
        @GetMapping("/getwritten/{userId}")
        public Result getWtitten(@PathVariable("userId") int userId) throws IOException {
            Result result = new Result();
            result.setData(platformService.getWrittenLatestTerm(userId));
            return result;
        }

        //文章详情页
        @GetMapping("/getdetail/{articleId}")
        public Result getDetail(@PathVariable("articleId") int articleId) throws IOException {
            Result result = new Result();
            ArrayList<DetailTerm> detailTerms = new ArrayList<>();
            detailTerms.add(platformService.getDetailTerm(articleId));
            result.setData(detailTerms);
            return result;
        }

       //返回评论区
        @GetMapping("/getreviews/{articleId}")
        public Result getReviews(@PathVariable("articleId") int articleId) throws IOException {
            Result result = new Result();
            result.setMap(platformService.getReviews(articleId));
            return result;
        }
        //写文章
        @PostMapping("/write/{userId}")
        public Result postArticle(@RequestBody Article article , @PathVariable("userId") int userId) throws IOException {
            Result result = new Result();
            Integer i = platformService.insertArticle(article, userId);
            ArrayList<Integer> list = new ArrayList<>();
            list.add(i);
            result.setData(list);
            return result;
        }

//        //登录
//        @PostMapping("/login")
//        public Result login(@RequestBody User user) {
//            Result result = new Result();
//
//        }

        //注册（图片是源文件）
        @PostMapping("/register")
        public Result register(String userName , String password, MultipartFile file) throws Exception {
            Result result = new Result();
            String s = platformService.insertUser(userName, password, file);
            ArrayList<String> list = new ArrayList<>();
            list.add(s);
            result.setData(list);
            return result;
        }

        //点赞
        @PostMapping("/likearticle")
        public Result likeArticle(int articleId,int userId) throws IOException {
            Result result = new Result();
            ArrayList<Integer> list = new ArrayList<>();
            list.add( platformService.putLikes(articleId,userId));
            result.setData(list);
            return result;
        }

        //浏览
        @PutMapping("/views")
         public Result viewArticle(Integer articleId) throws IOException {
            Result result = new Result();
            platformService.putViews(articleId);
            return result;
         }

         //写主评论
        @PostMapping("/domcomment")
        public Result domComment(@RequestBody PostReview postReview) throws IOException {
            Result result = new Result();
            ArrayList<Integer> list = new ArrayList<>();
            list.add(platformService.postReview(postReview));
            result.setData(list);
            return result;
        }


        //写从评论
        @PostMapping("/subcomment")
        public Result subComment(@RequestBody PostSubReview postSubReview) throws IOException {
            Result result = new Result();
            ArrayList<Integer> list = new ArrayList<>();
            list.add(platformService.postSubReview(postSubReview));
            result.setData(list);
            return result;
        }

        //修改用户信息
        @PutMapping("/updateinfo")
        public Result updateUser(String userName, String password , MultipartFile image,int userId) throws IOException {
            Result result = new Result();
            int i = platformService.updateUserInfo(userName, password, image, userId);
            ArrayList<Integer> list = new ArrayList<>();
            list.add(i);
            result.setData(list);
            return result;
        }


}
