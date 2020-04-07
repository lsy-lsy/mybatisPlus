package com.mybatisplus.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mybatisplus.demo.bean.CatTeaValue;
import com.mybatisplus.demo.bean.Teacher;

import java.util.List;

public interface CatTeaValueMapper extends BaseMapper<CatTeaValue> {
    List<Teacher> getCatalog2Teacher(Integer id);

}
