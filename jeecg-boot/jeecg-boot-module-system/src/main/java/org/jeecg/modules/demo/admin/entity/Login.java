package org.jeecg.modules.demo.admin.entity;

/**
 * ClassName:login
 * Package:IntelliJ IDEA
 * Description:
 *
 * @Date 2019/11/26 15:27
 */
public class Login {
    public static String getLoginLock(String name ){
        return "user:login:lockflag:"+name ;
    }
    public static  String getLoginFailTimes(String name){
        return "user:login:failtimes:"+name ;
    }
}
