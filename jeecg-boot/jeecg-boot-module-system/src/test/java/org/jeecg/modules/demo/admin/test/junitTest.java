package org.jeecg.modules.demo.admin.test;

import org.jeecg.modules.demo.admin.entity.Admin;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * ClassName:junitTest
 * Package:IntelliJ IDEA
 * Description:
 *
 * @Date 2019/11/28 9:19
 */
public class junitTest {
    @Autowired
    RedisTemplate redis ;
    @Resource(name="redis")
    HashOperations<String, Integer, Admin> hash  ;
   @Test
    public void test1() {
       Integer id  = 1 ;
       if(hash.hasKey(Admin.getName(), id )){
           System.out.println("redis查询");
           System.out.println(hash.get(Admin.getName(),id));
       }else{
           Admin admin = new Admin();
           admin.setId(id);
           admin.setPassword("hello");
           admin.setUsername("zhangsan");
           hash.put(Admin.getName(),id,admin);
           System.out.println("mysql查询");
           System.out.println(admin);
       }
    }
}