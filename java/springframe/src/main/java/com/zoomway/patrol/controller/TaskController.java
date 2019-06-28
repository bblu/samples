package com.zoomway.patrol.controller;

import com.zoomway.patrol.controller.common.HttpResult;
import com.zoomway.patrol.entity.Task;
import com.zoomway.patrol.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.hibernate.validator.constraints.Range;

import java.util.HashMap;
import java.util.List;

//@EnableTransactionManagement


@CrossOrigin
@RestController
@RequestMapping("api/task")
@Validated
public class TaskController {
    @Autowired
    TaskService service;
    /**
     * 批量查询 by wblu @ 2019-05-28
     *
     * @param id       任务id
     */
    @GetMapping(value = "/")
    public HttpResult getTask(
            @Range(min=0,message = "make sure task id is corrent") @RequestParam(required = false,defaultValue = "0") int id,
            @Range(min=0,message = "make sure offset is corrent") @RequestParam(required = false,defaultValue = "0") int offset,
            @Range(min=0,message = "make sure limit is corrent") @RequestParam(required = false,defaultValue = "0") int limit
            ){
        if(id >0) {
            Task task = service.getSingle(id);
            return HttpResult.success(task);
        }
        List<Task> tasks = service.getTask(offset,limit);
        return HttpResult.success(tasks);
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
