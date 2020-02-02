package org.jeecg.modules.demo.question.service.impl;

import org.jeecg.modules.demo.question.entity.Question;
import org.jeecg.modules.demo.question.mapper.QuestionMapper;
import org.jeecg.modules.demo.question.service.IQuestionService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 问题
 * @Author: jeecg-boot
 * @Date:   2020-01-26
 * @Version: V1.0
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

}
