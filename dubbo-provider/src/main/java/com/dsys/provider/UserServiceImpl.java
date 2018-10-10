package com.dsys.provider;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.dsys.dao.UserDao;
import com.dsys.exception.CommonException;
import com.dsys.service.UserService;
import com.dsys.utils.CommonUtils;
import com.dsys.utils.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author dsys
 * @CreateTime 18-10-9-下午7:31
 * @Description
 **/
@Service(
        version = "${api.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public String register(String nickname, String password, String sex) {
        JSONUtils.testEmpty("注册昵称",nickname);
        JSONUtils.testEmpty("注册密码",password);
        //用户账号是否已存在
        boolean userAccountExisit=true;
        String account="";
        do{
            account=createAccount();
            userAccountExisit=userDao.findUserByAccount(account)>0;
        }while(userAccountExisit);
        String hashPwd=CommonUtils.md5Salt(account,password);
        if(StringUtils.isEmpty(hashPwd)){
            return JSONUtils.packageResult(false,"密码过于复杂","");
        }
        boolean registerStatus=userDao.register(nickname,hashPwd,account,sex)>0;
        if(registerStatus){
            Map<String,Object> userMap=new HashMap<String,Object>();
            userMap.put("account",account);
            userMap.put("sex",sex);
            userMap.put("nickname",nickname);
            return JSONUtils.packageResult(true,userMap,"注册成功");
        }else{
            return JSONUtils.packageResult(false,"","系统故障,注册失败");
        }

    }
    public String login(String account,String password){
        JSONUtils.testEmpty("用户账号",account);
        JSONUtils.testEmpty("登录密码",password);
        boolean userAccountExisit=userDao.findUserByAccount(account)>0;
        if(!userAccountExisit){
            return JSONUtils.packageResult(false,"","账号"+account+"不存在");
        }
        String hashPwd=CommonUtils.md5Salt(account,password);
        Map<String,Object> userMap=userDao.login(account,hashPwd);
        if(userMap==null){
            return JSONUtils.packageResult(false,"","登录密码错误");
        }else{
            return JSONUtils.packageResult(true,userMap,"登录成功");
        }
    }
    static String createAccount(){
        return UUID.randomUUID().toString().replace("-","").toLowerCase();
    }

}
