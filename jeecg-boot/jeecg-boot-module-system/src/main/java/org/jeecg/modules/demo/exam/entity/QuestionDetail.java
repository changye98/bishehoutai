package org.jeecg.modules.demo.exam.entity;

import lombok.Data;
import org.jeecg.modules.demo.questionOption.entity.QuestionOption;

import java.util.ArrayList;
import java.util.List;
@Data
public class QuestionDetail {
    /**
     * 问题的id
     */
    private String id;

    /**
     * 考试题目
     */
    private String name;

    /**
     * 考试描述
     */
    private String description;
    /**
     * 问题的类型
     */
    private String type;
    /**
     * 问题的选项
     */
    private List<QuestionOption> options;
    /**
     * 问题的答案,选项的id组成的数组
     */
    private List<String> answers = new ArrayList<>();
}
