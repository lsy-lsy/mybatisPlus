package com.mybatisplus.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mybatisplus.demo.bean.Teacher;
import com.mybatisplus.demo.bean.Video;

import java.util.List;

public interface VideoService extends IService<Video> {
    IPage<Video> pageVideoList(Page pages,String vidName);

    Teacher getTeacher(Integer id);

    Boolean videoSave(Video video, String teaId);

    Boolean updateVideo(Video video, String teaId);


    List<Video> getCat2VideoList(Integer id);




    Boolean delVideo(Integer id);
}
