package org.jeecg.modules.demo.examRecord.entity;

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
 * @Description: 考试记录
 * @Author: jeecg-boot
 * @Date:   2020-01-27
 * @Version: V1.0
 */
@Data
@TableName("exam_record")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="exam_record对象", description="考试记录")
public class ExamRecord {
    
	/**考试记录表的主键*/
	@Excel(name = "考试记录表的主键", width = 15)
    @ApiModelProperty(value = "考试记录表的主键")
	private String examRecordId;
	/**考试参与者的用户id*/
	@Excel(name = "考试参与者的用户id", width = 15)
    @ApiModelProperty(value = "考试参与者的用户id")
	private String examJoinerId;
	/**参加考试的时间*/
    @ApiModelProperty(value = "参加考试的时间")
	private Date examJoinDate;
	/**完成考试所用的时间,单位分钟*/
	@Excel(name = "完成考试所用的时间,单位分钟", width = 15)
    @ApiModelProperty(value = "完成考试所用的时间,单位分钟")
	private Integer examTimeCost;
	/**参与考试的实际得分*/
	@Excel(name = "参与考试的实际得分", width = 15)
    @ApiModelProperty(value = "参与考试的实际得分")
	private Integer examJoinScore;
	/**考试结果的等级*/
	@Excel(name = "考试结果的等级", width = 15)
    @ApiModelProperty(value = "考试结果的等级")
	private Integer examResultLevel;
	/**answerOptionIds*/
	@Excel(name = "answerOptionIds", width = 15)
    @ApiModelProperty(value = "answerOptionIds")
	private String answerOptionIds;
	/**examId*/
	@Excel(name = "examId", width = 15)
    @ApiModelProperty(value = "examId")
	private String examId;
}
