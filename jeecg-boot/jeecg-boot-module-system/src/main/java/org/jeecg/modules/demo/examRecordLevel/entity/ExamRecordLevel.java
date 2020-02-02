package org.jeecg.modules.demo.examRecordLevel.entity;

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
 * @Description: 考试成绩等级
 * @Author: jeecg-boot
 * @Date:   2020-01-27
 * @Version: V1.0
 */
@Data
@TableName("exam_record_level")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="exam_record_level对象", description="考试成绩等级")
public class ExamRecordLevel {
    
	/**考试结果等级表的主键*/
	@Excel(name = "考试结果等级表的主键", width = 15)
    @ApiModelProperty(value = "考试结果等级表的主键")
	private Integer examRecordLevelId;
	/**考试结果等级的名称*/
	@Excel(name = "考试结果等级的名称", width = 15)
    @ApiModelProperty(value = "考试结果等级的名称")
	private String examRecordLevelName;
	/**考试结果等级的详细阐述*/
	@Excel(name = "考试结果等级的详细阐述", width = 15)
    @ApiModelProperty(value = "考试结果等级的详细阐述")
	private String examRecordLevelDescription;
}
