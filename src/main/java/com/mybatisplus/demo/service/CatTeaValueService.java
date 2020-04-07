package com.mybatisplus.demo.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mybatisplus.demo.bean.CatTeaValue;
import com.mybatisplus.demo.bean.Catalog2;
import com.mybatisplus.demo.bean.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CatTeaValueService extends IService<CatTeaValue> {


    Boolean getCatTeacher(Integer teaId, int id);
}
