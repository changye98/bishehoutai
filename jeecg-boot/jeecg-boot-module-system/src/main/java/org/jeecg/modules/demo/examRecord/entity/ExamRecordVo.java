package org.jeecg.modules.demo.examRecord.entity;

import lombok.Data;
import org.jeecg.modules.demo.exam.entity.Exam;
import org.jeecg.modules.demo.user.entity.User;

@Data
public class ExamRecordVo {
    /**
     * 当前考试记录对应的考试
     */
    private Exam exam;

    /**
     * 当前考试对应的内容
     */
    private ExamRecord examRecord;

    /**
     * 参加考试的用户信息
     */
    private User user;
}
