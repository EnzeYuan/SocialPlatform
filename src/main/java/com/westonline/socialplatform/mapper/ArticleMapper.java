package com.westonline.socialplatform.mapper;

import com.westonline.socialplatform.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface ArticleMapper {

    List<Article> selectAllArticle();

    int getLikes(int articleId);

    Long getAuthor(int articleId);

    Article selectArticleById(int articleId);

    List<Article> selectAllArticleOrdered();

    int insertArticle(Article article);

    int insertWriting(int articleId , Long userId);

    int putToLike(int articleId, Long userId);

    void updateViews(int articleId);
}
