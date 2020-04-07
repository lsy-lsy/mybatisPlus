package com.mybatisplus.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class UserData {
    private String id = "1";
    private String name = "name";

    public UserData(){}
}
