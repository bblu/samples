package com.zoomway.patrol.dao;

import com.zoomway.patrol.entity.TowerStyle;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TowerStyleDAO extends BaseDAO<TowerStyle> {

    List<TowerStyle> getAll();

    TowerStyle getById(@Param("id") int id);


}
