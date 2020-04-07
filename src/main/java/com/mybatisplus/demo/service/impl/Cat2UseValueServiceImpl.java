package com.mybatisplus.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mybatisplus.demo.bean.Cat2UseValue;
import com.mybatisplus.demo.mapper.Cat2UseValueMapper;
import com.mybatisplus.demo.service.Cat2UseValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Cat2UseValueServiceImpl extends ServiceImpl<Cat2UseValueMapper, Cat2UseValue> implements Cat2UseValueService {

    @Autowired
    Cat2UseValueMapper cat2UseValueMapper;

    @Override
    public Boolean getCat2UseTeacher(int uId, int teaId , int catalog2Id) {
        QueryWrapper<Cat2UseValue> cat2UseValueQueryWrapper = new QueryWrapper<>();
        cat2UseValueQueryWrapper.eq("use_id",uId);
        cat2UseValueQueryWrapper.eq("cat2_id",catalog2Id);
        cat2UseValueQueryWrapper.eq("tea_id",teaId);
        Cat2UseValue cat2UseValue = cat2UseValueMapper.selectOne(cat2UseValueQueryWrapper);
        if(cat2UseValue==null){
            return false;
        }
        return true;
    }

    @Override
    public Boolean deleteCat2(String cat2, String useId) {
        QueryWrapper<Cat2UseValue> cat2UseValueQueryWrapper = new QueryWrapper<>();
        cat2UseValueQueryWrapper.eq("use_id",useId);
        cat2UseValueQueryWrapper.eq("cat2_id",cat2);
        int delete = cat2UseValueMapper.delete(cat2UseValueQueryWrapper);
        if(delete ==1){
            return true;
        }
        return false;
    }
}
