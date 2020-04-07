package com.mybatisplus.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatisplus.demo.bean.Catalog1;
import com.mybatisplus.demo.bean.Catalog2;
import com.mybatisplus.demo.bean.User;
import com.mybatisplus.demo.service.Catalog2Service;
import com.mybatisplus.demo.service.CatalogService;
import com.mybatisplus.demo.service.UserService;
import com.mybatisplus.util.R;
import com.mybatisplus.util.UploadUtil;
import com.mybatisplus.util.UploadingFile;
import com.sun.org.apache.xml.internal.resolver.Catalog;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userServiceImpl;

    @Autowired
    Catalog2Service catalog2ServiceImpl;
    @Autowired
    CatalogService catalogServiceImpl;

    private  static final String PREFIX ="admin/userManage";


    @RequestMapping("/getUserList")
    @ResponseBody
    public Object getUserList(HttpServletRequest request,@Param("useName")String useName){
        int page=(Integer.parseInt(request.getParameter("page").trim()));
        int limit=(Integer.parseInt(request.getParameter("limit").trim()));
        Page pages = new Page<>(page,limit);
        IPage<User> userIPage =userServiceImpl.pageUserList(pages,useName);
//        userIPage.setTotal(userIPage.getRecords().size());
        return R.return4Page(userIPage);
    }

    //删除用户
    @DeleteMapping("/del")
    @ResponseBody
    public Object del(@Param("id")Integer id){
        Boolean del = userServiceImpl.delUseId(id);
        if(del) {
            return R.success("删除成功！");
        }else {
            return R.error("删除失败！");
        }
    }

    //    保存用户页面
    @RequestMapping("/saveUserView")
    public String toCreate(){
        return PREFIX + "/saveUser";
    }


    /**
     * 查询用户更多详细页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/selectUser")
    public String selectUser(@Param("id") Integer id ,Model model){
        //用户信息
        User user = userServiceImpl.getById(id);


        //用户报名课程信息
        List<Catalog2> catalog2List = catalog2ServiceImpl.getCat2User(id);
        model.addAttribute("user",user);
        model.addAttribute("catalog2List",catalog2List);
        return PREFIX + "/selectUser";
    }




    //  用户列表页面
    @RequestMapping("/toUserList")
    public String toUserList(){
        return PREFIX + "/userList";
    }

    //    保存用户
    @PostMapping(value = "/saveUser")
    @ResponseBody
    public Object saveUser(User user){
        Boolean save = userServiceImpl.save(user);
        if(save){
            return R.success("添加用户成功！");
        }else{
            return R.error("添加用户失败！");
        }
    }


    //    更新用户
    @RequestMapping(value = "/updateUser")
    @ResponseBody
    public Object updateUser(User user){

        Boolean update = userServiceImpl.updateUser(user);
        if(update){
            return R.success("更新用户成功！");
        }else{
            return R.error("更新用户失败！");
        }
    }

    // 用户修改页面
    @RequestMapping("/toUpdateUser")
    public String toUpdateUser(@Param("id")Integer id, Model model){
        User user = userServiceImpl.getById(id);
        System.out.println(user);
        model.addAttribute("user",user);

        System.out.println(id);
        return PREFIX + "/updateUser";
    }

    /**
     * 跳转到用户添加课程页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/saveCat1User",method = RequestMethod.GET)
    public String saveCata1User(HttpServletRequest request,Model model){
        String uId = request.getParameter("id").trim();
        model.addAttribute("uId",uId);
        List<Catalog1> catalog1List = catalogServiceImpl.getCatalog1();
        model.addAttribute("catalog1List",catalog1List);
        return PREFIX + "/saveCata1User";
    }

    @PostMapping("/saveCat2")
    @ResponseBody
    public Object saveCat1Use(HttpServletRequest request){
        String uId = request.getParameter("uId").trim();
        String catalog2Id = request.getParameter("catalog2Id").trim();
        String teaId = request.getParameter("teaId").trim();


        Boolean getCat1Use = catalog2ServiceImpl.getCat1Use(uId,teaId,catalog2Id);
        if(getCat1Use){
            return R.error("该老师的课程已存在，请重新选择");
        }
        Boolean saveCat1Use = catalog2ServiceImpl.getSaveCat1Use(uId,teaId,catalog2Id);
        if(saveCat1Use){
            return R.success("添加课程成功！");
        }
        return R.error("添加课程失败！");
    }


    @PostMapping("/uploadImg")
    @ResponseBody
    public Object uploadImg(@RequestParam("file") MultipartFile multipartFile){

        String url = UploadingFile.uploadFile(multipartFile,"headPortrai/");
        return R.success(url);
    }


}
