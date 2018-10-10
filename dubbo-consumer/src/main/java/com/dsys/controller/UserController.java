package com.dsys.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dsys.exception.CommonException;
import com.dsys.service.TestService;
import com.dsys.service.UserService;
import com.dsys.utils.JSONUtils;
import com.dsys.utils.JwtHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author dsys
 * @CreateTime 18-10-9-下午8:48
 * @Description
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Reference(
            version = "${api.service.version}",
            application = "${dubbo.application.id}",
            registry = "${dubbo.registry.id}"
    )
    private UserService userService;
    @PostMapping("/register")
    public String register(String nickname,String password,String sex){
        return userService.register(nickname,password,sex);
    }
    @PostMapping("/login")
    public String login(String account,String password){
        String login= userService.login(account,password);
        Map<String,Object> loginMap=JSONUtils.json2Map(login);
        boolean isSuccess=loginMap.get(JSONUtils.STATUS).equals(CommonException.StatusCode.SUCCESS.getCode());
        if(isSuccess){
            String token=JwtHelper.createJwt(account);
            loginMap.put("token",token);
            return JSONUtils.map2Json(loginMap);
        }else{
            return login;
        }

    }
}
