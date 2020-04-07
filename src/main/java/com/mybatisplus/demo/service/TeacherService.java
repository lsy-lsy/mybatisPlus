package com.mybatisplus.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mybatisplus.demo.bean.Admin;
import com.mybatisplus.demo.bean.Teacher;

import java.util.List;

public interface TeacherService extends IService<Teacher> {

    List<Teacher> teacherList();

    IPage<Teacher> pageTeacherList(Page pages, String teaName);

    Teacher teaLogin(String teaLoginName);
}
