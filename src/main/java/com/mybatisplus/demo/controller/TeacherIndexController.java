package com.mybatisplus.demo.controller;

import com.mybatisplus.demo.bean.*;
import com.mybatisplus.demo.service.*;
import com.mybatisplus.util.R;
import com.sun.org.apache.xml.internal.resolver.Catalog;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/teacherInd")
public class TeacherIndexController {

    @Autowired
    TeacherService teacherServiceImpl;
    @Autowired
    Catalog2Service catalog2ServiceImpl;
    @Autowired
    CatalogService catalogServiceImpl;
    @Autowired
    CatTeaValueService catTeaValueServiceImpl;

    @Autowired
    VideoService videoServiceImpl;
    @Autowired
    TopicService topicServiceImpl;
    private static final  String PREFIX = "teacherIndex";

    /**
     *教师登陆
     * @param request
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    public Object login (HttpServletRequest request){
        String teaLoginName = request.getParameter("teaLoginName").trim();
        String teaPwd = request.getParameter("teaPwd").trim();
        Teacher teacher =  teacherServiceImpl.teaLogin(teaLoginName);
        if(teacher==null){
            return R.error("用户名不存在！");
        }
        if(!teacher.getTeaPwd().equals(teaPwd)){
            return R.error("密码错误！");
        }
        return R.success(teacher);
    }

    /**
     * 教师发布视频页面
     * @return
     */
    @GetMapping ("/index")
    public String teacherIndex(HttpServletRequest request, HttpSession session,Model model){
        String teaId = request.getParameter("teaId").trim();
        Teacher teacher = teacherServiceImpl.getById(teaId);
        session.setAttribute("teacher",teacher);
        List<Catalog2> catalog2List = catalog2ServiceImpl.getCat2Tea(Integer.valueOf(teaId));
        model.addAttribute("catalog2List",catalog2List);
        return PREFIX + "/teaIndex";
    }

    /**
     * 教师发布课程管理页面
     * @return
     */
    @GetMapping("/catalog2")
    public String catalog2(Model model){
        List<Catalog1> catalog1List = catalogServiceImpl.getCatalog1();
        model.addAttribute("catalog1List",catalog1List);
        return PREFIX + "/catalog2";
    }

    /**
     * 教师发布课程管理页面
     * @return
     */
    @GetMapping("/login")
    public String login(){
        return PREFIX + "/login";
    }

    //教师发布保存
    @PostMapping("/savaCat2")
    @ResponseBody
    public Object catalog2List(@Param("teaId")Integer teaId,Catalog2 catalog2){
        CatTeaValue catTeaValue = new CatTeaValue();
////        判断教师是否已发布
//        保存二级目录
        Catalog2 catalog21  = catalog2ServiceImpl.saveCat2(catalog2);

        catTeaValue.setTea_id(teaId);
        catTeaValue.setCat2_id(catalog21.getId());
        catTeaValueServiceImpl.save(catTeaValue);
        return R.success("发布成功！");
    }

    @GetMapping("/saveVideo")
    public String saveVideo(@Param("cat2Id")String cat2Id,@Param("teaId")String teaId,Model model){
        //查询出二级目录 一级目录 教师。
        Teacher teacher = teacherServiceImpl.getById(teaId);
        Catalog2 catalog2 = catalog2ServiceImpl.getById(cat2Id);
        Catalog1 catalog1ById = catalogServiceImpl.getCatalog1ByCat2Id(String.valueOf(catalog2.getCatalog1Id()));
        model.addAttribute("teacher",teacher);
        model.addAttribute("catalog2",catalog2);
        model.addAttribute("catalog1ById",catalog1ById);

        return PREFIX + "/saveVideo";
    }

    @PostMapping("/delCat2")
    public Object del (@Param("cat2Id")String cat2){
        boolean b = catalog2ServiceImpl.delCat2(Integer.valueOf(cat2));
        if(b) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

    @GetMapping("/selectCourse")
    public String selectCourse(@Param("cat2Id")String cat2Id,@Param("teaId")String teaId,Model model){
        List<Catalog2> catalog2List = catalog2ServiceImpl.getVideoTeacherValue(cat2Id,teaId);
        model.addAttribute("catalog2List",catalog2List);
        return PREFIX + "/selectCourse.html";
    }

    @GetMapping("/selectVideo")
    public String selectVideo(@Param("vidId")String vidId,Model model){
        Video videos = videoServiceImpl.getById(vidId);
        model.addAttribute("videos",videos);
        return PREFIX + "/selectVideo.html";
    }

    @GetMapping("/saveTopic")
    public String saveTopic(@Param("vidId")String vidId,Model model){
        model.addAttribute("vidId",vidId);
        return  PREFIX + "/saveTopic.html";
    }

    //题目保存
    @PostMapping("/saveTopicAns")
    @ResponseBody
    public Object SaveTopic(Topic topic, Answer answer){
        //查询一下是否有重复的问题
        Boolean same = topicServiceImpl.sameTopic(topic.getTopName(),topic.getVidId());
        if(same){
            return R.error("每个视频不能有相同的题目!");
        }
//        //查询视频下的题目不要超过四个
//        Boolean aBoolean = topicServiceImpl.topicExceed(topic.getVidId());
//        if(!aBoolean){
//            return R.error("每个视频不能超过四道题目!");
//        }

//        保存题目和答案
        Boolean save = topicServiceImpl.saveTopic(topic,answer);

        if(save){
            return R.success("保存成功！");
        }
        return R.error("保存失败！");
    }

    @PostMapping("/delVideo")
    @ResponseBody
    public Object delVideo (@Param("vid")String vid){
        Boolean del = videoServiceImpl.delVideo(Integer.valueOf(vid));
        if(del){
            return R.success("删除成功！");
        }else {
            return R.error("删除失败!");
        }
    }

}
