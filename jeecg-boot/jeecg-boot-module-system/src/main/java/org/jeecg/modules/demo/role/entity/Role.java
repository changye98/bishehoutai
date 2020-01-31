package org.jeecg.modules.demo.role.entity;

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
 * @Description: 权限
 * @Author: jeecg-boot
 * @Date:   2020-01-20
 * @Version: V1.0
 */
@Data
@TableName("role")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="role对象", description="权限")
public class Role {
    
	/**角色表主键id*/
	@Excel(name = "角色表主键id", width = 15)
    @ApiModelProperty(value = "角色表主键id")
	private Integer roleId;
	/**角色名称*/
	@Excel(name = "角色名称", width = 15)
    @ApiModelProperty(value = "角色名称")
	private String roleName;
	/**角色的描述*/
	@Excel(name = "角色的描述", width = 15)
    @ApiModelProperty(value = "角色的描述")
	private String roleDescription;
	/**角色的详细功能阐述*/
	@Excel(name = "角色的详细功能阐述", width = 15)
    @ApiModelProperty(value = "角色的详细功能阐述")
	private String roleDetail;
	/**当前角色所能访问的页面的id集合*/
	@Excel(name = "当前角色所能访问的页面的id集合", width = 15)
    @ApiModelProperty(value = "当前角色所能访问的页面的id集合")
	private String rolePageIds;
}
