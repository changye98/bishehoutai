package org.jeecg.modules.demo.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.user.entity.User;
import org.jeecg.modules.demo.user.service.IUserService;
import org.jeecg.modules.demo.user.service.impl.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description: 用户表
 * @Author: jeecg-boot
 * @Date:   2020-01-20
 * @Version: V1.0
 */
@Slf4j
@Api(tags="登录")
@RestController
@RequestMapping("/login")
public class UserLoginController {
    @Autowired
    private IUserService userServiceLogin;
    @Autowired
    private LoginServiceImpl loginService;
    //登录限制
    @GetMapping("/dengLuXianZhi")
    public Result<User> login(@RequestParam("username") String username, @RequestParam("password") String psw ){
        Result<User> userResult = new Result<>();
        //判断该用户当前是否被锁定
        Map<String, Object> map = loginService.loginLockCheck(username);
        if((Boolean) map.get("flag")){
//            System.out.println(map.get("msg"));
            userResult.setSuccess(false);
            userResult.setMessage((String) map.get("msg"));
            return  userResult ;

        }else{
            int count = userServiceLogin.count(new QueryWrapper<User>().eq("USER_USERNAME", username).eq("USER_PASSWORD", psw));
            if(count>=1){
                User one = userServiceLogin.getOne(new QueryWrapper<User>().eq("USER_USERNAME", username).eq("USER_PASSWORD", psw));
                userResult.setResult(one);
                userResult.setSuccess(true);
                return userResult ;
                
            }else{
                userResult.setSuccess(false);
                String s = loginService.loginFailTimes(username);
                userResult.setMessage(s);
                return userResult;
            }
            
        }
    }

}
