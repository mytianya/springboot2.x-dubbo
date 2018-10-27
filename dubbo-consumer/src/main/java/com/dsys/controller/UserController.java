package com.dsys.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dsys.comsumer.UserServiceComsumer;
import com.dsys.exception.CommonException;
import com.dsys.service.UserService;
import com.dsys.utils.JSONUtils;
import com.dsys.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author dsys
 * @CreateTime 18-10-9-下午8:48
 * @Description
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceComsumer userServiceComsumer;
    @PostMapping("/register")
    public String register(String nickname,String password){
        return  userServiceComsumer.register(nickname,password);
    }
    @PostMapping("/login")
    public String login(String account,String password){
        String login= userServiceComsumer.login(account,password);
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
    @PostMapping("/loginSession")
    public String loginSession(HttpServletRequest request){
        String account=request.getParameter("account");
        String password=request.getParameter("password");
        String login=userServiceComsumer.login(account,password);
        Map<String,Object> loginMap=JSONUtils.json2Map(login);
        boolean isSuccess=loginMap.get(JSONUtils.STATUS).equals(CommonException.StatusCode.SUCCESS.getCode());
        if(isSuccess){
            request.getSession().setAttribute(account,loginMap);
            return JSONUtils.map2Json(loginMap);
        }else{
            return login;
        }
    }
}
