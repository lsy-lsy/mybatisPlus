package com.mybatisplus.demo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatisplus.demo.bean.Catalog1;
import com.mybatisplus.demo.bean.Catalog2;
import com.mybatisplus.demo.bean.Teacher;
import com.mybatisplus.demo.bean.Video;
import com.mybatisplus.demo.service.CatalogService;
import com.mybatisplus.demo.service.TeacherService;
import com.mybatisplus.demo.service.VideoService;
import com.mybatisplus.util.R;
import com.mybatisplus.util.UploadUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/video")
public class VideoController {
    private static final  String PREFIX ="admin/videoManage";

    @Autowired
    VideoService videoServiceImpl;
    @Autowired
    TeacherService teacherServiceImpl;
    @Autowired
    CatalogService catalogServiceImpl;

    //根据二级目录查询getCat2VideoList
    @PostMapping("/getCat2VideoList")
    @ResponseBody
    public List<Video> getCat2VideoList(@Param("id")Integer id){
        List<Video> Cat2VideoList = videoServiceImpl.getCat2VideoList(id);
        return Cat2VideoList;
    }

    //    查询全部科目视频list
    @GetMapping("/getVideoList")
    @ResponseBody
    public Object getVideoList(HttpServletRequest request,@Param("vidName")String vidName){
        int page = Integer.parseInt(request.getParameter("page").trim());
        int limit = Integer.parseInt(request.getParameter("limit").trim());
        Page pages = new Page(page,limit);
        IPage<Video> videoIPage =videoServiceImpl.pageVideoList(pages,vidName);
        return R.return4Page(videoIPage);
    }
//    视频管理页面
    @GetMapping("/video")
    public String tovideoList() {

        return PREFIX + "/video";
    }


    //查询视频详细信息
    @RequestMapping("/selectVideo")
    public String toSelctVideo(@Param("id")Integer id,Model model) {
        Video videos = videoServiceImpl.getById(id);
//        根据视频查询老师
        Teacher teachers =videoServiceImpl.getTeacher(id);
//        查询目录
        Catalog2 catalog2 = catalogServiceImpl.getCatalog2ById(videos.getCatalog2Id());
        Catalog1 catalog1 = catalogServiceImpl.getCatalog1ById(String.valueOf(catalog2.getCatalog1Id()));

        model.addAttribute("videos",videos);
        model.addAttribute("teachers",teachers);
        model.addAttribute("catalog2",catalog2);
        model.addAttribute("catalog1",catalog1);
        return PREFIX + "/selectVideo";
    }

    //    视频编辑页面
    @GetMapping("/editVideo")
    public String updateVideo(@Param("id")String id, Model model) {
        Video video = videoServiceImpl.getById(id);
        //查询老师
        Teacher teacher = videoServiceImpl.getTeacher(Integer.valueOf(id));;
        //全部老师
        List<Teacher> teacherList =teacherServiceImpl.teacherList();
        //一级分类
        List<Catalog1> catalog1List = catalogServiceImpl.getCatalog1();
        //二级分类
        Catalog2 catalog2 = catalogServiceImpl.getCatalog2ById(video.getCatalog2Id());
        //一级分类
        Catalog1 catalog1 = catalogServiceImpl.getCatalog1ById(String.valueOf(catalog2.getCatalog1Id()));
        //查询商品信息
        model.addAttribute("video",video);
        model.addAttribute("teacher",teacher);
        model.addAttribute("catalog2",catalog2);
        model.addAttribute("catalog1",catalog1);
        model.addAttribute("teacherList",teacherList);
        model.addAttribute("catalog1List",catalog1List);
        return PREFIX + "/editVideo";
    }

    //    视频编辑页面
    @PutMapping("/edit")
    @ResponseBody
    public Object updateVideo(Video video,@Param("teaId")String teaId) {
        Boolean update = videoServiceImpl.updateVideo(video,teaId);
        if(update){
            return R.success("修改成功！");
        }else {
            return R.error("修改失败！");
        }
    }


    //删除视频功能
    @DeleteMapping("/del")
    @ResponseBody
    public Object del(@Param("id")Integer id){
        Boolean del = videoServiceImpl.delVideo(id);
        if(del){
            return R.success("删除成功！");
        }else {
            return R.error("删除失败!");
        }
    }
    //    保存视频页面
    @GetMapping("/saveVideo")
    public String saveVideo(Model model){
        //一级分类
        List<Catalog1> catalog1List = catalogServiceImpl.getCatalog1();
        //查询老师
        List<Teacher> teacherList =teacherServiceImpl.teacherList();
        model.addAttribute("teacherList",teacherList);
        model.addAttribute("catalog1List",catalog1List);
        return PREFIX + "/saveVideo";
    }

//    保存视频功能
    @PostMapping("save")
    @ResponseBody
    public Object saveVideo(Video video,@Param("teaId")String teaId){

        Boolean save = videoServiceImpl.videoSave(video,teaId);
        if(save){
            return R.success("保存成功");
        }else {
            return R.error("保存失败");
        }
    }

//    保存文件

    @RequestMapping("saveFile")
    @ResponseBody
    public Object saveFile(@RequestParam("file") MultipartFile multipartFile){
        String url = UploadUtil.uploadFile(multipartFile);
        return R.success(url);
    }

    //    首页
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

}
