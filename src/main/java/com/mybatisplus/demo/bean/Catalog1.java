package com.mybatisplus.demo.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.mybatisplus.util.Page;

import java.io.Serializable;
import java.util.List;

public class Catalog1 extends Page implements Serializable {
    private int id;
    private String cat1Name;
    @TableField(exist = false)
    private List<Catalog2> catalog2List;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Catalog1() {

    }

    public Catalog1(int id, String cat1Name, List<Catalog2> catalog2List) {
        this.id = id;
        this.cat1Name = cat1Name;
        this.catalog2List = catalog2List;
    }

    @Override
    public String toString() {
        return "Catalog1{" +
                "id=" + id +
                ", cat1Name='" + cat1Name + '\'' +
                ", catalog2List=" + catalog2List +
                '}';
    }

    public String getCat1Name() {
        return cat1Name;
    }

    public void setCat1Name(String cat1Name) {
        this.cat1Name = cat1Name;
    }

    public List<Catalog2> getCatalog2List() {
        return catalog2List;
    }

    public void setCatalog2List(List<Catalog2> catalog2List) {
        this.catalog2List = catalog2List;
    }
}
