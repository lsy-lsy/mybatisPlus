package com.mybatisplus.demo.controller;
import com.mybatisplus.demo.bean.*;
import com.mybatisplus.demo.service.*;
import com.mybatisplus.demo.service.impl.Catalog2ServiceImpl;
import com.mybatisplus.util.R;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/index")
public class indexController {
    @Autowired
    Catalog2Service catalog2ServiceImpl;
    @Autowired
    VideoService videoServiceImpl;
    @Autowired
    UserService userServiceImpl;
    @Autowired
    CatalogService catalogServiceImpl;
    @Autowired
    TopicService topicServiceImpl;
    @Autowired
    Cat2UseValueService cat2UseValueServiceImpl;
    @Autowired
    TopUseValueService topUseValueServiceImpl;


    private static final String PREFIX = "/index";

    //    登陆页面
    @GetMapping("/lo")
    public String lo(){
        return PREFIX + "/login.html";
    }

    @PostMapping("login")
    @ResponseBody
    public Object login (HttpServletRequest request){
        String useLoginName = request.getParameter("useLoginName").trim();
        String usePwd = request.getParameter("usePwd").trim();
        User user =  userServiceImpl.userLogin(useLoginName);
        if(user==null){
            return R.error("用户名不存在！");
        }
        if(!user.getUsePwd().equals(usePwd)){
            return R.error("密码错误！");
        }
        return R.success(user);
    }


    /**
     * 访问首页
     * @param request
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/in")
    public String index(HttpServletRequest request, HttpSession session,Model model){
//        查询是否登陆
        String useId = request.getParameter("id");
        if(useId!=null && !useId.equals("")){
            User user = userServiceImpl.getById(useId);
            session.setAttribute("user",user);
        }
//        课程中心查询出一级目录
        List<Catalog1> catalog1List = catalogServiceImpl.getCatalog1();
//        读取cat1目录
        model.addAttribute("catalog1List",catalog1List);
        List<Catalog2> catalog2List =  catalog2ServiceImpl.getCat2TeacherList();
        model.addAttribute("catalog2List",catalog2List);

        return PREFIX + "/index.html";
    }

//    转发info视频详情页
    @GetMapping("/info")
    public String info(@Param("cat2Id")String cat2Id,@Param("teaId")String teaId,Model model){
        System.out.println(teaId);
        List<Catalog2> catalog2List = catalog2ServiceImpl.getVideoTeacherValue(cat2Id,teaId);
        //
        Catalog2 catalog2 = null;
        for(int i = 0 ; i < 1;i++){
            catalog2 = catalog2List.get(i);
        }
        List<Video> videoList =  catalog2.getVideoList();
        Video video = null;
        for(int i = 0 ; i < 1;i++){
            video = videoList.get(i);
        }

        System.out.println(video);
//        读取cat2视频
        model.addAttribute("catalog2List",catalog2List);
        model.addAttribute("video",video);
        System.out.println("123");
        return PREFIX + "/info.html";
    }

    // 拿取单个商品
    @PostMapping("/video")
    @ResponseBody
    public Object video(@Param("id")String id){
        Video videos = videoServiceImpl.getById(id);
        if(videos!=null){
            return R.success(videos);
        }
        return R.error("视频不存在！");
    }

    //清楚user登陆信息 退出功能
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return PREFIX + "/login.html";
    }

    //用户个人页面
    @RequestMapping("myindex")
    public String myindex(HttpServletRequest request,Model model){
        Integer useId = Integer.valueOf(request.getParameter("useId").trim());
//        根据用户id查询报名的课程
        List<Catalog2> catalog2List = catalog2ServiceImpl.getCat2User(useId);
        model.addAttribute("catalog2List",catalog2List);
        return PREFIX + "/myindex.html";
    }

    @GetMapping("infoSave")
    public String infoSave (HttpServletRequest request,Model model){
        String uId = request.getParameter("id").trim();
        model.addAttribute("uId",uId);
        List<Catalog1> catalog1List = catalogServiceImpl.getCatalog1();
        model.addAttribute("catalog1List",catalog1List);
        return PREFIX + "/infoSave.html";
    }

    /**
     * 跳到课程中心课程页面
     * @param model
     * @return
     */
    @GetMapping("/search")
    public String search(Model model){
        List<Catalog1> catalog1 = catalogServiceImpl.getCatalog1();
        model.addAttribute("catalog1",catalog1);
        return PREFIX + "/search.html";
    }

    //根据一级目录名称拿到二级目录
//    getCat1Cata2log
    @PostMapping("/getCat1Cata2log")
    @ResponseBody
    public Object getCat1Cata2log(HttpServletRequest request){
        String id = request.getParameter("id");
        List<Catalog2> catalog2List = catalog2ServiceImpl.getCat1Cata2log(id);

//        System.out.println(id);
        if(catalog2List !=null){
            return R.success(catalog2List);
        }

        return R.error("暂无视频！");
    }

    //查询所有一级目录,二级目录并由老师发布课程
    @PostMapping("/getCat1Cata2logAll")
    @ResponseBody
    public Object getCat1Cata2logAll(@Param("cat2Name") String cat2Name){
        List<Catalog2> catalog2List = catalog2ServiceImpl.getCat1Cata2logAll(cat2Name);

//        System.out.println(id);
        if(catalog2List !=null){
            return R.success(catalog2List);
        }
        return R.error("暂无视频！");
    }


    //保存用户课程
    @PostMapping("/saveCata2TeacherUser")
    @ResponseBody
    public Object saveCata2TeacherUser(Cat2UseValue cat2UseValue){
        //保存之前进行判断，判断学生是否已报名改教师课程
        Boolean aBoolean = cat2UseValueServiceImpl.getCat2UseTeacher(cat2UseValue.getUseId(),cat2UseValue.getTeaId(),cat2UseValue.getCat2Id());
        if(aBoolean){
            return R.error("已存在该老师课程，请勿重新报名！");
        }
        //        cata
        Boolean save = cat2UseValueServiceImpl.save(cat2UseValue);

//        System.out.println(id);
        if(save){
            return R.success("保存成功！");
        }
        return R.error("保存失败！");
    }

    //    根据视频id获取题目
    @PostMapping("/getTopic")
    @ResponseBody
    public Object getTopic(HttpServletRequest request){
        String vId = request.getParameter("vId");
        String uId = request.getParameter("uId");
        List<Topic> topicList = topicServiceImpl.getTopic(vId,uId);
        for (Topic topic:topicList) {
            System.out.println(topic);
        }
        if(topicList!=null){
            return R.success(topicList);
        }

        return R.error("该视频暂无课程!");
    }

    @PostMapping("saveAnswerUserValue")
    @ResponseBody
    public Object saveAnswerUserValue(HttpServletRequest request){
        String uId =request.getParameter("uId");
        //答案
        String ansCorrect1 = request.getParameter("ansCorrect1");
        String ansCorrect2 = request.getParameter("ansCorrect2");
        String ansCorrect3 = request.getParameter("ansCorrect3");
        String ansCorrect4 = request.getParameter("ansCorrect4");
//        获取对应的题目Id
        String topId1 = request.getParameter("topId1");
        String topId2 = request.getParameter("topId2");
        String topId3 = request.getParameter("topId3");
        String topId4 = request.getParameter("topId4");

        //保存课题数据库
        TopUseValue topUseValue1 = new TopUseValue(uId,topId1,ansCorrect1);
        topUseValueServiceImpl.save(topUseValue1);

        TopUseValue topUseValue2 = new TopUseValue(uId,topId2,ansCorrect2);
        topUseValueServiceImpl.save(topUseValue2);

        TopUseValue topUseValue3 = new TopUseValue(uId,topId3,ansCorrect3);
        topUseValueServiceImpl.save(topUseValue3);

        TopUseValue topUseValue5 = new TopUseValue(uId,topId4,ansCorrect4);
        topUseValueServiceImpl.save(topUseValue5);



        return R.success("提交成功!");
    }

    @GetMapping("/axaj")
    public Object axaj(){
        return PREFIX + "/ajax.html";
    }


    @GetMapping("/user")
    @ResponseBody
    public Object uses(){
        UserData userData = new UserData();
        return userData;
    }

    @GetMapping("/register")
    public String register(){
        return  PREFIX +"/register.html";
    }

    //    保存用户
    @PostMapping(value = "/saveUser")
    @ResponseBody
    public Object saveUser(User user){
        Boolean save = userServiceImpl.save(user);
        if(save){
            return R.success("注册成功！");
        }else{
            return R.error("注册失败！");
        }
    }

    @PostMapping(value = "/deleteCat2")
    @ResponseBody
    public Object deleteCat2(@Param("cat2Id") String cat2 ,@Param("useId")String useId){
        Boolean deleteCat2 = cat2UseValueServiceImpl.deleteCat2(cat2,useId);
        if(deleteCat2){
            return R.success("取消报名成功！");
        }else{
            return R.error("取消报名失败！");
        }
    }
}
