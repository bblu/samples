package com.zoomway.patrol.service;

import com.zoomway.patrol.dao.TowerDAO;
import com.zoomway.patrol.dao.TowerStyleDAO;
import com.zoomway.patrol.entity.Tower;
import com.zoomway.patrol.entity.TowerStyle;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class TowerService {
    @Resource
    private TowerDAO towerDao;
    @Resource
    private TowerStyleDAO styleDao;

    public List<Tower> getByLine(int line){return towerDao.getByLine(line);}

    public Tower getSingle(int taskId){
           return towerDao.getById(taskId);
    }

    public List<Tower> getTower(int offset,int limit){
        return towerDao.select(offset,limit);
    }

    public List<TowerStyle> getStyle(){ return styleDao.getAll(); }

    public TowerStyle getStyle(int id){ return styleDao.getById(id); }


}
