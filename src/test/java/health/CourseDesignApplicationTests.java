package health;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import test.util.ValidateCodeUtils;

@SpringBootTest
class CourseDesignApplicationTests {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Test
    void contextLoads() {
        System.out.println(ValidateCodeUtils.generateValidateCode4String(6));
    }
    @Test
    void test(){
        redisTemplate.opsForValue().set("name","google");
        Object o = redisTemplate.opsForValue().get("name");
        System.out.println(o);
    }

}
