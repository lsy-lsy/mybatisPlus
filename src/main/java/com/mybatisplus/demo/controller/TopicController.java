package com.mybatisplus.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatisplus.demo.bean.*;
import com.mybatisplus.demo.mapper.AnswerMapper;
import com.mybatisplus.demo.service.AnswerService;
import com.mybatisplus.demo.service.CatalogService;
import com.mybatisplus.demo.service.TopicService;
import com.mybatisplus.demo.service.VideoService;
import com.mybatisplus.util.R;
import com.sun.org.apache.xml.internal.resolver.Catalog;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/topic")
public class TopicController {
    private  static final String PREFIX = "admin/topicManage/";

    @Autowired
    CatalogService catalogServiceImpl;
    @Autowired
    TopicService topicServiceImpl;
    @Autowired
    VideoService videoServiceImpl;
    @Autowired
    AnswerService answerServiceImpl;


    //题目主页面
    @RequestMapping("/toTopicList")
    public String toToipicList(){
        return PREFIX + "topicList";
    }

    //查询全部题目
    @RequestMapping("/topicList")
    @ResponseBody
    public Object toTopicList(HttpServletRequest request, @Param("topName") String topName){
        int page = Integer.parseInt(request.getParameter("page").trim());
        int limit = Integer.parseInt(request.getParameter("limit").trim());
        Page pages = new Page<>(page,limit);
        IPage<Topic> topicIPage = topicServiceImpl.pageTopicList(pages,topName);
        topicIPage.setTotal(topicIPage.getRecords().size());
        return R.return4Page(topicIPage);
    }


    //题目修改页面
    @RequestMapping("/toUpdateTopic")
    public String toUpdateTopic(@Param("id")Integer id,Model model){
        //课题信息
        Topic topic = topicServiceImpl.getById(id);
        model.addAttribute("topic",topic);
//        视频信息
        Video video = videoServiceImpl.getById(topic.getVidId());
        model.addAttribute("video",video);
        // 答案信息
        QueryWrapper<Answer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("top_id",topic.getId());
        Answer answer = answerServiceImpl.getOne(queryWrapper);
        model.addAttribute("answer",answer);

        return PREFIX + "updateTopic";
    }

    //修改功能
    @RequestMapping("/editTopic")
    @ResponseBody
    public Object deitTopic(@Param("ansId")Integer ansId,@Param("vidId") Integer vidId, Topic topic,Answer answer){
        //设置一下答案id和题目id
        answer.setId(ansId);answer.setTopId(topic.getId());
        //设置一下视频id
        topic.setVidId(vidId);

        Boolean edit = topicServiceImpl.updateTopic(topic, answer);
        if(edit){
            return R.success("修改成功!");
        }
        return R.error("修改失败!");
    }

    @DeleteMapping("/delTopic")
    @ResponseBody
    public Object delTopic(@Param("id")Integer id){
        Boolean del = topicServiceImpl.delTopic(id);
        if(del){
            return R.success("删除成功！");
        }
        return R.error("删除失败！");
    }

    //查询题目更多信息页面
    @GetMapping("/toSelectTopic")
    public String toSelectTopic(Model model,@Param("id")Integer id){
        //课题信息
        Topic topic = topicServiceImpl.getById(id);
        model.addAttribute("topic",topic);
//        视频信息
        Video video = videoServiceImpl.getById(topic.getVidId());
        model.addAttribute("video",video);
        // 答案信息
        QueryWrapper<Answer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("top_id",topic.getId());
        Answer answer = answerServiceImpl.getOne(queryWrapper);
        model.addAttribute("answer",answer);
        return PREFIX + "selectTopic";
    }

    //题目保存页面
    @GetMapping("/topicView")
    public String saveTopic(Model model){
        //一级分类
        List<Catalog1> catalog1List = catalogServiceImpl.getCatalog1();
        model.addAttribute("catalog1List",catalog1List);
        return PREFIX + "saveTopic";
    }

    //题目保存
    @PostMapping("/save")
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

}
