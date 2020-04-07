package com.mybatisplus.demo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatisplus.demo.bean.Catalog1;
import com.mybatisplus.demo.bean.Catalog2;
import com.mybatisplus.demo.bean.Teacher;
import com.mybatisplus.demo.service.Catalog2Service;
import com.mybatisplus.demo.service.CatalogService;
import com.mybatisplus.util.R;
import com.mybatisplus.util.UploadUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/catalog2")
public class Catalog2Controller {
    private static final  String PREFIX ="/admin/videoManage/Catalog2";

    @Autowired
    Catalog2Service catalog2ServiceImpl;
    @Autowired
    CatalogService catalogServiceImpl;

    //编辑页面
    @GetMapping("editCatalog2")
    public String toDeitCatalog2View(Model model,@Param("id") Integer id){
//        查询二级目录
        Catalog2 catalog2 = catalog2ServiceImpl.getById(id);
        //根据二级目录查询一级目录
        Catalog1 catalog1 = catalogServiceImpl.getCatalog1ById(String.valueOf(catalog2.getCatalog1Id()));
        List<Catalog1> catalog1List = catalogServiceImpl.getCatalog1();
        model.addAttribute("catalog1",catalog1);
        model.addAttribute("catalog2",catalog2);
        model.addAttribute("catalog1List",catalog1List);
        return PREFIX + "/editCatalog2";
    }

//    修改功能
    @PutMapping("edit")
    @ResponseBody
    public Object editCatalog2(Catalog2 catalog2){
        Boolean edit = catalog2ServiceImpl.editCat2(catalog2);
        if(edit){
            return R.success("修改成功！");
        }
        return R.error("修改成功！");
    }


    //删除功能
    @DeleteMapping("/del")
    @ResponseBody
    public Object delCatalog2(@Param("id")Integer id){
        //删除数据库数据
        boolean del = catalog2ServiceImpl.delCat2(id);
        if(del){
            return R.success("删除成功！");
        }
        return R.success("删除失败！");
    }


    //二级保存目录页面
    @GetMapping("saveCatalog")
    public String saveCatalog2View(Model model){
        List<Catalog1> catalog1List = catalogServiceImpl.getCatalog1();
        model.addAttribute("catalog1List",catalog1List);
        return PREFIX + "/saveCatalog";
    }
    //保存页面功能
    @PostMapping("save")
    @ResponseBody
    public Object saveCatalog2(Catalog2 catalog2){

        Boolean cat2Name = catalog2ServiceImpl.getCat2Name(catalog2);
        if(cat2Name){
            return R.error("二级目录已存在,请勿重复！");
        }

        Boolean save = catalog2ServiceImpl.save(catalog2);
        if(save){
            return  R.success("保存成功！");
        }
        return R.error("保存失败！");
    }

    //二级目录页面
    @RequestMapping("catalog2")
    public String catalog2(){

        return PREFIX + "/catalog2";
    }
    //二级目录信息
    @RequestMapping("catalog2List")
    @ResponseBody
    public Object catalog2List(HttpServletRequest request,Model model){
        //获取页面page limit
        int page = Integer.parseInt(request.getParameter("page").trim());
        int limit = Integer.parseInt(request.getParameter("limit").trim());
        Page page1 = new Page<>(page,limit);
        IPage<Catalog2> catalog2IPage = catalog2ServiceImpl.pageCatalog2(page1);
        return R.return4Page(catalog2IPage);
    }



    //    保存文件

    @RequestMapping("upload")
    @ResponseBody
    public Object upload(@RequestParam("file") MultipartFile multipartFile){
        String url = UploadUtil.uploadFile(multipartFile);
        return R.success(url);
    }

    /**
     *
     * @param id
     * @return teacherList
     */
    @PostMapping("getCatalog2Teacher")
    @ResponseBody
    public List<Teacher> getCatalog2Teacher(@Param("id")String id){
        List<Teacher> teacherList = catalog2ServiceImpl.getCatalog2Teacher(Integer.valueOf(id));

        System.out.println("123");
        return teacherList;
    }

}
