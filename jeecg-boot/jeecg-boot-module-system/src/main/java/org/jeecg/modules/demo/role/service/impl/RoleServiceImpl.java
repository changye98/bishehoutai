package org.jeecg.modules.demo.role.service.impl;

import org.jeecg.modules.demo.role.entity.Role;
import org.jeecg.modules.demo.role.mapper.RoleMapper;
import org.jeecg.modules.demo.role.service.IRoleService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 权限
 * @Author: jeecg-boot
 * @Date:   2020-01-20
 * @Version: V1.0
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
