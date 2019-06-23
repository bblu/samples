package com.zoomway.patrol.dao;

import com.zoomway.patrol.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDAO extends BaseDAO<User> {

    //int addUser()

    List<User> getUser(@Param("offset") int offset, @Param("limit") int limit);

    User getById(@Param("id") int id);

    User getByUsername(@Param("username") String username);

    User getByToken(@Param("token") String token);

    int updateToken(User user);
}