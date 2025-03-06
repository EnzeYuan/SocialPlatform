package com.westonline.socialplatform.controller;

import com.westonline.socialplatform.pojo.PostReview;
import com.westonline.socialplatform.pojo.PostSubReview;
import com.westonline.socialplatform.pojo.Result;
import com.westonline.socialplatform.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    //返回评论区
    @GetMapping("/getreviews/{articleId}")
    public Result getReviews(@PathVariable("articleId") int articleId) throws IOException {
        Result result = new Result();
        result.setMap(reviewService.getReviews(articleId));
        return result;
    }

    //写主评论
    @PostMapping("/domcomment")
    public Result domComment(@RequestBody PostReview postReview) throws IOException {
        Result result = new Result();
        ArrayList<Integer> list = new ArrayList<>();
        list.add(reviewService.postReview(postReview));
        result.setData(list);
        return result;
    }


    //写从评论
    @PostMapping("/subcomment")
    public Result subComment(@RequestBody PostSubReview postSubReview) throws IOException {
        Result result = new Result();
        ArrayList<Integer> list = new ArrayList<>();
        list.add(reviewService.postSubReview(postSubReview));
        result.setData(list);
        return result;
    }





}
