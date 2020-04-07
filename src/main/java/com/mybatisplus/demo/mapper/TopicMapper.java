package com.mybatisplus.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mybatisplus.demo.bean.Admin;
import com.mybatisplus.demo.bean.Topic;

import java.util.List;

public interface TopicMapper extends BaseMapper<Topic> {
    List<Topic> getTopic(String vid,String uId);
}
