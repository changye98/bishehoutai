package org.jeecg.modules.demo.admin.service.impl;

import org.jeecg.modules.demo.admin.entity.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:login
 * Package:IntelliJ IDEA
 * Description:
 *
 * @Date 2019/11/26 15:30
 */
@Service
public class LoginServiceImplTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate ;

    /**
     * 判断账户是否被锁定
     * @param name用户名
     * @return map
     */
    public Map<String,Object> loginLockCheck(String name){
        Map<String, Object> map = new HashMap<>();
        String key = Login.getLoginLock(name);
        Boolean flag = redisTemplate.hasKey(key);
        if(flag){
            Long expire = redisTemplate.getExpire(key, TimeUnit.MINUTES);
            map.put("flag",true);
            map.put("msg","由于该账户短时间内账号密码输入多次错误,已经被锁定,剩余时间为"+expire+"分钟");
        }else{
            map.put("flag",false);
        }
        return  map ;
    }
    public String loginFailTimes(String name ){
        //账号密码登录失败次数
        int failTimes =  5 ;
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String failkey = Login.getLoginFailTimes(name);
        Boolean flag = redisTemplate.hasKey(failkey);
        if(flag){
            long  currentTime =  Integer.toUnsignedLong((Integer) valueOperations.get(failkey)) ;
            Long expireSecond = redisTemplate.getExpire(failkey, TimeUnit.SECONDS);
            if(currentTime<failTimes-1){
                valueOperations.increment(failkey,1);
                return "在"+expireSecond+"秒内还有"+(failTimes-currentTime-1)+"次失败机会" ;
            }else{
                String lockKey = Login.getLoginLock(name);
                valueOperations.set(lockKey,true);
                long lockLastTime = 1 ;
                redisTemplate.expire(lockKey,lockLastTime,TimeUnit.HOURS);
                return "多次输入错误账号密码,账号被锁定" +lockLastTime+"小时" ;
            }
        }else{
            valueOperations.set(failkey,1);
            long failLastTime = 2 ;
            redisTemplate.expire(failkey,failLastTime,TimeUnit.MINUTES);
            return "在"+failLastTime+"分钟内还有"+(failTimes-1)+"次失败机会" ;

        }

    }
}
