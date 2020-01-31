package org.jeecg.modules.demo.exam.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.demo.exam.entity.Exam;
import org.jeecg.modules.demo.exam.entity.ExamDetail;
import org.jeecg.modules.demo.exam.entity.QuestionDetail;
import org.jeecg.modules.demo.exam.service.IExamService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.demo.examRecord.entity.ExamRecord;
import org.jeecg.modules.demo.examRecord.entity.ExamRecordVo;
import org.jeecg.modules.demo.examRecord.entity.RecordDetailVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 考试
 * @Author: jeecg-boot
 * @Date:   2020-01-07
 * @Version: V1.0
 */
@Slf4j
@Api(tags="考试")
@RestController
@RequestMapping("/exam")
public class ExamController {
	@Autowired
	private IExamService examService;
	
	/**
	  * 分页列表查询
	 * @param exam
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "考试-分页列表查询")
	@ApiOperation(value="考试-分页列表查询", notes="考试-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Exam>> queryPageList(Exam exam,
									  @RequestParam(name="currentPage", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<Exam>> result = new Result<>();
		QueryWrapper<Exam> queryWrapper = QueryGenerator.initQueryWrapper(exam, req.getParameterMap());
		Page<Exam> page = new Page<>(pageNo, pageSize);
		IPage<Exam> pageList = examService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	 /**
	  *   考试详情
	  * @param id
	  * @return
	  */
	 @GetMapping("/detail/{id}")
	 @ApiOperation("根据考试的id，获取考试详情")
	 Result<ExamDetail> getExamDetail(@PathVariable String id) {
		 // 根据id获取考试详情
		 Result<ExamDetail> result = new Result<>();
		 try {
			 ExamDetail examDetail = examService.getExamDetail(id);
			 result.setResult(examDetail);
			 result.setSuccess(true);
			 result.setMessage("获取考试详情成功");
		 } catch (Exception e) {
			 result.setSuccess(false);
			 result.setMessage("获取考试详情失败");
		 }
		 return result;
	 }
	 /**
	  *   问题详情
	  * @param id
	  * @return
	  */
	 @GetMapping("/question/detail/{id}")
	 @ApiOperation("根据问题的id获取问题的详细信息")
	 Result<QuestionDetail> getQuestionDetail(@PathVariable String id) {
		 //  根据问题id获取问题的详细信息
		 Result<QuestionDetail> result = new Result<>();
		 try {
			 QuestionDetail questionDetail = examService.getQuestionDetail(id);
			 result.setMessage("获取问题详情成功");
			 result.setSuccess(true);
			 result.setResult(questionDetail);
		 } catch (Exception e) {
			 e.printStackTrace();
			 result.setMessage("获取问题详情失败");
			 result.setSuccess(false);
		 }
		 return result;
	 }
	 @PostMapping ("/finish/{examId}/{userId}")
	 @ApiOperation("根据用户提交的答案对指定id的考试判分")
	 Result<ExamRecord> finishExam(@PathVariable String userId, @PathVariable String examId, @RequestBody HashMap<String, List<String>> answersMap, HttpServletRequest request) {
		 Result<ExamRecord> result = new Result<>();
//		 HashMap<String, List<String>>
//		 Map map = (Map) JSON.parse(answersMap) ;
		 try {
			 // 下面根据用户提交的信息进行判分,返回用户的得分情况
			 ExamRecord examRecord = examService.judge(userId, examId, answersMap);
			 result.setSuccess(true);
			 result.setMessage("考卷提交成功");
		 } catch (Exception e) {
			 e.printStackTrace();
			 result.setSuccess(false);
			 result.setMessage("考卷提交失败");
		 }
		 return result;
	 }
	 @GetMapping("/record/list/{userId}")
	 @ApiOperation("获取当前用户的考试记录")
	 Result<List<ExamRecordVo>> getExamRecordList(@PathVariable String userId,HttpServletRequest request) {
		 Result<List<ExamRecordVo>> result = new Result<>();
		 try {
			 // 下面根据用户账号拿到他(她所有的考试信息)，注意要用VO封装下
			 List<ExamRecordVo> examRecordVoList = examService.getExamRecordList(userId);
			 result.setResult(examRecordVoList);
			 result.setSuccess(true);
			 result.setMessage("获取考试记录成功");
		 } catch (Exception e) {
			 e.printStackTrace();
			 result.setSuccess(false);
			 result.setMessage("获取考试记录失败");
		 }
		 return result;
	 }
     @GetMapping("/record/detail/{recordId}")
     @ApiOperation("根据考试记录id获取考试记录详情")
     Result<RecordDetailVo> getExamRecordDetail(@PathVariable String recordId) {
         Result<RecordDetailVo> result = new Result<>();
         try {
             RecordDetailVo recordDetailVo = examService.getRecordDetail(recordId);
             result.setSuccess(true);
             result.setMessage("获取考试记录详情成功");
             result.setResult(recordDetailVo);
         } catch (Exception e) {
             e.printStackTrace();
             result.setSuccess(false);
             result.setMessage("获取考试记录详情失败");
         }
         return result;
     }




}
