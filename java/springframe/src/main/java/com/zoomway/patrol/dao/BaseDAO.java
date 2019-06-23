package com.zoomway.patrol.dao;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface BaseDAO<T> extends Mapper<T>,MySqlMapper<T> {

}
