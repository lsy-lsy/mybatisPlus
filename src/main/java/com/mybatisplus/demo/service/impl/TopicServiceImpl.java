package com.mybatisplus.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mybatisplus.demo.bean.Answer;
import com.mybatisplus.demo.bean.Teacher;
import com.mybatisplus.demo.bean.Topic;
import com.mybatisplus.demo.mapper.AnswerMapper;
import com.mybatisplus.demo.mapper.TeacherMapper;
import com.mybatisplus.demo.mapper.TopicMapper;
import com.mybatisplus.demo.mapper.VideoMapper;
import com.mybatisplus.demo.service.TeacherService;
import com.mybatisplus.demo.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements TopicService {

    @Autowired
    VideoMapper videoMapper;
    @Autowired
    TopicMapper topicMapper;
    @Autowired
    AnswerMapper answerMapper;

    @Override
    public IPage<Topic> pageTopicList(Page pages, String topName) {
        QueryWrapper<Topic> queryWrapper = new QueryWrapper();
        if(topName != null && !topName.equals("")){
            queryWrapper.eq("top_name",topName);
        }
        IPage<Topic> topicIPages = topicMapper.selectPage(pages, queryWrapper);
        System.out.println(topicIPages);
        return topicIPages;
    }

    @Override
    public Boolean saveTopic(Topic topic, Answer answer) {
        //先保存课题
        int insert = topicMapper.insert(topic);
        System.out.println(insert);
        if (insert==1){
            //查找出课题id
            QueryWrapper<Topic> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("top_name",topic.getTopName());
            queryWrapper.eq("vid_id",topic.getVidId());
            Topic topic1 = topicMapper.selectOne(queryWrapper);
            answer.setTopId(topic1.getId());
//            根据课程id保存答案
            int insert1 = answerMapper.insert(answer);
            if (insert1==1){
                return true;
            }
        }
        //根据课题保存答案
        return false;
    }

    //修改课题和答案
    @Override
    public Boolean updateTopic(Topic topic, Answer answer) {
//        修改课题
        int i = topicMapper.updateById(topic);
        if(i==1){
//            修改答案
            int i1 = answerMapper.updateById(answer);
            if(i1==1){
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean delTopic(Integer id) {
//        删除课题
        int delTop = topicMapper.deleteById(id);
        if(delTop==1){
            QueryWrapper<Answer> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("top_id",id);
            answerMapper.delete(queryWrapper);
            return true;
        }
        return false;
    }

    /**
     * 根据视频id删除题目
     * @param id
     * @return
     */
    @Override
    public Boolean delTopicVid(Integer id) {
//        删除课题
        QueryWrapper<Topic> topicQueryWrapper = new QueryWrapper<>();
        topicQueryWrapper.eq("vid_id",id);
        int delTop =topicMapper.delete(topicQueryWrapper);
        if(delTop==1){
            QueryWrapper<Answer> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("top_id",id);
            answerMapper.delete(queryWrapper);
            return true;
        }
        return false;
    }

    /**
     * 查询视频下的题目不要超过四个
     * @param vidId
     * @return
     */
    @Override
    public Boolean topicExceed(int vidId) {
        QueryWrapper<Topic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("vid_id",vidId);
        List<Topic> topics = topicMapper.selectList(queryWrapper);
        if(topics.size()>=4){
            return false;
        }
            return true;
    }

//    每个视频不能有相同的题目
    @Override
    public Boolean sameTopic(String topName, int vidId) {
        QueryWrapper<Topic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("top_name",topName);
        queryWrapper.eq("vid_id",vidId);
        Topic topic = topicMapper.selectOne(queryWrapper);
        if(topic==null){
            return false;
        }
        return true;
    }

    //根据id查询题目
    @Override
    public List<Topic> getTopic(String vid,String uId) {
        List<Topic> topicList = topicMapper.getTopic(vid,uId);
        return topicList;
    }

    //查询同个视频不能有相同的答案

}
