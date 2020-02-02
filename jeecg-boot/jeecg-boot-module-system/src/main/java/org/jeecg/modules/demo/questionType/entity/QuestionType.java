package org.jeecg.modules.demo.questionType.entity;

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
 * @Description: 问题类型
 * @Author: jeecg-boot
 * @Date:   2020-01-26
 * @Version: V1.0
 */
@Data
@TableName("question_type")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="question_type对象", description="问题类型")
public class QuestionType {
    
	/**题目类型表的主键*/
	@Excel(name = "题目类型表的主键", width = 15)
    @ApiModelProperty(value = "题目类型表的主键")
	private Integer questionTypeId;
	/**题目类型名称*/
	@Excel(name = "题目类型名称", width = 15)
    @ApiModelProperty(value = "题目类型名称")
	private String questionTypeName;
	/**题目类型的描述*/
	@Excel(name = "题目类型的描述", width = 15)
    @ApiModelProperty(value = "题目类型的描述")
	private String questionTypeDescription;
}
