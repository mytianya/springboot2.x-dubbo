package sqltest;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dsys.Provider;
import com.dsys.dao.UserDao;
import com.dsys.entity.User;
import com.dsys.service.UserService;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Provider.class)
@WebAppConfiguration
public class UserTest {
    @Autowired
    private UserDao userDao;
//    @Reference(
//            version = "${api.service.version}",
//            application = "${dubbo.application.id}",
//            registry = "${dubbo.registry.id}"
//    )
//    private UserService userService;
    @Test
    public void testAddUser(){
        User user=new User();
        user.setPassword("123456");
        user.setAccount("124");
        user.setName("1");
        user.setLastLoginTime(LocalDateTime.now());
        user.setCreateTime(LocalDateTime.now());
        userDao.addUser(user);
        System.out.println("-------------------"+user.getId());
    }
   @Test
    public void testFindUserByAccount(){
        User user=userDao.findUserByAccount("123456789");
        System.out.println(user.getAccount());
       assert user.getId()==7;
   }
   @Test
    public void testLogin(){
        User user=userDao.findUserByAccountAndPwd("72c475ad59a540178f3915b3e23d2808","9XcuQTWqiqZkCgfLVPXu5g==");
        System.out.println(user.getAccount());
   }
}
