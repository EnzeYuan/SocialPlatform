package com.westonline.socialplatform.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.westonline.socialplatform.mapper.ArticleMapper;
import com.westonline.socialplatform.mapper.UserMapper;
import com.westonline.socialplatform.pojo.*;
import com.westonline.socialplatform.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author 袁同学
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    UserUtil userUtil = new UserUtil();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    //主页
    @Override
    public ArrayList<LatestTerm> getLatestTerm() {
        if(stringRedisTemplate.hasKey("ArticleList")){
            //从redis中获取
            List<String> articleList = stringRedisTemplate.opsForList().range("ArticleList", 0, -1);
            ArrayList<LatestTerm> list = new ArrayList<>();
            if (articleList != null) {
                articleList.forEach(article -> {
                    try {
                        LatestTerm latestTerm = OBJECT_MAPPER.readValue(article, LatestTerm.class);
                        list.add(latestTerm);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            return list;
        }else{
            //如果没有命中
            List<Article> articles = articleMapper.selectAllArticle();
            //添加到redis库中(以LatestTerm形式存)
            ArrayList<LatestTerm> list = getLatestTerms(articles);
            list.forEach(article -> {
                String prefix = article.getArticleId()+"";
                try {
                    stringRedisTemplate.opsForList().rightPush("ArticleList"+":"+prefix,OBJECT_MAPPER.writeValueAsString(article));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });
            //设置过期时间
            stringRedisTemplate.expire("ArticleList", 300, TimeUnit.SECONDS);
            return list;
        }
    }


    //榜单(redis)
    @Override
    public ArrayList<TopTerm> getTopTerm() throws IOException {
        Set<String> strings = stringRedisTemplate.opsForZSet().reverseRange( "Top", 0, -1);
        ArrayList<TopTerm> topTerms = new ArrayList<>();
        if (strings != null) {
            for (String jsonString : strings) {
                TopTerm topTerm = OBJECT_MAPPER.readValue(jsonString, TopTerm.class);
                topTerms.add(topTerm);
            }
        }
        return topTerms;
    }


    //获取喜欢的文章，封装成LatestTerm
    @Override
    public ArrayList<LatestTerm> getLikedLatestTerm() throws IOException {
        List<Article> articles = userMapper.selectLikes(userUtil.getUserId());
        return getLatestTerms(articles);
    }


    //获取写的文章，封装成LatestTerm
    @Override
    public ArrayList<LatestTerm> getWrittenLatestTerm() throws IOException {
        List<Article> articles = userMapper.selectWritten(userUtil.getUserId());
        return getLatestTerms(articles);
    }

    //文章详情页
    @Override
    public DetailTerm getDetailTerm(int articleId)  {
        //从redis中获取
        Map<Object, Object> articleDetail = stringRedisTemplate.opsForHash().entries("ArticleDetail:" + articleId);
        DetailTerm detailTerm = (DetailTerm)articleDetail.get(articleId);

        String articleName,writerName,body;
        int likes,numOfViews;
        Timestamp timestamp;

        if(detailTerm != null){
            articleName = detailTerm.getArticleName();
            writerName = detailTerm.getWriterName();
            body = detailTerm.getBody();
            likes=detailTerm.getNumOfLikes();
            numOfViews=detailTerm.getNumOfViews();
            timestamp=detailTerm.getReleaseTime();
        }else {
            //如果没有
            Article article = articleMapper.selectArticleById(articleId);
            articleName = article.getArticleName();
            writerName = userMapper.selectOneUser(articleMapper.getAuthor(articleId));
            body = article.getBody();
            likes = articleMapper.getLikes(articleId);
            numOfViews = article.getNumOfViews();
            timestamp = article.getReleaseTime();
            DetailTerm detailTerm1 = new DetailTerm(articleId, articleName, writerName, body, likes, numOfViews, timestamp);
            //添加到Redis中
            stringRedisTemplate.opsForHash().put("ArticleDetail", articleId, detailTerm1);
            stringRedisTemplate.expire("ArticleDetail:" + articleId, 300, TimeUnit.SECONDS);
        }
        return new DetailTerm(articleId,articleName,writerName,body,likes,numOfViews,timestamp);
    }

    //todo: 写文章("/write")
    @Transactional
    @Override
    public int insertArticle(Article article){
        articleMapper.insertArticle(article);
        int articleId = article.getArticleId();
        articleMapper.insertWriting(articleId,userUtil.getUserId());
        stringRedisTemplate.delete("ArticleList");
        stringRedisTemplate.opsForZSet().add("Top:"+articleId,article.getBody(),0);
        return articleId;
    }

    //Article转换成LatestTerm
    private ArrayList<LatestTerm> getLatestTerms(List<Article> articles) {

        ArrayList<LatestTerm> latestTerms = new ArrayList<>();
        for (Article article : articles) {
            int articleId = article.getArticleId();
            String articleName = article.getArticleName();
            String writerName = userMapper.selectOneUser(articleMapper.getAuthor(articleId));
            String body = article.getBody();
            int likes = articleMapper.getLikes(articleId);
            Integer numOfViews = article.getNumOfViews();
            latestTerms.add(new LatestTerm(articleId, articleName, writerName, body, likes, numOfViews));
        }

        return latestTerms;

    }


//    //获取用户id
//    public Long getUserId(){
//        LoginUser user=(LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user1= new User();
//        user1.setUserName(user.getUsername());
//        //从redis中获取
//        Long userId = (Long) stringRedisTemplate.opsForHash().get("LoginUser:" + user1.getUserName(), "userId");
//        //如果没有命中
//        if(userId==null){
//            userId = Long.valueOf(userMapper.getUserId(user1.getUserName()));
//            //写入redis
//            stringRedisTemplate.opsForHash().put("LoginUser:" + user1.getUserName(), "userId", String.valueOf((userId)));
//            stringRedisTemplate.expire("LoginUser:" + user1.getUserName(),600, TimeUnit.SECONDS);
//        }
//        return userId;
//    }

}
