package com.mybatisplus.demo.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.mybatisplus.util.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
public class Catalog2 extends Page implements Serializable {
    private int id;
    private String cat2Name;
    private String cat2Img;
    private int catalog1Id;
    @TableField(exist = false)
    private String order;

    //二级目录和老师
    @TableField(exist = false)
    private List<VidTeaValue> cat2TeaValues;

    //二级目录和学生
    @TableField(exist = false)
    private List<Cat2UseValue> cat2UseValues;

    //二级目录和老师
    @TableField(exist = false)
    private Teacher teacher;

    //二级目录和老师
    @TableField(exist = false)
    private List<Teacher> teacherList;

    //视频
    @TableField(exist = false)
    private List<Video> videoList;

    //视频
    @TableField(exist = false)
    private Video video;


    public Catalog2() {
    }
}
