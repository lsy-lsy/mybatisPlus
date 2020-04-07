package com.mybatisplus.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UploadingFile {


    public static String uploadFile(MultipartFile multipartFile,String url) {
        String Url = "http://127.0.0.1:90";

//    文件路径
        String tracker ="E:/server/image/";

        String newVidoeName = "";
//        返回给的地址
        String returnName ="";

        String originalFimename="";
        String extName ="";
        UUID uuid = UUID.randomUUID();
        try {
            byte[] bytes = multipartFile.getBytes();
            originalFimename = multipartFile.getOriginalFilename();
            int i = originalFimename.lastIndexOf(".");
            extName = originalFimename.substring(i+1);
            System.out.println(extName);

            newVidoeName = tracker+url+"/"+uuid+"."+extName;
            returnName = url+uuid+"."+extName;

            System.out.println(newVidoeName);
            multipartFile.transferTo(new File(newVidoeName));

        } catch (IOException e) {
            e.printStackTrace();
        }
        Url+="/"+returnName;
        System.out.println(Url);
        return Url;
    }
}
