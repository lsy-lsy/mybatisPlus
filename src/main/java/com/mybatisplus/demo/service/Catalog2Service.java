package com.mybatisplus.demo.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mybatisplus.demo.bean.Catalog2;
import com.mybatisplus.demo.bean.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface Catalog2Service extends IService<Catalog2> {

    IPage<Catalog2> pageCatalog2(Page page);

    Boolean getCat2Name(Catalog2 catalog2);

    boolean delCat2(Integer id);

    Boolean editCat2(Catalog2 catalog2);

    List<Catalog2> getCat2Tea(Integer id);

    List<Catalog2> getCat2User(Integer id);

    List<Catalog2>  getCat2TeacherList();

    List<Catalog2> getVideoTeacherValue(String cat2Id, String teaId);

    Boolean getSaveCat1Use(String uId, String teaId,String catalog2Id);

    Boolean getCat1Use(String uId,String teaId, String catalog2Id);


    List<Teacher> getCatalog2Teacher(Integer id);


    List<Catalog2> getCat1Cata2log(String id);

    List<Catalog2> getCat1Cata2logAll(String cat2Name);

    Catalog2 saveCat2(Catalog2 catalog2);
}
