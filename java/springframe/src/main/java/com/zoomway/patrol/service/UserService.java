package com.zoomway.patrol.service;


//import com.zoomway.patrol.dao.loginTicketsDAO;
import com.zoomway.patrol.dao.UserDAO;
import com.zoomway.patrol.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;


@Service
public class UserService {
    Random random = new Random();

    @Autowired
    UserDAO userDAO;

    //@Autowired
    //loginTicketsDAO lTicketsDAO;

    //注册

    public Map<String, String> register(String userName, String password) {

        Map<String, String> map = new HashMap<String, String>();
        if (StringUtils.isEmpty(userName)) {
            map.put("msg", "用户名不能为空");
            return map;
        }

        if (StringUtils.isEmpty(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }

        User user = userDAO.getByUsername(userName);
        if (user != null) {
            map.put("msg", "用户名已被注册");
            return map;
        }
        user = new User();
        user.setName(userName);
        //user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        //user.setHead_url(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
        //user.setPassword(WendaUtil.MD5(password + user.getSalt()));
        user.setPassword(password);
        //userDAO.addUser(user);
        userDAO.insert(user);

        //注册完成下发ticket之后自动登录
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);

        return map;
    }

    //登陆
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isEmpty(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }

        if (StringUtils.isEmpty(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }

        User user = userDAO.getByUsername(username);
        if (user == null) {
            map.put("msg", "用户名不存在");
            return map;
        }

        //if (!WendaUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
        if (!password.equals(user.getPassword())) {
            map.put("msg", "密码错误");
            return map;
        }
        String ticket = addLoginTicket(user.getId());
        map.put("token", ticket);
        return map;
    }

    public int logout(String token){
        User usr = this.getByToken(token);
        User utk= new User();
        utk.setId(usr.getId());
        if(usr.getId()==1)utk.setToken("admin");
        utk.setStatus(0);
        return userDAO.updateToken(utk);
    }

    public String addLoginTicket(int id) {
        User ticket = new User();
        ticket.setId(id);
        Date nowDate = new Date();
        nowDate.setTime(3600 * 24 * 100 + nowDate.getTime());
        ticket.setExpired(nowDate);
        ticket.setStatus(1);
        if(id==1){
            ticket.setToken("admin");
        }else {
            ticket.setToken(UUID.randomUUID().toString().replaceAll("_", ""));
        }
        //ticketsDAO.addTicket(ticket);
        int i = userDAO.updateToken(ticket) ;
        return ticket.getToken();

    }

    public User getByToken(String token){
        User user = userDAO.getByToken(token);
        if(token.equals("admin")) {
            String[] r = {"admin"};
            user.setRoles(r);
        }
        return user;
    }
    public User getUser(int id) {
        return userDAO.getById(id);
    }


}
