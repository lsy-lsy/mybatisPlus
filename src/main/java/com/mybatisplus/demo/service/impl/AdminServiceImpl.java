package com.mybatisplus.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mybatisplus.demo.mapper.AdminMapper;
import com.mybatisplus.demo.service.AdminService;
import com.mybatisplus.demo.bean.Admin;
import com.mybatisplus.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    AdminMapper adminMapper;

//
//    @Override
//    public Object login(String adm_name, String adm_pwd) {
//        QueryWrapper queryWrapper =new QueryWrapper();
//        queryWrapper.eq("adm_name",adm_name);
//        Admin admin = adminMapper.selectOne(queryWrapper);
//        System.out.println(admin);
//        if(admin==null) {
//            return R.error("用户名不存在！");
//        }
//        if(!admin.getAdmPwd().equals(adm_pwd)){
//            return R.error("密码错误！");
//        }
//        return R.success("登陆成功！");
//    }
}
