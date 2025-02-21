package com.westonline.socialplatform.mapper;

import com.westonline.socialplatform.pojo.Article;
import com.westonline.socialplatform.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    String selectOneUser(int id);

    User selectOne (int id);

    List<Article> selectLikes (int id);

    List<Article> selectWritten(int id);

    String getPassword(String userName);

    String getUserId(String userName);

    int insertUser(User user);

    int updateUser(String userName, String password,byte[] image,int userId);

    User selectUserByUserName(String userName);
}
