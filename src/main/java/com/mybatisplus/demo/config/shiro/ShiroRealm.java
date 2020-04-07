package com.mybatisplus.demo.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mybatisplus.demo.bean.Admin;
import com.mybatisplus.demo.service.AdminService;
import com.mybatisplus.util.CryptoUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    AdminService adminServiceImpl;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("权限判断-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        System.out.println("身份认证-->MyShiroRealm.doGetAuthenticationInfo()");
        String admName = (String) token.getPrincipal();
        String password = String.valueOf(token.getPassword());
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("adm_name",admName);
        Admin admin = adminServiceImpl.getOne(queryWrapper);
        if(admin==null){
            throw  new AccountException("该用户不存在");
        }
        if(!password.equals(admin.getAdmPwd())){
            throw new AccountException("密码错误");
        }

        return  new SimpleAuthenticationInfo(admin, admin.getAdmPwd(), getName());
    }
}
