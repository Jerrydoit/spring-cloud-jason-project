package com.jason.utils;

import com.jason.domain.UserEntity;
import com.jason.user.UserContext;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName RequestUtils
 * @Description TODO
 * @Author GCJ
 * @Date 2020/9/17 16:32
 */
public class RequestUtils {
    public static void setUserRequest(HttpServletRequest request){
        String username = request.getHeader("username");
        String account = request.getHeader("account");
        UserEntity userEntity = new UserEntity();
        userEntity.setUserAccount(account);
        userEntity.setUserName(username);
        userEntity.setUserPhone("17521124563");
        UserContext.setCurrentUser(userEntity);
    }
}
