package com.mybatisplus.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mybatisplus.demo.bean.User;

public interface UserService extends IService<User> {
    IPage<User> pageUserList(Page page, String useName);


    int saveUser(User user);

    User userLogin(String useLoginName);

    Boolean updateUser(User user);

    Boolean delUseId(Integer id);
}
