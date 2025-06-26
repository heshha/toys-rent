//package cn.edu.sdua.toysrent.test;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//
//@SpringBootTest
//public class TestRedisTemplate {
//
//    @Autowired
//    private StringRedisTemplate redisTemplate;
//
//    @Test
//    public void testSet() {
//        redisTemplate.opsForValue().set("key2", "toysrent");
//    }
//
//    @Test
//    public void testGet() {
//        System.out.println(redisTemplate.opsForValue().get("key2"));
//    }
//
//}
