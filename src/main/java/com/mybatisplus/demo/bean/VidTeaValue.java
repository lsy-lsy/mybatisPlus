package com.mybatisplus.demo.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class VidTeaValue {

    @TableId(type = IdType.AUTO)
    private int id;
    private String vidId;
    private String teaId;



    @Override
    public String toString() {
        return "VidTeaValue{" +
                "id=" + id +
                ", vidId='" + vidId + '\'' +
                ", teaId='" + teaId + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVidId() {
        return vidId;
    }

    public void setVidId(String vidId) {
        this.vidId = vidId;
    }

    public String getTeaId() {
        return teaId;
    }

    public void setTeaId(String teaId) {
        this.teaId = teaId;
    }
}
