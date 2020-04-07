package com.mybatisplus.demo.bean;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class TopUseValue {
    private int id;
    private String useId;
    private String topId;
    private String useAnswer;

    @TableField(exist = false)
    private String order;

    public TopUseValue( String useId, String topId, String useAnswer) {
        this.useId = useId;
        this.topId = topId;
        this.useAnswer = useAnswer;
    }

    public TopUseValue(){}

}
