package com.mybatisplus.util;

import java.io.File;


//删除文件
public class DeleteFile {

    public void delFile(String url){

        if(url!=null &&!url.equals("")){
            //获取名字
//        String url1 = "http://127.0.0.1:90/img/384f43eb-3847-402c-a495-f016b8ad7de0.jpg";
            String path = "E:/server/image/";
            String name = url.substring(20,url.length());
            System.out.println(name);

            File file = new File(path+name);
            if(file.exists() && file.isFile()){
                file.delete();
            }
        }
    }

}
