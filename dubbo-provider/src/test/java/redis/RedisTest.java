package redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dsys.Provider;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Provider.class)
@WebAppConfiguration
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void testRedis1(){
        Map<String,String> hm=new HashMap<>();
        hm.put("name","dsys");
        hm.put("age","1024");
        redisTemplate.opsForValue().set("user",hm);
        Map<String,String> user= (Map<String, String>) redisTemplate.opsForValue().get("user");
        System.out.println(user.get("name"));
    }
}
