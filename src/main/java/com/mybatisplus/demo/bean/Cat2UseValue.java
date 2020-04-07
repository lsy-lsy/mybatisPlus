package com.mybatisplus.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Cat2UseValue {
    private int id;
    private int useId;
    private int cat2Id;
    private int teaId;

    public Cat2UseValue() {
    }


}
