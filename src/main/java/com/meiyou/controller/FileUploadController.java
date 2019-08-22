package com.meiyou.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author ：huangzhaoyang
 * @date ：Created in 2019/8/22 10:34
 * @description：文件上传接口
 * @modified By：huangzhaoyang
 * @version: 1.0.0
 */
@RestController
public class FileUploadController {

    //时间格式
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd/");
    @PostMapping("/uploads")
    public HashMap<String,String> upload(MultipartFile[] uploadFile, HttpServletRequest request){
        String realPath=request.getSession().getServletContext().getRealPath("/uploadFile/");

        String format=sdf.format(new Date());
        File folder=new File(realPath+format);
        //是否目录
        if(!folder.isDirectory()){
            folder.mkdirs();
        }
        HashMap<String,String> map=new HashMap<String,String>();
        String filePath="";
        for(MultipartFile multipartFile:uploadFile){
            String oldName=multipartFile.getOriginalFilename();
            String newName= UUID.randomUUID().toString()+oldName.substring(oldName.lastIndexOf("."),oldName.length());
            try {
                //用于图片上传时，把内存中图片写入磁盘
                multipartFile.transferTo(new File(folder,newName));
                filePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/uploadFile/"+format+newName;
                map.put(newName, filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;

    }

}
