package com.mybatisplus.demo.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mybatisplus.util.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


@Data
@AllArgsConstructor
@ToString
public class User extends Page implements Serializable {

    private int id;
    private String useLoginName;
    private String useName;
    private String usePwd;
    private String useSex;
    private String useImage;
    private String useEmail;
    private String useNumber;
    private String useSignature;

    public User(){}

}