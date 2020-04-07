package com.mybatisplus.demo.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.mybatisplus.util.Page;

import java.io.Serializable;

//题目答案表
public class Answer extends Page implements Serializable {
    private int id;
    private String A;
    private String B;
    private String C;
    private String D;
    private String ansParse;
    private String ansCorrect;
    private int topId;

    @TableField(exist = false)
    private String order;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getAnsParse() {
        return ansParse;
    }

    public void setAnsParse(String ansParse) {
        this.ansParse = ansParse;
    }

    public String getAnsCorrect() {
        return ansCorrect;
    }

    public void setAnsCorrect(String ansCorrect) {
        this.ansCorrect = ansCorrect;
    }

    public Integer getTopId() {
        return topId;
    }

    public void setTopId(Integer topId) {
        this.topId = topId;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", A='" + A + '\'' +
                ", B='" + B + '\'' +
                ", C='" + C + '\'' +
                ", D='" + D + '\'' +
                ", ansParse='" + ansParse + '\'' +
                ", ansCorrect='" + ansCorrect + '\'' +
                ", topId=" + topId +
                '}';
    }
}
