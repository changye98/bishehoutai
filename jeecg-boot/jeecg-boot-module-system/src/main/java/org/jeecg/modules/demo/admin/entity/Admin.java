package org.jeecg.modules.demo.admin.entity;

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
 * @Description: ceshi
 * @Author: jeecg-boot
 * @Date:   2019-11-20
 * @Version: V1.0
 */
@Data
@TableName("admin")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="admin对象", description="ceshi")
public class Admin {
    
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private Integer id;
	/**username*/
	@Excel(name = "username", width = 15)
    @ApiModelProperty(value = "username")
	private String username;
	/**password*/
	@Excel(name = "password", width = 15)
    @ApiModelProperty(value = "password")
	private String password;
	public   static  String getName(){
		return  "admin" ;
	}

}
