package com.westonline.socialplatform.mapper;

import com.westonline.socialplatform.pojo.Article;
import com.westonline.socialplatform.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    String selectOneUser(Long id);

    User selectOne (Long id);

    List<Article> selectLikes (Long id);

    List<Article> selectWritten(Long id);

    String getPassword(String userName);

    String getUserId(String userName);

    int insertUser(User user);

    int updateUser(String userName, String password,byte[] image,Long userId);

    User selectUserByUserName(String userName);

    int selectNumOfLikes(Long userId);

    int selectNumOfWritten(Long userId);
}
