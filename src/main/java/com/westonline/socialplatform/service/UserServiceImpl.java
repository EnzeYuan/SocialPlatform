package com.westonline.socialplatform.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.westonline.socialplatform.mapper.UserMapper;
import com.westonline.socialplatform.pojo.Result;
import com.westonline.socialplatform.pojo.User;
import com.westonline.socialplatform.pojo.UserBrief;
import com.westonline.socialplatform.utils.SnowflakeIdWorker;
import com.westonline.socialplatform.utils.UserUtil;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    PasswordEncoder passwordEncoder;

    UserUtil userUtil = new UserUtil();

    //个人主页
    @Override
    public User getUser(){
        User user = userMapper.selectOne(userUtil.getUserId());
        user.setPassword(null);
        return user;
    }

    //注册
    @Override
    public Result insertUser(String userName, String password, MultipartFile image) throws Exception {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(passwordEncoder.encode(password));
        user.setImage(image.getBytes());
        //加密
        user.setUserId(new SnowflakeIdWorker(1,1).nextId());
        if (userMapper.selectUserByUserName(userName)!=null) {
            throw new Exception("Username already exists!");
        }
        userMapper.insertUser(user);

        return getInfo();
    }

    @Override
    public int updateUserInfo(String userName, String password, MultipartFile image) throws IOException {
        byte[] bytes = image.getBytes();
        return userMapper.updateUser(userName,password,bytes,userUtil.getUserId());
    }

    @Override
    public int getNumberOfLikes()  {
        return userMapper.selectNumOfLikes(userUtil.getUserId());
    }

    public int getNumberOfWritten()  {
        return userMapper.selectNumOfWritten(userUtil.getUserId());
    }

    @Override
    public Result getInfo() throws IOException {
        Long userId = userUtil.getUserId();
        Result result = new Result();
        //从Redis中获取
        String s = stringRedisTemplate.opsForValue().get("UserInfo:" + userId);
        if(s==null) {
            //未命中
            UserBrief userBrief = new UserBrief();
            User user = userMapper.selectOne(userId);
            userBrief.setUserId(user.getUserId());
            userBrief.setUserName(user.getUserName());
            userBrief.setCv(user.getCv());
            userBrief.setImage(user.getImage());
            userBrief.setNumOfLikes(getNumberOfLikes());
            userBrief.setNumOfWriting(getNumberOfWritten());
            //添加到redis
            s=mapper.writeValueAsString(userBrief);
            stringRedisTemplate.opsForValue().append("UserInfo:" + userId,s);
        }
        result.setData(List.of(s));
        return result;
    }

//    @Override
//    public WriterInfo getWriterInfo(String userName) throws IOException {
//        User user = userMapper.selectUserByUserName(userName);
//        WriterInfo writerInfo = new WriterInfo();
//        writerInfo.setImage(user.getImage());
//        int num = userMapper.selectWritten(user.getUserId()).size();
//        writerInfo.setNumOfWritten(num);
//        return writerInfo;
//    }

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
