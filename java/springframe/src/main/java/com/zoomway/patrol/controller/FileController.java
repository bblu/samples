package com.zoomway.patrol.controller;

import com.zoomway.patrol.controller.common.HttpResult;
import com.zoomway.patrol.entity.Task;
import com.zoomway.patrol.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.List;

//@EnableTransactionManagement


@CrossOrigin
@RestController
@RequestMapping("api/file")
@Validated
public class FileController {
    private final static Logger logger = LoggerFactory.getLogger(FileController.class);
    @Autowired
    TaskService service;
    /**
     * upload file by wblu @ 2019-06-23
     *
     * @param files
     */
    @PostMapping(value = "/upload")
    public HttpResult upload(MultipartFile[] files)throws Exception{
        HttpResult res = new HttpResult();
        for (MultipartFile file : files) {
            // ... 处理文件储存逻辑
            logger.warn(file.getName());
        }
        return res.setData("ok");
    }
/**
 * 任务详情 by wblu @ 2019-05-28
 *
 * @param id       任务id
 */
    @GetMapping(value = "/{id}")
    public Object getById(@Range(min=0,message = "make sure task id is corrent") @PathVariable int id){
        Task t = service.getSingle(id);
        HashMap<String,Object> res = new HashMap<>();
        res.put("data",t);
        if(id==2)return t;
        return res;
    }
}
