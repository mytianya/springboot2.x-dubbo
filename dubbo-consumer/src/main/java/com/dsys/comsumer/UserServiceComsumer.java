package com.dsys.comsumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dsys.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceComsumer {
    @Reference(
            version = "${api.service.version}",
            application = "${dubbo.application.id}",
            registry = "${dubbo.registry.id}"
    )
    private UserService userService;
    public String register(String nickname,String password){
        return userService.register(nickname,password);
    }
    public  String login(String account,String password){
        return userService.login(account,password);
    }
}
