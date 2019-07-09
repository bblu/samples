package com.zoomway.patrol.dao;

import com.zoomway.patrol.entity.Tower;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TowerDAO extends BaseDAO<Tower> {

    List<Tower> select(@Param("offset") int offset, @Param("limit") int limit);

    List<Tower> getByLine(@Param("line") int line);

    Tower getById(@Param("id") int id);

    Integer updateBBox(@Param("id") int id,@Param("bbox") String bbox);

}
