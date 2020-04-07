package com.mybatisplus.demo.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mybatisplus.demo.bean.Answer;
import com.mybatisplus.demo.bean.Catalog2;
import com.mybatisplus.demo.bean.Topic;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TopicService extends IService<Topic> {
    IPage<Topic> pageTopicList(Page pages, String topName);

    Boolean saveTopic(Topic topic, Answer answer);

    Boolean updateTopic(Topic topic, Answer answer);

    Boolean delTopic(Integer id);

    Boolean delTopicVid(Integer id);

    Boolean topicExceed(int vidId);

    Boolean sameTopic(String topName, int vidId);

    List<Topic> getTopic(String vid,String uId);
}
