package com.dsys.provider;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.dsys.dao.UserDao;
import com.dsys.entity.User;
import com.dsys.exception.CommonException;
import com.dsys.service.UserService;
import com.dsys.utils.CommonUtils;
import com.dsys.utils.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
    public String register(String nickname, String password) {
        JSONUtils.testEmpty("注册昵称",nickname);
        JSONUtils.testEmpty("注册密码",password);
        //用户账号是否已存在
        boolean userAccountExisit=true;
        String account="";
        do{
            account=createAccount();
          userAccountExisit=userDao.findUserByAccount(account)!=null;
        }while(userAccountExisit);
        String hashPwd=CommonUtils.md5Salt(account,password);
        if(StringUtils.isEmpty(hashPwd)){
            return JSONUtils.packageResult(false,"密码过于复杂","");
        }
        User user=new User();
        user.setCreateTime(LocalDateTime.now());
        user.setName(nickname);
        user.setAccount(account);
        user.setPassword(hashPwd);
        userDao.addUser(user);
        if(user.getId()>0){
            Map<String,Object> userMap=new HashMap<String,Object>();
            userMap.put("account",account);
            userMap.put("nickname",nickname);
            return JSONUtils.packageResult(true,userMap,"注册成功");
        }else{
            return JSONUtils.packageResult(false,"","系统故障,注册失败");
        }
    }
    public String login(String account,String password){
        JSONUtils.testEmpty("用户账号",account);
        JSONUtils.testEmpty("登录密码",password);
        boolean userAccountExisit=userDao.findUserByAccount(account)==null;
        if(userAccountExisit){
            return JSONUtils.packageResult(false,"","账号"+account+"不存在");
        }
        String hashPwd=CommonUtils.md5Salt(account,password);
        User user=userDao.findUserByAccountAndPwd(account,hashPwd);
        if(user==null){
            return JSONUtils.packageResult(false,"","登录密码错误");
        }else{
           if(user.forbiden()){
               return  JSONUtils.packageResult(false,"","用户"+account+"禁止登陆");
           }
           Map<String,Object> userMap=new HashMap<>();
           userMap.put("nickname",user.getName());
           userMap.put("account",user.getAccount());
           return JSONUtils.packageResult(true,userMap,"登录成功");
        }
    }
    static String createAccount(){
        return UUID.randomUUID().toString().replace("-","").toLowerCase();
    }

}
