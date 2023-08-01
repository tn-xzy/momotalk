package com.momotalk_v1.service;

import com.momotalk_v1.entity.UploadFile;
import com.momotalk_v1.mapper.FileMapper;
import com.momotalk_v1.utils.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    String resourcePath = Paths.get(System.getProperty("user.dir") , "static").toString();
    File path=new File(resourcePath);
    @Autowired
    FileMapper fileMapper;
    @Value("${server.port}")
    String port;
    public String upload(MultipartFile file) {
        try {
            String sha1= HashUtil.sha1(file.getInputStream());
            UploadFile uploadFile=fileMapper.check(sha1);
            if (uploadFile!=null){
                return uploadFile.getFilename();
            }else {
                String[] fileNames=file.getOriginalFilename().split("\\.");
                String fileName=sha1+"."+fileNames[fileNames.length-1];
                File originFile=Paths.get(resourcePath,"img",fileName).toFile();
                file.transferTo(originFile);
                String filePath=Paths.get("static","img",fileName).toString();
                uploadFile=new UploadFile(sha1,filePath);
                fileMapper.add(uploadFile);
                return filePath;
            }
        }catch (IOException e){

        }
        return null;
    }
    @PostConstruct
    private void init(){
        if (!path.exists())
            path.mkdirs();
        File imgPath=Paths.get(resourcePath,"img").toFile();
        if (!imgPath.exists())
            imgPath.mkdirs();
    }
}
