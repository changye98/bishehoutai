package org.jeecg.modules.demo.examRecordLevel.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.examRecordLevel.entity.ExamRecordLevel;
import org.jeecg.modules.demo.examRecordLevel.service.IExamRecordLevelService;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 考试成绩等级
 * @Author: jeecg-boot
 * @Date:   2020-01-27
 * @Version: V1.0
 */
@Slf4j
@Api(tags="考试成绩等级")
@RestController
@RequestMapping("/examRecordLevel/examRecordLevel")
public class ExamRecordLevelController {
	@Autowired
	private IExamRecordLevelService examRecordLevelService;
	
	/**
	  * 分页列表查询
	 * @param examRecordLevel
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "考试成绩等级-分页列表查询")
	@ApiOperation(value="考试成绩等级-分页列表查询", notes="考试成绩等级-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ExamRecordLevel>> queryPageList(ExamRecordLevel examRecordLevel,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<ExamRecordLevel>> result = new Result<IPage<ExamRecordLevel>>();
		QueryWrapper<ExamRecordLevel> queryWrapper = QueryGenerator.initQueryWrapper(examRecordLevel, req.getParameterMap());
		Page<ExamRecordLevel> page = new Page<ExamRecordLevel>(pageNo, pageSize);
		IPage<ExamRecordLevel> pageList = examRecordLevelService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param examRecordLevel
	 * @return
	 */
	@AutoLog(value = "考试成绩等级-添加")
	@ApiOperation(value="考试成绩等级-添加", notes="考试成绩等级-添加")
	@PostMapping(value = "/add")
	public Result<ExamRecordLevel> add(@RequestBody ExamRecordLevel examRecordLevel) {
		Result<ExamRecordLevel> result = new Result<ExamRecordLevel>();
		try {
			examRecordLevelService.save(examRecordLevel);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param examRecordLevel
	 * @return
	 */
	@AutoLog(value = "考试成绩等级-编辑")
	@ApiOperation(value="考试成绩等级-编辑", notes="考试成绩等级-编辑")
	@PutMapping(value = "/edit")
	public Result<ExamRecordLevel> edit(@RequestBody ExamRecordLevel examRecordLevel) {
		Result<ExamRecordLevel> result = new Result<ExamRecordLevel>();
		ExamRecordLevel examRecordLevelEntity = examRecordLevelService.getById(examRecordLevel.getExamRecordLevelId());
		if(examRecordLevelEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = examRecordLevelService.updateById(examRecordLevel);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}
		
		return result;
	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "考试成绩等级-通过id删除")
	@ApiOperation(value="考试成绩等级-通过id删除", notes="考试成绩等级-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			examRecordLevelService.removeById(id);
		} catch (Exception e) {
			log.error("删除失败",e.getMessage());
			return Result.error("删除失败!");
		}
		return Result.ok("删除成功!");
	}
	
	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "考试成绩等级-批量删除")
	@ApiOperation(value="考试成绩等级-批量删除", notes="考试成绩等级-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<ExamRecordLevel> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<ExamRecordLevel> result = new Result<ExamRecordLevel>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.examRecordLevelService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "考试成绩等级-通过id查询")
	@ApiOperation(value="考试成绩等级-通过id查询", notes="考试成绩等级-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ExamRecordLevel> queryById(@RequestParam(name="id",required=true) String id) {
		Result<ExamRecordLevel> result = new Result<ExamRecordLevel>();
		ExamRecordLevel examRecordLevel = examRecordLevelService.getById(id);
		if(examRecordLevel==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(examRecordLevel);
			result.setSuccess(true);
		}
		return result;
	}

  /**
      * 导出excel
   *
   * @param request
   * @param response
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
      // Step.1 组装查询条件
      QueryWrapper<ExamRecordLevel> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              ExamRecordLevel examRecordLevel = JSON.parseObject(deString, ExamRecordLevel.class);
              queryWrapper = QueryGenerator.initQueryWrapper(examRecordLevel, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<ExamRecordLevel> pageList = examRecordLevelService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "考试成绩等级列表");
      mv.addObject(NormalExcelConstants.CLASS, ExamRecordLevel.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("考试成绩等级列表数据", "导出人:Jeecg", "导出信息"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
  }

  /**
      * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<ExamRecordLevel> listExamRecordLevels = ExcelImportUtil.importExcel(file.getInputStream(), ExamRecordLevel.class, params);
              examRecordLevelService.saveBatch(listExamRecordLevels);
              return Result.ok("文件导入成功！数据行数:" + listExamRecordLevels.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.ok("文件导入失败！");
  }

}
