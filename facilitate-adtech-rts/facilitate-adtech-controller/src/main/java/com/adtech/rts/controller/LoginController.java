package com.adtech.rts.controller;

import com.adtech.rts.model.result.Result;
import com.adtech.rts.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

/**
 * 登录接口
 */
@Api(value = "/login", tags = "登录接口")
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;
    @GetMapping("/loginIndex")
    public ModelAndView loginIndex(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("/login")
    @ResponseBody
    public Result login(@ApiParam(name="userName",value="用户名",required=true)String userName,
                        @ApiParam(name="password",value="密码",required=true)String password, HttpServletResponse response){
        return loginService.login(userName,password,response);
    }
}
