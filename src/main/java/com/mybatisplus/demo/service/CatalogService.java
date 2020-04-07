package com.mybatisplus.demo.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatisplus.demo.bean.Catalog1;
import com.mybatisplus.demo.bean.Catalog2;
import com.sun.org.apache.xml.internal.resolver.Catalog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CatalogService  {
    List<Catalog1> getCatalog1();

    List<Catalog2> getCatalog2(Integer id);

    Catalog2 getCatalog2ById(String id);

    Catalog1 getCatalog1ById(String catalog1Id);

    IPage<Catalog1> pageCatalogList(Page pages);

    Boolean saveCatalog1(Catalog1 catalog1);

    Boolean editCatalog1(Catalog1 catalog1);
//    删除一级目录
    Boolean removeCatalog1ById(Integer id);

    Catalog1 getCatalog1ByCat2Id(String cat2Id);
}
