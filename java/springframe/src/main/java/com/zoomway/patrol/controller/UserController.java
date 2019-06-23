package com.zoomway.patrol.controller;

import com.zoomway.patrol.controller.common.HttpResult;
import com.zoomway.patrol.service.UserService;
import com.zoomway.patrol.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Validated
@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("api/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    //注册
    @RequestMapping(path = {"/reg/"}, method = {RequestMethod.POST})
    public String reg(Model model, @RequestParam("username") String username, @RequestParam("password") String password,
                      @RequestParam(value = "rememberme",defaultValue = "false") boolean rememberme,
                      HttpServletResponse response) {
        try {
            Map<String, String> map = userService.register(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket",map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);
                return "redirect:/";
            }else{
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return "login";
        }
    }

    @RequestMapping(path = {"/reglogin"}, method = {RequestMethod.GET})
    public String register(Model model) {
        return "login";
    }

    @GetMapping("/info")
    public HttpResult userInfo(@RequestParam("token") String token){
        HttpResult res = new HttpResult();
        User user = userService.getByToken(token);
        if(user == null){
            res.setCode(400);
            return res.setMessage("can not get user for " + token);
        }
        res.setData(user);
        return res;
    }

    @RequestMapping(path={"/login"},method = {RequestMethod.POST})
    public HttpResult loginPostJson(@RequestBody User user){
        HttpResult res = new HttpResult();
        try{
            Map<String,Object> map = userService.login(user.getUsername(),user.getPassword());
            if(map.containsKey("token")){
                return res.setData(map);
            }
            return res.setMessage(map.get("msg").toString());
        }catch (Exception e){
            logger.error("登陆异常" + e.getMessage());
            return res.setMessage(e.getMessage());
        }
    }

    @RequestMapping(path={"/logout"},method = {RequestMethod.POST})
    public HttpResult logoutPostJson(HttpServletRequest request){
        HttpResult res = new HttpResult();
        try{
            String token = request.getHeader("X-Token");
            int c = userService.logout(token);
            if(c==1){
                return res.setMessage("success");
            }
            res.setCode(400);
            return res.setMessage("error");
        }catch (Exception e){
            res.setCode(500);
            logger.error("登陆异常" + e.getMessage());
            return res.setMessage(e.getMessage());
        }
    }
    //登陆
    @RequestMapping(path={"/loginForm"},method = {RequestMethod.POST})
    public HttpResult loginFrom(@RequestParam("username") String username,@RequestParam("password") String password){
        HttpResult res = new HttpResult();
        try{
            Map<String,Object> map = userService.login(username,password);
            if(map.containsKey("token")){
                return res.setData(map);
            }
            return res.setMessage(map.get("msg").toString());
        }catch (Exception e){
            logger.error("登陆异常" + e.getMessage());
            return res.setMessage(e.getMessage());
        }
    }
    //登陆
    @RequestMapping(path={"/loginbody"},method = {RequestMethod.POST})
    public String loginbody(Model model,@ModelAttribute("username") String username, @ModelAttribute("password") String password,
                        @RequestParam(value = "rememberme",defaultValue = "false") boolean rememberme,
                        HttpServletResponse response){
        try{
            Map<String,Object> map = userService.login(username,password);
            if(map.containsKey("token")){
                Cookie cookie = new Cookie("token",map.get("token").toString());
                cookie.setPath("/");           //可在同一应用服务器内共享cookie
                response.addCookie(cookie);
                return "redirect:/";
            }
            else{
                model.addAttribute("msg",map.get("msg"));
                return "login";
            }
        }catch (Exception e){
            logger.error("登陆异常" + e.getMessage());
            return "login";
        }
    }
}
