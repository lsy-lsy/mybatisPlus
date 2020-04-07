package com.mybatisplus.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mybatisplus.demo.bean.Teacher;
import com.mybatisplus.demo.bean.User;
import com.mybatisplus.demo.mapper.TeacherMapper;
import com.mybatisplus.demo.mapper.VidTeaValueMapper;
import com.mybatisplus.demo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    VidTeaValueMapper vidTeaValueMapper;

    @Override
    public List<Teacher> teacherList() {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper();
        List<Teacher> teacherList = teacherMapper.selectList(queryWrapper);
        return teacherList;
    }

    @Override
    public IPage<Teacher> pageTeacherList(Page pages, String teaName) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        if( teaName != null && !teaName.equals("") ){
            queryWrapper.eq("tea_name",teaName);
        }
        IPage iPage = teacherMapper.selectPage(pages, queryWrapper);
        return iPage;
    }

    @Override
    public Teacher teaLogin(String teaLoginName) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tea_login_name",teaLoginName);
        Teacher teahcer = teacherMapper.selectOne(queryWrapper);
        if(teahcer !=null){
            return teahcer;
        }
        return null;
    }


}
