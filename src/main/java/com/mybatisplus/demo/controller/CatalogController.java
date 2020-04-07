package com.mybatisplus.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatisplus.demo.bean.Catalog1;
import com.mybatisplus.demo.bean.Catalog2;
import com.mybatisplus.demo.service.CatalogService;
import com.mybatisplus.util.R;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CatalogController {
    private static final String PREFIX ="/admin/videoManage/Catalog1/";
    @Autowired
    CatalogService catalogServiceImpl;
    //一级目录保存页面
    @RequestMapping("toSaveCatalog1View")
    public String toSaveCatalog1View(){
        return PREFIX + "saveCatalog1View";
    }
    //一级目录保存功能saveCatalog
    @RequestMapping("/saveCatalog")
    @ResponseBody
    public Object saveCatalog1(Catalog1 catalog1){
        Boolean save = catalogServiceImpl.saveCatalog1(catalog1);
        if(save){
           return R.success("保存成功！");
        }
        return R.error("保存失败！");
    }

    //一级目录修改页面
    @RequestMapping("toDeitCatalog1View")
    public String toDelCatalog1View(@Param("id") String id, Model model){
        Catalog1 catalog1 = catalogServiceImpl.getCatalog1ById(id);
        model.addAttribute("catalog1",catalog1);
        return PREFIX + "deitCatalog1View";
    }

    //一级目录修改功能
    @RequestMapping("/editCatalog1View")
    @ResponseBody
    public Object editCatalog1View(Catalog1 catalog1){
        Boolean edit = catalogServiceImpl.editCatalog1(catalog1);
        if(edit){
            return R.success("修改成功！");
        }
        return R.error("修改失败！");
    }
    //删除功能delCatalog1
    @DeleteMapping("delCatalog1")
    @ResponseBody
    public Object delCatalog1(@Param("id")Integer id){
        Boolean del = catalogServiceImpl.removeCatalog1ById(id);
        if(del){
            return R.success("删除成功！");
        }else {
            return R.error("删除失败!");
        }
    }

    //一级目录页面
    @RequestMapping("toCatalog1View")
    public String toCatalog1List(){
        return PREFIX + "toCatalog1View";
    }
    //一级目录分页
    @RequestMapping("getCatalog1List")
    @ResponseBody
    public Object getCatalog1List(HttpServletRequest request){
        int page=(Integer.parseInt(request.getParameter("page").trim()));
        int limit=(Integer.parseInt(request.getParameter("limit").trim()));
        Page pages = new Page<>(page,limit);
        IPage<Catalog1> catalog1IPage =catalogServiceImpl.pageCatalogList(pages);
        return R.return4Page(catalog1IPage);
    }

    //全部一级目录
    @RequestMapping("getCatalog1")
    @ResponseBody
    public List<Catalog1> getCatalog1(){
        List<Catalog1> catalog1List = catalogServiceImpl.getCatalog1();
        return catalog1List;
    }

    @RequestMapping("getCatalog2")
    @ResponseBody
    public List<Catalog2> getCatalog2(@Param("id")Integer id){
        List<Catalog2> catalog2List = catalogServiceImpl.getCatalog2(id);
        return catalog2List;
    }
}
