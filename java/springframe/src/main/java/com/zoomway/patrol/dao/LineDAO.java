package com.zoomway.patrol.dao;

import com.zoomway.patrol.entity.Line;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LineDAO extends BaseDAO<Line> {

    List<Line> getAll();

    Line getById(@Param("id") int id);
}
