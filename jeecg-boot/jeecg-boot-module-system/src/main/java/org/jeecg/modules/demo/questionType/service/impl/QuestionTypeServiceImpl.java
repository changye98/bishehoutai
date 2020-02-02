package org.jeecg.modules.demo.questionType.service.impl;

import org.jeecg.modules.demo.questionType.entity.QuestionType;
import org.jeecg.modules.demo.questionType.mapper.QuestionTypeMapper;
import org.jeecg.modules.demo.questionType.service.IQuestionTypeService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 问题类型
 * @Author: jeecg-boot
 * @Date:   2020-01-26
 * @Version: V1.0
 */
@Service
public class QuestionTypeServiceImpl extends ServiceImpl<QuestionTypeMapper, QuestionType> implements IQuestionTypeService {

}
