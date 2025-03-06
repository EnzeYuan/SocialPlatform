package com.westonline.socialplatform.utils;

import com.westonline.socialplatform.mapper.UserMapper;
import com.westonline.socialplatform.pojo.LoginUser;
import com.westonline.socialplatform.pojo.User;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.concurrent.TimeUnit;

public class UserUtil {
    StringRedisTemplate stringRedisTemplate;
    UserMapper userMapper;


    //获取用户id
    public Long getUserId(){
        LoginUser user=(LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user1= new User();
        user1.setUserName(user.getUsername());
        //从redis中获取
        Long userId = (Long) stringRedisTemplate.opsForHash().get("LoginUser:" + user1.getUserName(), "userId");
        //如果没有命中
        if(userId==null){
            userId = Long.valueOf(userMapper.getUserId(user1.getUserName()));
            //写入redis
            stringRedisTemplate.opsForHash().put("LoginUser:" + user1.getUserName(), "userId", String.valueOf((userId)));
            stringRedisTemplate.expire("LoginUser:" + user1.getUserName(),600, TimeUnit.SECONDS);
        }
        return userId;
    }
}
