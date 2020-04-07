package com.mybatisplus.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mybatisplus.demo.bean.Catalog2;
import com.mybatisplus.demo.bean.Teacher;

import java.util.List;

public interface Catalog2Mapper extends BaseMapper<Catalog2> {
    List<Catalog2> getCat2Tea(Integer id);

    List<Catalog2> getCat2User(Integer id);

    List<Catalog2> getCat2TeacherList();

    List<Catalog2> getVideoTeacherValue(String cat2Id, String teaId);

    List<Catalog2> getCat1Cata2log(String id);

    List<Catalog2> getCat1Cata2logAll(String cat2Name );

}
