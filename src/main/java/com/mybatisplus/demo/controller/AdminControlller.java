package com.mybatisplus.demo.controller;

import com.mybatisplus.demo.bean.Admin;
import com.mybatisplus.demo.service.AdminService;
import com.mybatisplus.util.R;
import com.mybatisplus.util.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminControlller {

    @Autowired
    AdminService adminServiceImpl;

    @RequestMapping("/toLogin")
    @ResponseBody
    public Object toLogin(HttpServletRequest request){
        String admName = request.getParameter("adm_name").trim();
        String admPwd = request.getParameter("adm_pwd").trim();
        Subject currentAdmin = ShiroUtil.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(admName,admPwd.toCharArray());
        try {
            currentAdmin.login(token);
        }catch (AccountException e){
            return R.error(e.getMessage());
        }

        return R.success("登陆成功！");
    }



    //登陆
    @RequestMapping("/adminLogin")
    public String adminLogin(){

        return "/admin/login";
    }

    //首页
    @RequestMapping("/index")
    public String index(HttpServletRequest request){
        Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
//        System.out.println(admin);
        request.setAttribute("admin",admin);
        return "admin/index";
    }

    @RequestMapping("logout")
    private String logout(){
         ShiroUtil.getSubject().logout();
        return "/admin/login";
    }

}
