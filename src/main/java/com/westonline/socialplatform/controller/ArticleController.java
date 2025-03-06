package com.westonline.socialplatform.controller;

import com.westonline.socialplatform.pojo.*;
import com.westonline.socialplatform.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    //首页

    //时间榜
    @GetMapping("/getarticlehome")
    public Result getLatest() {
        Result result = new Result();
        List<LatestTerm> latestTerm = articleService.getLatestTerm();
        result.setData(latestTerm);
        return result;
    }

    //热度榜
    @GetMapping("/getaticlerank")
    public Result getTops() throws IOException {
        Result result = new Result();
        ArrayList<TopTerm> topTerms = articleService.getTopTerm();
        result.setData(topTerms);
        return result;
    }

    //喜欢的文章
    @GetMapping("/getlikes")
    public Result getLikes() throws IOException {
        Result result = new Result();
        result.setData( articleService.getLikedLatestTerm());
        return result;
    }
    //写的文章
    @GetMapping("/getwritten")
    public Result getWtitten() throws IOException {
        Result result = new Result();
        result.setData(articleService.getWrittenLatestTerm());
        return result;
    }

    //文章详情页
    @GetMapping("/getdetail/{articleId}")
    public Result getDetail(@PathVariable("articleId") int articleId) throws IOException {
        Result result = new Result();
        ArrayList<DetailTerm> detailTerms = new ArrayList<>();
        detailTerms.add(articleService.getDetailTerm(articleId));
        result.setData(detailTerms);
        return result;
    }

    //写文章
    @PostMapping("/write")
    public Result postArticle(@RequestBody Article article ) throws IOException {
        Result result = new Result();
        Integer i =articleService.insertArticle(article);
        ArrayList<Integer> list = new ArrayList<>();
        list.add(i);
        result.setData(list);
        return result;
    }

    //        //热度帮详情
//        @GetMapping("/getarticleranktopall")
//        public Result getAllTops() {
//            Result result = new Result();
//            ArrayList<LatestTerm> latestTerm = articleService.getLatestTermOrdered();
//            result.setData(latestTerm);
//            return result;
//        }

}
