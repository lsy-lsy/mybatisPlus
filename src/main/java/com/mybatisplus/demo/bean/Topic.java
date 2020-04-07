package com.mybatisplus.demo.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.mybatisplus.util.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@ToString
public class Topic extends Page implements Serializable {
    private int id;
    private String topName;
    private int vidId;

    @TableField(exist = false)
    public Answer answer;

    @TableField(exist = false)
    public TopUseValue topUseValue;

    public Topic(){}



}
