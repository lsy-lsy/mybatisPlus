package com.mybatisplus.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mybatisplus.demo.bean.Admin;
import com.mybatisplus.demo.bean.Answer;
import com.mybatisplus.demo.mapper.AdminMapper;
import com.mybatisplus.demo.mapper.AnswerMapper;
import com.mybatisplus.demo.service.AdminService;
import com.mybatisplus.demo.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements AnswerService {

}
