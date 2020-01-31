package org.jeecg.modules.demo.exam.entity;

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
 * @Description: 考试
 * @Author: jeecg-boot
 * @Date:   2020-01-07
 * @Version: V1.0
 */
@Data
@TableName("exam")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="exam对象", description="考试")
public class Exam {
    
	/**考试表的主键*/
	@Excel(name = "考试表的主键", width = 15)
    @ApiModelProperty(value = "考试表的主键")
	private String examId;
	/**考试名称*/
	@Excel(name = "考试名称", width = 15)
    @ApiModelProperty(value = "考试名称")
	private String examName;
	/**考试的预览图*/
	@Excel(name = "考试的预览图", width = 15)
    @ApiModelProperty(value = "考试的预览图")
	private String examAvatar;
	/**考试描述*/
	@Excel(name = "考试描述", width = 15)
    @ApiModelProperty(value = "考试描述")
	private String examDescription;
	/**当前考试下的题目的id用-连在一起地字符串*/
	@Excel(name = "当前考试下的题目的id用-连在一起地字符串", width = 15)
    @ApiModelProperty(value = "当前考试下的题目的id用-连在一起地字符串")
	private String examQuestionIds;
	/**当前考试下的题目单选题的id用-连在一起地字符串*/
	@Excel(name = "当前考试下的题目单选题的id用-连在一起地字符串", width = 15)
    @ApiModelProperty(value = "当前考试下的题目单选题的id用-连在一起地字符串")
	private String examQuestionIdsRadio;
	/**当前考试下的题目多选题的id用-连在一起地字符串*/
	@Excel(name = "当前考试下的题目多选题的id用-连在一起地字符串", width = 15)
    @ApiModelProperty(value = "当前考试下的题目多选题的id用-连在一起地字符串")
	private String examQuestionIdsCheck;
	/**当前考试下的题目判断题的id用-连在一起地字符串*/
	@Excel(name = "当前考试下的题目判断题的id用-连在一起地字符串", width = 15)
    @ApiModelProperty(value = "当前考试下的题目判断题的id用-连在一起地字符串")
	private String examQuestionIdsJudge;
	/**当前考试的总分数*/
	@Excel(name = "当前考试的总分数", width = 15)
    @ApiModelProperty(value = "当前考试的总分数")
	private Integer examScore;
	/**当前考试每个单选题的分数*/
	@Excel(name = "当前考试每个单选题的分数", width = 15)
    @ApiModelProperty(value = "当前考试每个单选题的分数")
	private Integer examScoreRadio;
	/**当前考试每个多选题的分数*/
	@Excel(name = "当前考试每个多选题的分数", width = 15)
    @ApiModelProperty(value = "当前考试每个多选题的分数")
	private Integer examScoreCheck;
	/**当前考试每个判断题的分数*/
	@Excel(name = "当前考试每个判断题的分数", width = 15)
    @ApiModelProperty(value = "当前考试每个判断题的分数")
	private Integer examScoreJudge;
	/**考试创建者的用户id*/
	@Excel(name = "考试创建者的用户id", width = 15)
    @ApiModelProperty(value = "考试创建者的用户id")
	private String examCreatorId;
	/**考试的时间限制，单位为分钟*/
	@Excel(name = "考试的时间限制，单位为分钟", width = 15)
    @ApiModelProperty(value = "考试的时间限制，单位为分钟")
	private Integer examTimeLimit;
	/**考试有效期开始时间*/
    @ApiModelProperty(value = "考试有效期开始时间")
	private Date examStartDate;
	/**考试有效期结束时间*/
    @ApiModelProperty(value = "考试有效期结束时间")
	private Date examEndDate;
	/**创建时间*/
    @ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**更新时间*/
    @ApiModelProperty(value = "更新时间")
	private Date updateTime;
}
