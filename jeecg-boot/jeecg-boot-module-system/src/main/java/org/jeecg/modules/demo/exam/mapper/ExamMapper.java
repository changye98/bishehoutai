package org.jeecg.modules.demo.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.exam.entity.Exam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.demo.pagebean.PageBean;

/**
 * @Description: 考试
 * @Author: jeecg-boot
 * @Date:   2020-01-07
 * @Version: V1.0
 */
public interface ExamMapper extends BaseMapper<Exam> {
    List<Exam>  queryExamByPage(PageBean pageBean);
}
