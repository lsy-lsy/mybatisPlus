package com.mybatisplus.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UploadUtil {


    public static String uploadFile(MultipartFile multipartFile) {
        String Url = "http://127.0.0.1:90";

//    文件路径
        String tracker ="E:/server/image/";

        String newVidoeName = "";
//        返回给的地址
        String vidoeName ="";
        String originalFimename="";
        String extName ="";
        UUID uuid = UUID.randomUUID();
        try {
            byte[] bytes = multipartFile.getBytes();
            originalFimename = multipartFile.getOriginalFilename();
            int i = originalFimename.lastIndexOf(".");
            extName = originalFimename.substring(i+1);
            System.out.println(extName);
            if(extName.equals("mp4")||extName.equals("avi")){
                newVidoeName = tracker+"video/"+uuid+"."+extName;
                vidoeName = "video/"+uuid+"."+extName;

//                extName = "/video/"+extName;
            }else if (extName.equals("jpg")||extName.equals("png")||extName.equals("bmp")){
                newVidoeName = tracker+"img/"+uuid+"."+extName;
                vidoeName = "img/"+uuid+"."+extName;
            }
            else {
                newVidoeName = tracker+"word/"+uuid+"."+extName;
                vidoeName = "word/"+uuid+"."+extName;
            }

//                extName ="/word/"+extName;
            System.out.println(newVidoeName);
            multipartFile.transferTo(new File(newVidoeName));

        } catch (IOException e) {
            e.printStackTrace();
        }
        Url+="/"+vidoeName;
        System.out.println(Url);
        return Url;
    }
}
