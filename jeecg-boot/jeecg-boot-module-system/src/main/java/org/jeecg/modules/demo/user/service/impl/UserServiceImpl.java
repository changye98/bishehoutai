package org.jeecg.modules.demo.user.service.impl;

import org.jeecg.modules.demo.user.entity.User;
import org.jeecg.modules.demo.user.mapper.UserMapper;
import org.jeecg.modules.demo.user.service.IUserService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 用户表
 * @Author: jeecg-boot
 * @Date:   2020-01-20
 * @Version: V1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
