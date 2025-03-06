package com.westonline.socialplatform.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.westonline.socialplatform.mapper.ArticleMapper;
import com.westonline.socialplatform.pojo.*;
import com.westonline.socialplatform.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

/**
 * @author 袁同学
 */
@Service
public class platformServiceImpl implements platformService{
    @Autowired
    private ArticleMapper articleMapper;
    UserUtil userUtil = new UserUtil();

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static final String LEADERBOARD_KEY = "Top";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    //点赞
    @Transactional
    @Override
    public void putLikes(int articleId) {
        articleMapper.putToLike(articleId, userUtil.getUserId());
        //删除redis中相关信息
        stringRedisTemplate.opsForHash().delete("ArticleList:" + articleId);
    }

    //更新浏览量
    @Override
    public void putViews(Integer articleId) throws IOException {
        //article表要更新
        articleMapper.updateViews(articleId);
        //redis更新
        Set<String> jsonSet = stringRedisTemplate.opsForZSet().reverseRange(LEADERBOARD_KEY, 0, -1);
        //找到修改的对象
        TopTerm aim=null;
        if (jsonSet != null) {
            for (String jsonString : jsonSet) {
                TopTerm topTerm = OBJECT_MAPPER.readValue(jsonString, TopTerm.class);
                if (topTerm.getArticleId()==(articleId)) {
                    aim = topTerm;
                    break;
                }
            }
        }
        if (aim != null) {
            String aim0 = OBJECT_MAPPER.writeValueAsString(aim);
            aim.setScore(aim.getScore()+1);
            String aimJson = OBJECT_MAPPER.writeValueAsString(aim);
            //删除原来的
            stringRedisTemplate.opsForZSet().remove(LEADERBOARD_KEY,aim0);
            //添加新的
            stringRedisTemplate.opsForZSet().add(LEADERBOARD_KEY,aimJson,aim.getScore());
        }
    }




}
