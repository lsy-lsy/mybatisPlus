package com.mybatisplus.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mybatisplus.demo.bean.*;
import com.mybatisplus.demo.mapper.*;
import com.mybatisplus.demo.service.TopicService;
import com.mybatisplus.demo.service.VideoService;
import com.mybatisplus.util.DeleteFile;
import net.sf.jsqlparser.statement.select.Top;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    VideoMapper videoMapper;
    @Autowired
    VidTeaValueMapper vidTeaValueMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    CatTeaValueMapper catTeaValueMapper;
    @Autowired
    TopicService topicServiceImpl;
    @Override
    public IPage<Video> pageVideoList(Page pages,String vidName) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if(vidName != null && !vidName.equals("")) {
            queryWrapper.like("vid_name", vidName);
        }
        IPage<Video> videoIPage = videoMapper.selectPage(pages,queryWrapper);
        System.out.println(videoIPage);
        return videoIPage;
    }

//    根据视频id查询老师

    public Teacher getTeacher(Integer id){
        QueryWrapper<VidTeaValue> queryWrapper = new QueryWrapper();
        queryWrapper.eq("vid_id",id);
        VidTeaValue vidTeaValue = vidTeaValueMapper.selectOne(queryWrapper);

        Teacher teacher =teacherMapper.selectById(vidTeaValue.getTeaId());

        return teacher;
    }

    //保存视频
    @Override
    public Boolean videoSave(Video video, String teaId) {

        int save = videoMapper.insert(video);
        if(save==1){
            //查询保存的视频id
            QueryWrapper<Video>queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("vid_video_url",video.getVidVideoUrl());
            queryWrapper.eq("vid_name",video.getVidName());
            Video video1 = videoMapper.selectOne(queryWrapper);
            //保存老师和视频的信息
            VidTeaValue vidTeaValue = new VidTeaValue();
            vidTeaValue.setTeaId(teaId);
            vidTeaValue.setVidId(String.valueOf(video1.getId()));
            vidTeaValueMapper.insert(vidTeaValue);

            //保存一下二级目录和教师发布视频
            QueryWrapper<CatTeaValue> catTeaValueQueryWrapper = new QueryWrapper<>();
            catTeaValueQueryWrapper.eq("cat2_id",video1.getCatalog2Id());
            catTeaValueQueryWrapper.eq("tea_id",teaId);
            CatTeaValue catTeaValue = catTeaValueMapper.selectOne(catTeaValueQueryWrapper);
            if(catTeaValue==null){
                CatTeaValue catTeaValue1 = new CatTeaValue();
                catTeaValue1.setCat2_id(Integer.parseInt(video1.getCatalog2Id()));
                catTeaValue1.setTea_id(Integer.parseInt(teaId));
                catTeaValueMapper.insert(catTeaValue1);
            }
            return true;
        }
        return false;
    }

    /**
     * 修改视频信息
     * @param video
     * @param teaId
     * @return
     */
    @Override
    public Boolean updateVideo(Video video, String teaId) {
        //更新视频
        int update = videoMapper.updateById(video);
        if(update==1){
            //删除老师视频关系表
            QueryWrapper<VidTeaValue> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("vid_id",video.getId());
            vidTeaValueMapper.delete(queryWrapper);

            //重新插入信息
            VidTeaValue vidTeaValue = new VidTeaValue();
            vidTeaValue.setTeaId(teaId);
            vidTeaValue.setVidId(String.valueOf(video.getId()));
            vidTeaValueMapper.insert(vidTeaValue);
            return true;

        }
        return false;
    }

    //拿到二级目录下的视频
    @Override
    public List<Video> getCat2VideoList(Integer id) {
        QueryWrapper<Video>  queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("catalog2_id",id);
        List<Video> videoList = videoMapper.selectList(queryWrapper);
        return videoList;
    }

    //删除视频跟老师的信息
    @Override
    public Boolean delVideo(Integer id) {
        if(id !=null){
            QueryWrapper<VidTeaValue>queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("vid_id",id);
            vidTeaValueMapper.delete(queryWrapper);
            //删除nginx的视频信息
            Video video = videoMapper.selectById(id);
            if(video.getVidVideoUrl()!=null && !video.getVidVideoUrl().equals("") ){
                DeleteFile deleteFile = new DeleteFile();
                deleteFile.delFile(video.getVidVideoUrl());
            }
            //删除nginx的文件信息
            if(video.getVidDatumUrl()!=null && !video.getVidDatumUrl().equals("") ){
                DeleteFile deleteFile = new DeleteFile();
                deleteFile.delFile(video.getVidDatumUrl());
            }

//            删除视频下的题目
            topicServiceImpl.delTopicVid(id);

            videoMapper.deleteById(id);
            //判断是不是删除全部的视频
            QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
            videoQueryWrapper.eq("catalog2_id",video.getCatalog2Id());
            List<Video> videoList = videoMapper.selectList(videoQueryWrapper);
            if(videoList.size()==0){
                QueryWrapper<CatTeaValue> catTeaValueQueryWrapper = new QueryWrapper<>();
                catTeaValueQueryWrapper.eq("cat2_id",video.getCatalog2Id());
                catTeaValueMapper.delete(catTeaValueQueryWrapper);
            }
            return true;
        }

        return false;
    }

}
