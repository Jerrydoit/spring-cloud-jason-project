package com.jason.user.controller;

import com.jason.domain.ResultVo;
import com.jason.user.domain.UserInfoDto;
import com.jason.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author GCJ
 * @Date 2019/11/11 9:30
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    /**
     * 注册用户
     * @param userInfo
     * @return
     */
    @RequestMapping("/register")
    public ResultVo registerUser(@RequestBody UserInfoDto userInfo){
        return userService.registerUser(userInfo);
    }
    /**
     * 登录
     * @param userInfo
     * @return
     */
    @RequestMapping("/login")
    public ResultVo loginUser(@RequestBody UserInfoDto userInfo){
        return userService.loginUser(userInfo);
    }

    /**
     * 验证用户名的唯一性
     * @param map
     * @return
     */
    @RequestMapping("/checkUserName")
    public ResultVo checkUserName(@RequestBody Map<String,String> map){
        return userService.checkUserName(map);
    }
}
