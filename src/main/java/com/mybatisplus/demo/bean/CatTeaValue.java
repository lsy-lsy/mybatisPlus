package com.mybatisplus.demo.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@TableName("cat2_tea_value")
@Data
@AllArgsConstructor
@ToString
public class CatTeaValue {
    private int id;
    private int cat2_id;
    private int tea_id;

    @TableField(exist = false)
    List<Teacher> teacherList;

    public CatTeaValue() {
    }

}
