package com.mybatisplus.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mybatisplus.demo.bean.*;
import com.mybatisplus.demo.mapper.*;
import com.mybatisplus.demo.service.Catalog2Service;
import com.mybatisplus.util.DeleteFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Catalog2ServiceImpl extends ServiceImpl<Catalog2Mapper,Catalog2> implements Catalog2Service {

    @Autowired
    Catalog2Mapper catalog2Mapper;
    @Autowired
    Cat2UseValueMapper cat2UseValueMapper;
    @Autowired
    CatTeaValueMapper catTeaValueMapper;
    @Autowired
    VideoMapper videoMapper;
    @Autowired
    TopicMapper topicMapper;
    @Autowired
    AnswerMapper answerMapper;




    @Override
    public IPage<Catalog2> pageCatalog2(Page page) {
        QueryWrapper<Catalog2> queryWrapper = new QueryWrapper<>();
        IPage<Catalog2> catalog2IPage = catalog2Mapper.selectPage(page, queryWrapper);
        return catalog2IPage;
    }

    //查询二级目录是否存在
    @Override
    public Boolean getCat2Name(Catalog2 catalog2) {
        QueryWrapper<Catalog2> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cat2_name",catalog2.getCat2Name());
        queryWrapper.eq("catalog1_id",catalog2.getCatalog1Id());
        Catalog2 cat2Name = catalog2Mapper.selectOne(queryWrapper);
        if(cat2Name == null){
            return false;
        }
        return true;
    }

    @Override
    public boolean delCat2(Integer id) {
        Catalog2 catalog2 = catalog2Mapper.selectById(id);
        if(catalog2.getCat2Img()!=null && !catalog2.getCat2Img().equals("")){
            DeleteFile deleteFile = new DeleteFile();
            deleteFile.delFile(catalog2.getCat2Img());
        }
        int i = catalog2Mapper.deleteById(id);
        //生产二级目录的同时 删除 二级目录下的视频，老师信息，课题，答案
        if(i !=0 ){
            //生产二级目录的同时 删除 二级目录下的视频，老师发布课程，学生报名课程，课题，答案
//            根据cat2Id查询对应的视频
            QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
            videoQueryWrapper.eq("catalog2_id",id);
            List<Video> videoList = videoMapper.selectList(videoQueryWrapper);
            if(videoList.size()>0){
                for(Video video :videoList){
                    //逐个查出视频下题目
                    QueryWrapper<Topic> topicQueryWrapper = new QueryWrapper<>();
                    topicQueryWrapper.eq("vid_id",video.getId());
                    List<Topic> topicList = topicMapper.selectList(topicQueryWrapper);
                    //逐个删除题目的答案
                    if(topicList.size()>0){
                        for(Topic topic : topicList){
                            QueryWrapper<Answer> answerQueryWrapper = new QueryWrapper<>();
                            answerQueryWrapper.eq("top_id",topic.getId());
                            answerMapper.delete(answerQueryWrapper);
                        }
                    }
                    //没有就注解删除题目
                    for(Topic topic : topicList){
//                        QueryWrapper<Topic> topicQueryWrapper1 = new QueryWrapper<>();
//                        topicQueryWrapper1.setEntity(topic);
                        topicMapper.deleteById(topic.getId());
                    }
//                    删除视频
                    videoMapper.deleteById(video.getId());
                }
            }
//            删除老师cat2发布课程，
            QueryWrapper<CatTeaValue> catTeaValueQueryWrapper = new QueryWrapper<>();
            catTeaValueQueryWrapper.eq("cat2_id",id);
            catTeaValueMapper.delete(catTeaValueQueryWrapper);
//            学生cat2报名课程
            QueryWrapper<Cat2UseValue> cat2UseValueQueryWrapper = new QueryWrapper<>();
            cat2UseValueQueryWrapper.eq("cat2_id",id);
            cat2UseValueMapper.delete(cat2UseValueQueryWrapper);
            return true;
        }
        //删除nginx代理图片
        //删除数据库数据
        return false;
    }

    @Override
    public Boolean editCat2(Catalog2 catalog2) {
        //判断一下数据库的图片地址有没有改变
        Catalog2 catalog21 = catalog2Mapper.selectById(catalog2.getId());
        if(catalog21.getCat2Img() != null && !catalog21.getCat2Img().equals("")){
            if(catalog21.getCat2Img() != null && !catalog21.getCat2Img().equals(catalog2.getCat2Img())){
                DeleteFile deleteFile = new DeleteFile();
                deleteFile.delFile(catalog21.getCat2Img());
            }

        }
        int i = catalog2Mapper.updateById(catalog2);
        if(i != 0){
            return true;
        }
        return false;
    }

    /**
     *     查询教师发布的课程系列
     * @param id
     * @return catalog2List
     */
    @Override
    public List<Catalog2> getCat2Tea(Integer id) {
        List<Catalog2> catalog2List = catalog2Mapper.getCat2Tea(id);

        System.out.println(catalog2List);
        return catalog2List;
    }

    /**
     *     查询用户报名的课程系列
     * @param id
     * @return
     */

    @Override
    public List<Catalog2> getCat2User(Integer id) {
        //用户报名课程信息和报名课程老师信息
        List<Catalog2> catalog2List = catalog2Mapper.getCat2User(id);

        return catalog2List;
    }

    //查询全部二级和老师姓名目录
    @Override
    public List<Catalog2> getCat2TeacherList() {
        List<Catalog2> catalog2List = catalog2Mapper.getCat2TeacherList();
        return catalog2List;
    }

    /**
     * 根据系列cat2id和教师id查询 教师发布的视频
     * @param cat2Id
     * @param teaId
     * @return
     */
    @Override
    public List<Catalog2> getVideoTeacherValue(String cat2Id, String teaId) {
        List<Catalog2> catalog2List = catalog2Mapper.getVideoTeacherValue(cat2Id,teaId);
        return catalog2List;
    }


    /**
     * 添加课程
     * @param uId
     * @param catalog2Id
     * @return
     */
    @Override
    public Boolean getSaveCat1Use(String uId,String teaId, String catalog2Id) {
        Cat2UseValue cat2UseValue = new Cat2UseValue();
        cat2UseValue.setUseId(Integer.parseInt(uId));
        cat2UseValue.setCat2Id(Integer.parseInt(catalog2Id));
        cat2UseValue.setTeaId(Integer.parseInt(teaId));
        int insert = cat2UseValueMapper.insert(cat2UseValue);
        if(insert == 1){
            return true;
        }
        return false;
    }

    /**
     * 查询用户是否报名该课程
     * @param uId
     * @param catalog2Id
     * @return
     */
    @Override
    public Boolean getCat1Use(String uId,String teaId ,String catalog2Id) {
        QueryWrapper<Cat2UseValue> cat2UseValueQueryWrapper = new QueryWrapper<>();
        cat2UseValueQueryWrapper.eq("use_id",uId);
        cat2UseValueQueryWrapper.eq("cat2_id",catalog2Id);
        cat2UseValueQueryWrapper.eq("tea_id",teaId);
        Cat2UseValue cat2UseValue = cat2UseValueMapper.selectOne(cat2UseValueQueryWrapper);
        if(cat2UseValue==null){
            return false;
        }
        return true;
    }


    /**
     * 根据二级目录id查询发布的系列课程的教师
     * @param id
     * @return
     */
    @Override
    public List<Teacher> getCatalog2Teacher(Integer id) {
        List<Teacher> teacherList = catTeaValueMapper.getCatalog2Teacher(id);
        return teacherList;
    }


    /**
     *根据一级目录id拿到所有的二级目录
     * @param id
     * @return
     */
    @Override
    public List<Catalog2> getCat1Cata2log(String id) {

        List<Catalog2> catalog2List = catalog2Mapper.getCat1Cata2log(id);
        if(catalog2List !=null){
           return catalog2List;
        }
        return null;
    }

    /**
     * 拿取一级目录,二级目录并有老师发布的课程
     * @return
     */
    @Override
    public List<Catalog2> getCat1Cata2logAll(String cat2Name) {
        List<Catalog2> catalog2List = catalog2Mapper.getCat1Cata2logAll(cat2Name);
        return catalog2List;
    }



    @Override
    public Catalog2 saveCat2(Catalog2 catalog2) {
        catalog2Mapper.insert(catalog2);
//        查询出刚才保存的id;
        QueryWrapper<Catalog2> catalog2QueryWrapper = new QueryWrapper<>();
        catalog2QueryWrapper.eq("cat2_img",catalog2.getCat2Img());
        catalog2QueryWrapper.eq("cat2_name",catalog2.getCat2Name());
        Catalog2 catalog21 = catalog2Mapper.selectOne(catalog2QueryWrapper);
        return catalog21;
    }


}
