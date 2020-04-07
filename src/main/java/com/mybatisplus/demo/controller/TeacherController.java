package com.mybatisplus.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatisplus.demo.bean.Catalog2;
import com.mybatisplus.demo.bean.Teacher;
import com.mybatisplus.demo.service.Catalog2Service;
import com.mybatisplus.demo.service.TeacherService;
import com.mybatisplus.demo.service.VideoService;
import com.mybatisplus.util.R;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherServiceImpl;
    @Autowired
    VideoService videoServiceImpl;
    @Autowired
    Catalog2Service catalog2ServiceImpl;


    private static  final  String PREFIX="admin/teacherManage";
    @GetMapping("/teacher")
    public String teacher(){
        return PREFIX + "/teacher";
    }

    @GetMapping("/teacherList")
    @ResponseBody
    public Object teacherList(HttpServletRequest request, @Param("teaName") String teaName){
        int page = Integer.parseInt(request.getParameter("page").trim());
        int limit = Integer.parseInt(request.getParameter("limit").trim());
        Page pages = new Page<>(page,limit);
        IPage<Teacher> iPage = teacherServiceImpl.pageTeacherList(pages,teaName);
        return R.return4Page(iPage);
    }

    //教师保存页面
    @GetMapping("/saveTeacher")
    public String saveTeacher(){
        return PREFIX + "/saveTeacher";
    }

    //保存功能
    @PostMapping("/save")
    @ResponseBody
    public Object save(Teacher teacher) {

        boolean save = teacherServiceImpl.save(teacher);
        if (save) {
            return R.success("保存成功!");
        } else {
            return R.error("保存失败！");
        }
    }

    //删除功能！
    @DeleteMapping("/del")
    @ResponseBody
    public Object del(@Param("id")Integer id){
        boolean del = teacherServiceImpl.removeById(id);
        if(del){
            return R.success("删除成功！");
        }
        return R.error("删除失败！");
    }

    //查询教师页面
    @GetMapping("/select")
    public String select(Model model,@Param("id")Integer id){
        Teacher teacher = teacherServiceImpl.getById(id);
        model.addAttribute("teacher",teacher);
        //查询教师发布的二级目录
//        List<Video> videoList = videoServiceImpl.getVidTea(id);
        List<Catalog2> catalog2List =catalog2ServiceImpl.getCat2Tea(id);
        model.addAttribute("catalog2List",catalog2List);

        //二级目录
        return PREFIX + "/selectTeacher";
    }

    //修改教师页面
    @GetMapping("/editTeacher")
    public String update(Model model,@Param("id")Integer id){
        Teacher teacher = teacherServiceImpl.getById(id);
        model.addAttribute("teacher",teacher);
        return PREFIX + "/editTeacher";
    }

    /**
     *
     * @param teacher
     * @return
     */
    @PutMapping("/edit")
    @ResponseBody
    public Object edit(Teacher teacher){
        boolean updateById = teacherServiceImpl.updateById(teacher);
        if(updateById){
            return R.success("修改成功！");
        }
        return R.error("修改成功！");

    }

}
