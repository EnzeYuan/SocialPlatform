package com.westonline.socialplatform.service;

import java.io.IOException;

public interface platformService {

    //点赞
    void putLikes(int articleId) throws IOException;

    //更新浏览量
    void putViews(Integer articleId) throws IOException;


}
