package com.zoomway.patrol.service;

import com.zoomway.patrol.dao.LineDAO;
import com.zoomway.patrol.dao.TowerDAO;
import com.zoomway.patrol.entity.Line;
import com.zoomway.patrol.entity.Tower;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class LineService {
    @Resource
    private LineDAO lineDao;
    @Resource
    private TowerDAO towerDao;

    public Line getSingle(int id){
           return lineDao.getById(id);
    }


    public List<Line> getLine(){
        return lineDao.getAll();
    }

    public List<Tower> getTower(int line){
        return towerDao.getByLine(line);
    }


}
