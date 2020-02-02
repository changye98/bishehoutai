package org.jeecg.modules.demo.questionOption.entity;

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
 * @Description: 问题选项
 * @Author: jeecg-boot
 * @Date:   2020-01-26
 * @Version: V1.0
 */
@Data
@TableName("question_option")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="question_option对象", description="问题选项")
public class QuestionOption {
    
	/**题目选项表的主键*/
	@Excel(name = "题目选项表的主键", width = 15)
    @ApiModelProperty(value = "题目选项表的主键")
	private String questionOptionId;
	/**选项的内容*/
	@Excel(name = "选项的内容", width = 15)
    @ApiModelProperty(value = "选项的内容")
	private String questionOptionContent;
	/**选项的额外描述，可以用于题目答案解析*/
	@Excel(name = "选项的额外描述，可以用于题目答案解析", width = 15)
    @ApiModelProperty(value = "选项的额外描述，可以用于题目答案解析")
	private String questionOptionDescription;
}
