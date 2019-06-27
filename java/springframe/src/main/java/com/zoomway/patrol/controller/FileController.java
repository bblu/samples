package com.zoomway.patrol.controller;

import com.zoomway.patrol.controller.common.HttpResult;
import com.zoomway.patrol.entity.Task;
import com.zoomway.patrol.service.TaskService;
import com.zoomway.patrol.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@EnableTransactionManagement


@CrossOrigin
@RestController
@RequestMapping("api/file")
@Validated
public class FileController {
    private final static Logger logger = LoggerFactory.getLogger(FileController.class);
    private final StorageService storageService;

    @Autowired
    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }
    @Autowired
    TaskService service;
    /**
     * upload file by wblu @ 2019-06-23
     *
     * @param file
     */
    @PostMapping(value = "/upload")
    public HttpResult upload(@RequestHeader(name = "X-Tower", required = true) Integer tower,
                             MultipartFile file)throws Exception{
        HttpResult res = new HttpResult();
        String fileMd5 = storageService.store(file);
        // ... 处理文件储存逻辑
        logger.warn(file.getName());

        return res.setData("ok"+ tower);
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
