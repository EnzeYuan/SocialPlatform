package com.westonline.socialplatform.controller;

import com.westonline.socialplatform.mapper.ArticleMapper;
import com.westonline.socialplatform.pojo.*;
import com.westonline.socialplatform.service.platformServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;


@Transactional
@RestController
public class platformController {

    @Autowired
    private platformServiceImpl platformService;
    @Autowired
    private ArticleMapper articleMapper;

        //点赞
        @PostMapping("/likearticle/{articleId}")
        public Result likeArticle(@PathVariable("articleId") int articleId)  {
            Result result = new Result();
            ArrayList<Integer> list = new ArrayList<>();
            platformService.putLikes(articleId);
            list.add(articleMapper.getLikes(articleId));
            result.setData(list);
            return result;
        }

        //浏览
        @PostMapping("/views/{articleId}")
         public Result viewArticle(@PathVariable("articleId") int articleId) throws IOException {
            Result result = new Result();
            platformService.putViews(articleId);
            return result;
         }


}
