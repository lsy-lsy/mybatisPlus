package com.mybatisplus.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mybatisplus.demo.bean.User;
import com.mybatisplus.demo.bean.Video;

import java.util.List;

public interface VideoMapper extends BaseMapper<Video> {
    List<Video> getVidTea(Integer id);
}
