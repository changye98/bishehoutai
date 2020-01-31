package org.jeecg.modules.demo.exam.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.demo.exam.entity.Exam;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.demo.exam.entity.ExamDetail;
import org.jeecg.modules.demo.exam.entity.QuestionDetail;
import org.jeecg.modules.demo.examRecord.entity.ExamRecord;
import org.jeecg.modules.demo.examRecord.entity.ExamRecordVo;
import org.jeecg.modules.demo.examRecord.entity.RecordDetailVo;
import org.jeecg.modules.demo.pagebean.PageBean;

import java.util.HashMap;
import java.util.List;

/**
 * @Description: 考试
 * @Author: jeecg-boot
 * @Date:   2020-01-07
 * @Version: V1.0
 */
public interface IExamService extends IService<Exam> {
    List<Exam> queryExamByPage(PageBean pageBean);
     ExamDetail getExamDetail(String id );
    QuestionDetail getQuestionDetail(String id);
    ExamRecord judge(String userId, String examId, HashMap<String, List<String>> answersMap);
     List<ExamRecordVo> getExamRecordList(String userId);
    RecordDetailVo getRecordDetail(String recordId);
}
