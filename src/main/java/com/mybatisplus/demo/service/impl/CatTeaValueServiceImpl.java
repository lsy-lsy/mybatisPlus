package com.mybatisplus.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mybatisplus.demo.bean.Cat2UseValue;
import com.mybatisplus.demo.bean.CatTeaValue;
import com.mybatisplus.demo.mapper.Cat2UseValueMapper;
import com.mybatisplus.demo.mapper.CatTeaValueMapper;
import com.mybatisplus.demo.service.Cat2UseValueService;
import com.mybatisplus.demo.service.CatTeaValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatTeaValueServiceImpl extends ServiceImpl<CatTeaValueMapper, CatTeaValue> implements CatTeaValueService {
    @Autowired
    CatTeaValueMapper catTeaValueMapper;

    @Override
    public Boolean getCatTeacher(Integer teaId, int id) {
        QueryWrapper<CatTeaValue> catTeaValueQueryWrapper = new QueryWrapper<>();
        catTeaValueQueryWrapper.eq("cat_id",id);
        catTeaValueQueryWrapper.eq("tea_id",teaId);
        CatTeaValue catTeaValue = catTeaValueMapper.selectOne(catTeaValueQueryWrapper);
        if(catTeaValue == null){
            return false;
        }
//        catTeaValueMapper
        return true;
    }
}
