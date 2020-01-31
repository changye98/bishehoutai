package org.jeecg.modules.demo.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName:ListTest
 * Package:IntelliJ IDEA
 * Description:
 *
 * @Date 2019/11/29 10:28
 */
@Service
public class ListTestServiceImpl {
    @Autowired
    RedisTemplate<String,Object> redisTemplate  ;
    @Resource(name = "redisTemplate")
    ListOperations<String, String> list ;

    public void listAdd(){
//        System.out.println("hello");
//        System.out.println(redisTemplate);
        String key  = "news"   ;
        list.leftPushAll(key ,"1","2","3","4","5","6","7","8");
    }
    public List<String> listQueryPage(long page , long pagesize){
        String key  = "news"   ;
        long start  = (page-1)*pagesize ;
        long stop = (page*pagesize)-1  ;
        return list.range(key, start, stop);
    }
    public long  listQueryTotal(){
        String key  = "news"   ;
        return  list.size(key);
    }
}
