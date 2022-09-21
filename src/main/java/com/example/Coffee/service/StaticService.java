package com.example.Coffee.service;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
@Log4j2
public class StaticService {

    @Value("${upload.path}")
    private String uploadPath;

    public void savePhoto(String directory, MultipartFile file, String fileName) throws IOException {
        String slash = "\\";
        if(System.getProperty("os.name").contains("Linux")){
            slash = "/";
        }
//        File path = new File(ResourceUtils.getURL("classpath:").getPath()
//                + slash +"static" + slash + "img" + slash + directory);
        File path = new File(uploadPath + slash + directory);
        if(!path.exists()){
            path.mkdirs();
        }
        file.transferTo(new File(path + slash + fileName));
        log.info("new photo in " + directory + " directory");
    }

    public void deletePhoto(String directory, String fileName) throws FileNotFoundException {
        String slash = "\\";
        if(System.getProperty("os.name").contains("Linux")){
            slash = "/";
        }
//        File file = new File(ResourceUtils.getURL("classpath:").getPath()
//                + slash +"static" + slash + "img" + slash + directory + slash + fileName);
        File file = new File(uploadPath + slash + directory + slash + fileName);
        if(file.exists()){
            file.delete();
        } else {
            log.warn("Exception delete photo in " + directory + " directory file doesn't exist");
        }
        log.info("delete photo in " + directory + " directory");
    }
}
