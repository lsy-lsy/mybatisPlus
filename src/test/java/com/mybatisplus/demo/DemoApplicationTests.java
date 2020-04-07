package com.mybatisplus.demo;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mybatisplus.demo.bean.User;
import com.mybatisplus.demo.mapper.AdminMapper;
import com.mybatisplus.demo.mapper.UserMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Autowired
    AdminMapper adminMapper;
    @Test
    void contextLoads() {
//        List<user> userList = userMapper.selectList(null);
//        userList.forEach(System.out::println);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("use_name", "卢");
        List<User> userList = userMapper.selectList(queryWrapper);
        System.out.println(userList);
    }

}
