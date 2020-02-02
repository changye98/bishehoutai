package org.jeecg.modules.demo.question.entity;

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
 * @Description: 问题
 * @Author: jeecg-boot
 * @Date:   2020-01-26
 * @Version: V1.0
 */
@Data
@TableName("question")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="question对象", description="问题")
public class Question {
    
	/**题目的主键*/
	@Excel(name = "题目的主键", width = 15)
    @ApiModelProperty(value = "题目的主键")
	private String questionId;
	/**题目的名字*/
	@Excel(name = "题目的名字", width = 15)
    @ApiModelProperty(value = "题目的名字")
	private String questionName;
	/**题目的分数*/
	@Excel(name = "题目的分数", width = 15)
    @ApiModelProperty(value = "题目的分数")
	private Integer questionScore;
	/**题目创建者的用户id*/
	@Excel(name = "题目创建者的用户id", width = 15)
    @ApiModelProperty(value = "题目创建者的用户id")
	private String questionCreatorId;
	/**题目难易度级别*/
	@Excel(name = "题目难易度级别", width = 15)
    @ApiModelProperty(value = "题目难易度级别")
	private Integer questionLevelId;
	/**题目的类型，比如单选、多选、判断等*/
	@Excel(name = "题目的类型，比如单选、多选、判断等", width = 15)
    @ApiModelProperty(value = "题目的类型，比如单选、多选、判断等")
	private Integer questionTypeId;
	/**题目的类型，比如数学、英语、政治等*/
	@Excel(name = "题目的类型，比如数学、英语、政治等", width = 15)
    @ApiModelProperty(value = "题目的类型，比如数学、英语、政治等")
	private Integer questionCategoryId;
	/**题目额外的描述*/
	@Excel(name = "题目额外的描述", width = 15)
    @ApiModelProperty(value = "题目额外的描述")
	private String questionDescription;
	/**题目的选项，用选项的id用-连在一起表示答案*/
	@Excel(name = "题目的选项，用选项的id用-连在一起表示答案", width = 15)
    @ApiModelProperty(value = "题目的选项，用选项的id用-连在一起表示答案")
	private String questionOptionIds;
	/**题目的答案，用选项的id用-连在一起表示答案*/
	@Excel(name = "题目的答案，用选项的id用-连在一起表示答案", width = 15)
    @ApiModelProperty(value = "题目的答案，用选项的id用-连在一起表示答案")
	private String questionAnswerOptionIds;
	/**创建时间*/
    @ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**更新时间*/
    @ApiModelProperty(value = "更新时间")
	private Date updateTime;
}
