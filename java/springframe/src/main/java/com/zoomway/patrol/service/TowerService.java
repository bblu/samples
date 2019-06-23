package com.zoomway.patrol.service;

import com.zoomway.patrol.dao.TowerDAO;
import com.zoomway.patrol.entity.Tower;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class TowerService {
    @Resource
    private TowerDAO towerDao;

    public Tower getSingle(int taskId){
           return towerDao.getById(taskId);
    }


    public List<Tower> getTower(int offset,int limit){
        return towerDao.select(offset,limit);
    }


}
