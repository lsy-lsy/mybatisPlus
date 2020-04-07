package com.mybatisplus.util;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class Page {

    @TableField(exist = false)
    private int page;
    @TableField(exist = false)
    private int limit;
    @TableField(exist = false)
    private int total;


    public Page(){}
}
