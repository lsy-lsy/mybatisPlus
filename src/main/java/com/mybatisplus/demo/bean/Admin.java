package com.mybatisplus.demo.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mybatisplus.util.Page;

import java.io.Serializable;

public class Admin extends Page implements Serializable {
    @TableId(type = IdType.AUTO)
    private int id;

    private String admName;

    private String admPwd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdmName() {
        return admName;
    }

    public void setAdmName(String admName) {
        this.admName = admName;
    }

    public String getAdmPwd() {
        return admPwd;
    }

    public void setAdmPwd(String admPwd) {
        this.admPwd = admPwd;
    }

    public Admin(int id, String admName, String admPwd) {
        this.id = id;
        this.admName = admName;
        this.admPwd = admPwd;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", admName='" + admName + '\'' +
                ", admPwd='" + admPwd + '\'' +
                '}';
    }
}
