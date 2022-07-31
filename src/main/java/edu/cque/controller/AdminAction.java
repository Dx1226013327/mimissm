package edu.cque.controller;

import edu.cque.pojo.Admin;
import edu.cque.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminAction {
    @Autowired
    AdminService adminService;
    //实现登陆判断，并进行响应的跳转
    @RequestMapping("login")
    public String login(String name,String pwd,HttpServletRequest request){
        Admin admin = adminService.login(name,pwd);
        if(admin != null){
            //登陆成功
            request.setAttribute("admin",admin);
            return "main";
        }else {
            //登陆失败
            request.setAttribute("errmsg","用户名或密码不正确");
            return "login";
        }

    }
}
