package com.zoomway.patrol.dao;

import com.zoomway.patrol.entity.Task;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TaskDAO extends BaseDAO<Task> {

    List<Task> getTask(@Param("offset") int offset, @Param("limit") int limit);

    Task getById(@Param("id") int id);
}
