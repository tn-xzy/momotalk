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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    String resourcePath = Paths.get(System.getProperty("user.dir") , "static").toString();
    File path=new File(resourcePath);
    @Autowired
    FileMapper fileMapper;
    String[] units=new String[]{"bytes","kb","mb","gb","tb"};
//    @Value("${server.port}")
//    String port;
    public UploadFile upload(MultipartFile file,String medPath) {
        try {
            String sha1= HashUtil.sha1(file.getInputStream());
            UploadFile uploadFile=fileMapper.check(sha1);
            if (uploadFile!=null){
                return uploadFile;
            }else {
                String[] fileNames=file.getOriginalFilename().split("\\.");
                String fileName=sha1+"."+fileNames[fileNames.length-1];
                File targetPath=Paths.get(resourcePath,medPath).toFile();
                if (!targetPath.exists())
                    targetPath.mkdirs();
                File originFile=Paths.get(targetPath.getAbsolutePath(),fileName).toFile();
                file.transferTo(originFile);
                Path targetFile=Paths.get(originFile.getAbsolutePath());
                double size= Files.size(targetFile);
                int unitIndex=0;
                while (size>=1024&&unitIndex<units.length){
                    size/=1024;
                    unitIndex++;
                }
                uploadFile=new UploadFile(sha1,fileName,String.format("%.2f",size),units[unitIndex]);
                fileMapper.add(uploadFile);
                return uploadFile;
            }
        }catch (IOException ignored){
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
