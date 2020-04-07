package com.mybatisplus.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mybatisplus.demo.bean.Catalog1;
import com.mybatisplus.demo.bean.Catalog2;
import com.mybatisplus.demo.bean.Video;
import com.mybatisplus.demo.mapper.Catalog1Mapper;
import com.mybatisplus.demo.mapper.Catalog2Mapper;
import com.mybatisplus.demo.mapper.VideoMapper;
import com.mybatisplus.demo.service.CatalogService;
import com.mybatisplus.demo.service.VideoService;
import com.sun.org.apache.xml.internal.resolver.Catalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    Catalog1Mapper catalog1Mapper;
    @Autowired
    Catalog2Mapper catalog2Mapper;


    @Override
    public List<Catalog1> getCatalog1() {
        QueryWrapper<Catalog1> queryWrapper = new QueryWrapper<>();
        List<Catalog1> catalog1List = catalog1Mapper.selectList(queryWrapper);
        return catalog1List;
    }

    @Override
    public List<Catalog2> getCatalog2(Integer id) {
        QueryWrapper<Catalog2> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("catalog1_id",id);
        List<Catalog2> catalog2List = catalog2Mapper.selectList(queryWrapper);

        return catalog2List;
    }

    @Override
    public Catalog2 getCatalog2ById(String catalog2Id) {
        Catalog2 catalog2 = catalog2Mapper.selectById(catalog2Id);
        return catalog2;
    }

    @Override
    public Catalog1 getCatalog1ById(String catalog1Id) {
        Catalog1 catalog1 = catalog1Mapper.selectById(catalog1Id);
        return catalog1;
    }

    //查询一级目录分页
    @Override
    public IPage<Catalog1> pageCatalogList(Page pages) {
        QueryWrapper<Catalog1> queryWrapper = new QueryWrapper<>();
        IPage<Catalog1> catalog1IPage = catalog1Mapper.selectPage(pages,queryWrapper);
        return catalog1IPage;
    }

    //保存一级目录
    @Override
    public Boolean saveCatalog1(Catalog1 catalog1) {
        int save = catalog1Mapper.insert(catalog1);
        if(save ==1){
            return true;
        }
        return false;
    }
//修改一级分类
    @Override
    public Boolean editCatalog1(Catalog1 catalog1) {

        int edit = catalog1Mapper.updateById(catalog1);

        if(edit ==1){
            return true;
        }
        return false;
    }

    //删除一级目录
    @Override
    public Boolean removeCatalog1ById(Integer id) {
        int del = catalog1Mapper.deleteById(id);
        if(del==1){
            return true;
        }
        return false;
    }

    @Override
    public Catalog1 getCatalog1ByCat2Id(String cat2Id) {
        QueryWrapper<Catalog1> catalogQueryWrapper = new QueryWrapper<>();
        catalogQueryWrapper.eq("id",cat2Id);
        Catalog1 catalog1 = catalog1Mapper.selectOne(catalogQueryWrapper);
        return catalog1;
    }


}
