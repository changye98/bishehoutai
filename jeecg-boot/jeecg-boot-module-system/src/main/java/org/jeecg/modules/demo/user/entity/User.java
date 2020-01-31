package org.jeecg.modules.demo.user.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 用户表
 * @Author: jeecg-boot
 * @Date:   2020-01-20
 * @Version: V1.0
 */
@Data
@TableName("user")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="user对象", description="用户表")
public class User {
    
	/**用户id,主键，字符串型*/
	@Excel(name = "用户id,主键，字符串型", width = 15)
    @ApiModelProperty(value = "用户id,主键，字符串型")
	private String userId;
	/**用户名*/
	@Excel(name = "用户名", width = 15)
    @ApiModelProperty(value = "用户名")
	private String userUsername;
	/**用户昵称*/
	@Excel(name = "用户昵称", width = 15)
    @ApiModelProperty(value = "用户昵称")
	private String userNickname;
	/**用户秘密*/
	@Excel(name = "用户秘密", width = 15)
    @ApiModelProperty(value = "用户秘密")
	private String userPassword;
	/**当前用户的角色的id*/
	@Excel(name = "当前用户的角色的id", width = 15)
    @ApiModelProperty(value = "当前用户的角色的id")
	private Integer userRoleId;
	/**用户的头像地址*/
	@Excel(name = "用户的头像地址", width = 15)
    @ApiModelProperty(value = "用户的头像地址")
	private String userAvatar;
	/**用户的自我描述*/
	@Excel(name = "用户的自我描述", width = 15)
    @ApiModelProperty(value = "用户的自我描述")
	private String userDescription;
	/**用户邮箱*/
	@Excel(name = "用户邮箱", width = 15)
    @ApiModelProperty(value = "用户邮箱")
	private String userEmail;
	/**用户手机号*/
	@Excel(name = "用户手机号", width = 15)
    @ApiModelProperty(value = "用户手机号")
	private String userPhone;
	/**创建时间*/
    @ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**更新时间*/
    @ApiModelProperty(value = "更新时间")
	private Date updateTime;
}
