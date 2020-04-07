package com.mybatisplus.demo.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@ToString
public class Video {
    @TableId(type = IdType.AUTO)
    private int id;
    private String vidName;
    private String vidVideoUrl;
    private String vidIntroduce; //视频介绍
    private String vidDatum; //资料
    private String vidDatumUrl;//资料地址

    private String vidJudge;
    private String vidRight;
    private String vidPopupTime;

    private String catalog2Id;//视频二级目录

    @TableField(exist = false)
    private String order;
    @TableField(exist = false)
    private List<Catalog2> catalog2List;

    @TableField(exist = false)
    private List<VidTeaValue> vidTeaValues;

    public Video() {
    }


}
