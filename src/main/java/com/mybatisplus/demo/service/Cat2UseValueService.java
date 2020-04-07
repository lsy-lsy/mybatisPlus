package com.mybatisplus.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mybatisplus.demo.bean.Cat2UseValue;

public interface Cat2UseValueService extends IService<Cat2UseValue> {

    Boolean getCat2UseTeacher(int uId, int teaId , int catalog2Id);

    Boolean deleteCat2(String cat2, String useId);
}
