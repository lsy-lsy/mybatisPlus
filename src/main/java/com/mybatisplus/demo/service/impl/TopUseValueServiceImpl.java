package com.mybatisplus.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mybatisplus.demo.bean.Answer;
import com.mybatisplus.demo.bean.TopUseValue;
import com.mybatisplus.demo.bean.Topic;
import com.mybatisplus.demo.mapper.AnswerMapper;
import com.mybatisplus.demo.mapper.TopUseValueMapper;
import com.mybatisplus.demo.mapper.TopicMapper;
import com.mybatisplus.demo.mapper.VideoMapper;
import com.mybatisplus.demo.service.TopUseValueService;
import com.mybatisplus.demo.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopUseValueServiceImpl extends ServiceImpl<TopUseValueMapper, TopUseValue> implements TopUseValueService {


}
