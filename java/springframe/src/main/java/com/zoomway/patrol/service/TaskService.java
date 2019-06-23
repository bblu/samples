package com.zoomway.patrol.service;

import com.zoomway.patrol.dao.TaskDAO;
import com.zoomway.patrol.entity.Task;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class TaskService {
    @Resource
    private TaskDAO taskDao;

    public Task getSingle(int taskId){
           return taskDao.getById(taskId);
    }
    public List<Task> getTask(int offset,int limit){
        return taskDao.getTask(offset,limit);
    }

}
