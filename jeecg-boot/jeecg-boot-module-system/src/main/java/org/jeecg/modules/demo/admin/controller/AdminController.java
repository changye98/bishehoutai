//package org.jeecg.modules.demo.admin.controller;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.jeecg.common.api.vo.Result;
//import org.jeecg.common.system.query.QueryGenerator;
//import org.jeecg.common.aspect.annotation.AutoLog;
//import org.jeecg.common.util.oConvertUtils;
//import org.jeecg.modules.demo.admin.entity.Admin;
//import org.jeecg.modules.demo.admin.service.IAdminService;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import lombok.extern.slf4j.Slf4j;
//
//import org.jeecg.modules.demo.admin.service.impl.ListTestServiceImpl;
//import org.jeecg.modules.demo.admin.service.impl.LoginServiceImplTest;
//import org.jeecgframework.poi.excel.ExcelImportUtil;
//import org.jeecgframework.poi.excel.def.NormalExcelConstants;
//import org.jeecgframework.poi.excel.entity.ExportParams;
//import org.jeecgframework.poi.excel.entity.ImportParams;
//import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//import org.springframework.web.servlet.ModelAndView;
//import com.alibaba.fastjson.JSON;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
// /**
// * @Description: ceshi
// * @Author: jeecg-boot
// * @Date:   2019-11-20
// * @Version: V1.0
// */
//@Slf4j
//@Api(tags="ceshi")
//@RestController
//@RequestMapping("/test/admin")
//public class AdminController {
//	@Autowired
//	private IAdminService adminService;
//	@Autowired
//	private LoginServiceImplTest loginServiceTest ;
//	@Autowired
//    private  RedisTemplate<String,Object> redisTemplate ;
//	@Resource(name="redisTemplate")
//     HashOperations<String, String, Admin> hash  ;
//	@Autowired
//	private  ListTestServiceImpl list ;
//	//List 集合练习用于 热点新闻,大量数据分页展示
//	@GetMapping("/listTest")
//	public  String listTest(String fx ){
////		ListTestServiceImpl list = new ListTestServiceImpl();
//        System.out.println(fx);
//		long page =1   ;
//		long pageSize =3  ;
//		list.listAdd();
//		for ( String item:
//				list.listQueryPage(page,pageSize)) {
//			System.out.println(item );
//		}
//		System.out.println(list.listQueryTotal());
//		return "zhaoyi"  ;
//	}
//	//实体存储测试
//	@GetMapping("/duixiangTest")
//     public  void  ssss() {
//		String id  = "2" ;
//         if(hash.hasKey(Admin.getName(), id.toString() )){
//             System.out.println("redis查询");
//             System.out.println(hash.get(Admin.getName(),id.toString()));
//         }else{
//             Admin admin = new Admin();
//             admin.setId(Integer.parseInt(id));
//             admin.setPassword("hello");
//             admin.setUsername("zhangsan");
//             hash.put(Admin.getName(),id,admin);
//             System.out.println("mysql查询");
//             System.out.println(admin);
//         }
//     }
//     //登录限制
//	@GetMapping("/dengluxianzhiTest")
//	public String test(@RequestParam("username") String username){
//        Map<String, Object> map = loginServiceTest.loginLockCheck(username);
//        if((Boolean) map.get("flag")){
//            System.out.println(map.get("msg"));
//            return (String) map.get("msg");
//        }else{
//            String s = loginServiceTest.loginFailTimes(username);
//            System.out.println(s);
//            return s;
//        }
//	}
//
//	/**
//	  * 分页列表查询
//	 * @param admin
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@AutoLog(value = "ceshi-分页列表查询")
//	@ApiOperation(value="ceshi-分页列表查询", notes="ceshi-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<Admin>> queryPageList(Admin admin,
//									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//									  HttpServletRequest req) {
//		Result<IPage<Admin>> result = new Result<IPage<Admin>>();
//		QueryWrapper<Admin> queryWrapper = QueryGenerator.initQueryWrapper(admin, req.getParameterMap());
//		Page<Admin> page = new Page<Admin>(pageNo, pageSize);
//		IPage<Admin> pageList = adminService.page(page, queryWrapper);
//		result.setSuccess(true);
//		result.setResult(pageList);
//		return result;
//	}
//
//	/**
//	  *   添加
//	 * @param admin
//	 * @return
//	 */
//	@AutoLog(value = "ceshi-添加")
//	@ApiOperation(value="ceshi-添加", notes="ceshi-添加")
//	@PostMapping(value = "/add")
//	public Result<Admin> add(@RequestBody Admin admin) {
//		Result<Admin> result = new Result<Admin>();
//		try {
//			adminService.save(admin);
//			result.success("添加成功！");
//		} catch (Exception e) {
//			log.error(e.getMessage(),e);
//			result.error500("操作失败");
//		}
//		return result;
//	}
//
//	/**
//	  *  编辑
//	 * @param admin
//	 * @return
//	 */
//	@AutoLog(value = "ceshi-编辑")
//	@ApiOperation(value="ceshi-编辑", notes="ceshi-编辑")
//	@PutMapping(value = "/edit")
//	public Result<Admin> edit(@RequestBody Admin admin) {
//		Result<Admin> result = new Result<Admin>();
//		Admin adminEntity = adminService.getById(admin.getId());
//		if(adminEntity==null) {
//			result.error500("未找到对应实体");
//		}else {
//			boolean ok = adminService.updateById(admin);
//			//TODO 返回false说明什么？
//			if(ok) {
//				result.success("修改成功!");
//			}
//		}
//
//		return result;
//	}
//
//	/**
//	  *   通过id删除
//	 * @param id
//	 * @return
//	 */
//	@AutoLog(value = "ceshi-通过id删除")
//	@ApiOperation(value="ceshi-通过id删除", notes="ceshi-通过id删除")
//	@DeleteMapping(value = "/delete")
//	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
//		try {
//			adminService.removeById(id);
//		} catch (Exception e) {
//			log.error("删除失败",e.getMessage());
//			return Result.error("删除失败!");
//		}
//		return Result.ok("删除成功!");
//	}
//
//	/**
//	  *  批量删除
//	 * @param ids
//	 * @return
//	 */
//	@AutoLog(value = "ceshi-批量删除")
//	@ApiOperation(value="ceshi-批量删除", notes="ceshi-批量删除")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<Admin> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		Result<Admin> result = new Result<Admin>();
//		if(ids==null || "".equals(ids.trim())) {
//			result.error500("参数不识别！");
//		}else {
//			this.adminService.removeByIds(Arrays.asList(ids.split(",")));
//			result.success("删除成功!");
//		}
//		return result;
//	}
//
//	/**
//	  * 通过id查询
//	 * @param id
//	 * @return
//	 */
//	@AutoLog(value = "ceshi-通过id查询")
//	@ApiOperation(value="ceshi-通过id查询", notes="ceshi-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<Admin> queryById(@RequestParam(name="id",required=true) String id) {
//		Result<Admin> result = new Result<Admin>();
//		Admin admin = adminService.getById(id);
//		if(admin==null) {
//			result.error500("未找到对应实体");
//		}else {
//			result.setResult(admin);
//			result.setSuccess(true);
//		}
//		return result;
//	}
//
//  /**
//      * 导出excel
//   *
//   * @param request
//   * @param response
//   */
//  @RequestMapping(value = "/exportXls")
//  public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
//      // Step.1 组装查询条件
//      QueryWrapper<Admin> queryWrapper = null;
//      try {
//          String paramsStr = request.getParameter("paramsStr");
//          if (oConvertUtils.isNotEmpty(paramsStr)) {
//              String deString = URLDecoder.decode(paramsStr, "UTF-8");
//              Admin admin = JSON.parseObject(deString, Admin.class);
//              queryWrapper = QueryGenerator.initQueryWrapper(admin, request.getParameterMap());
//          }
//      } catch (UnsupportedEncodingException e) {
//          e.printStackTrace();
//      }
//
//      //Step.2 AutoPoi 导出Excel
//      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//      List<Admin> pageList = adminService.list(queryWrapper);
//      //导出文件名称
//      mv.addObject(NormalExcelConstants.FILE_NAME, "ceshi列表");
//      mv.addObject(NormalExcelConstants.CLASS, Admin.class);
//      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("ceshi列表数据", "导出人:Jeecg", "导出信息"));
//      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
//      return mv;
//  }
//
//  /**
//      * 通过excel导入数据
//   *
//   * @param request
//   * @param response
//   * @return
//   */
//  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
//  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
//      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
//      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
//          MultipartFile file = entity.getValue();// 获取上传文件对象
//          ImportParams params = new ImportParams();
//          params.setTitleRows(2);
//          params.setHeadRows(1);
//          params.setNeedSave(true);
//          try {
//              List<Admin> listAdmins = ExcelImportUtil.importExcel(file.getInputStream(), Admin.class, params);
//              adminService.saveBatch(listAdmins);
//              return Result.ok("文件导入成功！数据行数:" + listAdmins.size());
//          } catch (Exception e) {
//              log.error(e.getMessage(),e);
//              return Result.error("文件导入失败:"+e.getMessage());
//          } finally {
//              try {
//                  file.getInputStream().close();
//              } catch (IOException e) {
//                  e.printStackTrace();
//              }
//          }
//      }
//      return Result.ok("文件导入失败！");
//  }
//
//}
