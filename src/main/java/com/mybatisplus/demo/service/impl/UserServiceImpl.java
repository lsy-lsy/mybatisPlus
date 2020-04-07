package com.mybatisplus.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mybatisplus.demo.bean.Cat2UseValue;
import com.mybatisplus.demo.bean.TopUseValue;
import com.mybatisplus.demo.bean.User;
import com.mybatisplus.demo.mapper.Cat2UseValueMapper;
import com.mybatisplus.demo.mapper.TopUseValueMapper;
import com.mybatisplus.demo.mapper.UserMapper;
import com.mybatisplus.demo.service.Cat2UseValueService;
import com.mybatisplus.demo.service.TopUseValueService;
import com.mybatisplus.demo.service.UserService;
import com.mybatisplus.util.DeleteFile;
import com.mybatisplus.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    Cat2UseValueMapper cat2UseValueMapper;

    @Autowired
    TopUseValueMapper topUseValueMapper;


    @Override
    public IPage<User> pageUserList(Page page, String useName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        if(useName != null && !useName.equals("")) {
            queryWrapper.like("use_name", useName);
        }
        IPage<User> userIPage = userMapper.selectPage((IPage<User>) page,queryWrapper);
        return userIPage;
    }


    @Override
    public int saveUser(User user) {
        int mgs = userMapper.insert(user);
        if(mgs==1){
            return 200;
        }
        return 0;
    }

    @Override
    public User userLogin(String useLoginName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("use_login_name",useLoginName);
        User user = userMapper.selectOne(queryWrapper);
        if(user !=null){
            return user;
        }
        return null;
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @Override
    public Boolean updateUser(User user) {
        //判断用户是否修改图片，是删除原来图片
//        先查询原来的图片地址
        User user1 = userMapper.selectById(user.getId());
        if(user1.getUseImage()!=null&& !user1.getUseImage().equals("")){
            if(user.getUseImage()!=user1.getUseImage()&&!user.getUseImage().equals("")){
                DeleteFile deleteFile = new DeleteFile();
                deleteFile.delFile(user1.getUseImage());
            }
        }
        int i = userMapper.updateById(user);
        if(i == 1){
            return true;
        }
        return false;
    }

    //删除用户跟用户报名等等
    @Override
    public Boolean delUseId(Integer id) {
        if(id!=null&!id.equals("")){
            //删除用户报名二级目录id
            QueryWrapper<Cat2UseValue> cat2UseValueQueryWrapper = new QueryWrapper<>();
            cat2UseValueQueryWrapper.eq("use_id",id);
            cat2UseValueMapper.delete(cat2UseValueQueryWrapper);
//            删除已经做题目id
            QueryWrapper<TopUseValue> topUseValueQueryWrapper = new QueryWrapper<>();
            topUseValueQueryWrapper.eq("use_id",id);
            topUseValueMapper.delete(topUseValueQueryWrapper);
            //删除用户
            userMapper.deleteById(id);

            return true;
        }
        return false;

    }
}
