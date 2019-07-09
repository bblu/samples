package com.zoomway.patrol.controller;

import com.zoomway.patrol.controller.common.HttpResult;
import com.zoomway.patrol.service.FileService;
import com.zoomway.patrol.service.storage.StorageException;
import com.zoomway.patrol.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;

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
    FileService fileService;
    /**
     * upload file by wblu @ 2019-06-23
     *
     * @param file
     */
    @PostMapping(value = "/upload")
    public HttpResult upload(@RequestHeader(name = "X-Tower", required = true) Integer tower,
            @RequestHeader(name = "X-Line", required = true) Integer line, MultipartFile file)throws Exception{
        HttpResult res = new HttpResult();
        try {
            String fileMd5 = DigestUtils.md5DigestAsHex(file.getInputStream());
            String path = storageService.store(file);
            // ... 处理文件储存逻辑
            fileService.updateTowerByFile(tower,path);
            logger.warn(file.getName() + " Md5=" + fileMd5);
            return HttpResult.message("ok");
        }catch(StorageException e){
            return HttpResult.ServerError(e);
        }

    }
/**
 * 任务详情 by wblu @ 2019-05-28
 *
 * @param id       任务id
 */
    @GetMapping(value = "model/{id}")
    public HttpResult getById(@Range(min=0,message = "make sure task id is corrent") @PathVariable int id){
        return HttpResult.success("ok");
    }
}
