package com.ping.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Mycontroller {

    @RequestMapping({"/","/index"})
    public String toIndex(Model model){
        model.addAttribute("msg","hello,shiro");
        return "index";
    }

    @RequestMapping("/user/add")
    public String add(){
        return "/user/add";
    }

    @RequestMapping("/user/update")
    public String update(){
        return "/user/update";
    }

    @RequestMapping({"/toLogin"})
    public String toLogin(){
        return "login";
    }

    @RequestMapping({"/login"})
    public String login(String username ,String password,Model model){
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //执行登录的方法如果没有异常就说明OK了
        try{
            subject.login(token);
        }catch(UnknownAccountException e){
            model.addAttribute("msg","账号错误");
        }catch(IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
        }
        return "index";
    }

    @ResponseBody
    @RequestMapping("/noauth")
    public String unauthorized(){
        return "未经授权，无法访问此页面！";
    }
}
