package com.westonline.socialplatform.controller;

import com.westonline.socialplatform.mapper.UserMapper;
import com.westonline.socialplatform.pojo.Result;
import com.westonline.socialplatform.pojo.User;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author 袁同学
 */
@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserMapper userMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;


    //    登录接口与 Spring Security 的集成问题
//    在你的代码中，/user/login 被定义为一个普通的 Spring MVC 控制器方法，但同时你也在 Spring Security 配置中指定了登录处理逻辑（formLogin）。这种混合使用可能会导致一些问题：
//    问题描述：
//    Spring Security 的 formLogin 默认会接管 /user/login 的处理逻辑，但你的控制器方法也试图处理这个路径。这可能会导致冲突，或者 Spring Security 的登录逻辑被绕过。
//    如果用户直接访问 /user/login，Spring Security 的登录处理逻辑可能不会被触发，导致认证失败。
    @PostMapping("/user/login")
    public Result login(@RequestBody User user){

        // 使用用户名和密码进行认证
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user.getUserName(), user.getPassword());
        //验证
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        //用户id和用户名存入redis
        Long userId = Long.valueOf(userMapper.getUserId(user.getUserName()));
        stringRedisTemplate.opsForHash().put("userLogin:"+user.getUserName(),"userId",String.valueOf(userId));
        stringRedisTemplate.expire("userLogin:"+userId, 300, TimeUnit.SECONDS);

        Result result = new Result();
        result.setData(List.of("Login Success"));
        return result;
    }
}
