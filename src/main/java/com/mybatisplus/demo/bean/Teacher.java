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
public class Teacher extends Page implements Serializable {
    private int id;
    private String teaLoginName;
    private String teaName;
    private String teaPwd;
    private String teaSex;
    private String teaEmail;
    private String teaNumber;
    @TableField(exist = false)
    private String order;
    public Teacher() {
    }
}
