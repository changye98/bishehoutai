package org.jeecg.modules.demo.exam.service.impl;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.demo.exam.entity.Exam;
import org.jeecg.modules.demo.examRecord.entity.ExamRecord;
import org.jeecg.modules.demo.examRecord.entity.ExamRecordVo;
import org.jeecg.modules.demo.examRecord.entity.RecordDetailVo;
import org.jeecg.modules.demo.examRecord.mapper.ExamRecordMapper;
import org.jeecg.modules.demo.question.entity.Question;
import org.jeecg.modules.demo.exam.entity.ExamDetail;
import org.jeecg.modules.demo.exam.entity.QuestionDetail;
import org.jeecg.modules.demo.exam.mapper.ExamMapper;
import org.jeecg.modules.demo.questionOption.entity.QuestionOption;
import org.jeecg.modules.demo.exam.service.IExamService;
import org.jeecg.modules.demo.pagebean.PageBean;
import org.jeecg.modules.demo.question.mapper.QuestionMapper;
import org.jeecg.modules.demo.questionOption.mapper.QuestionOptionMapper;
import org.jeecg.modules.demo.questionType.entity.QuestionType;
import org.jeecg.modules.demo.questionType.mapper.QuestionTypeMapper;
import org.jeecg.modules.demo.user.entity.User;
import org.jeecg.modules.demo.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.*;

/**
 * @Description: 考试
 * @Author: jeecg-boot
 * @Date:   2020-01-07
 * @Version: V1.0
 */
@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements IExamService {
    @Autowired
    private ExamMapper examMapper ;
    @Autowired
    private QuestionMapper questionMapper ;
    @Autowired
    private QuestionTypeMapper questionTypeMapper ;
    @Autowired
    private QuestionOptionMapper questionOptionMapper ;
    @Autowired
    private ExamRecordMapper examRecordMapper ;
    @Autowired
    private UserMapper userMapper ;
    @Override
    public List<Exam> queryExamByPage(PageBean pageBean) {
        pageBean.setStart( (pageBean.getCurrentPage()-1)*pageBean.getPageSize() );
        return  examMapper.queryExamByPage(pageBean);
    }
    public  ExamDetail getExamDetail(String id ){
        Exam exam = examMapper.selectOne(new QueryWrapper<Exam>().eq("exam_id", id));
        ExamDetail examDetail = new ExamDetail();
        examDetail.setExam(exam);
        examDetail.setRadioIds(exam.getExamQuestionIdsRadio().split("-"));
        examDetail.setCheckIds(exam.getExamQuestionIdsCheck().split("-"));
        examDetail.setJudgeIds(exam.getExamQuestionIdsJudge().split("-"));
        return  examDetail ;
    }

    @Override
    public List<ExamRecordVo> getExamRecordList(String userId) {
        // 获取指定用户下的考试记录列表
        List<ExamRecord> examRecordList = examRecordMapper.selectList(new QueryWrapper<ExamRecord>().eq("exam_joiner_id",userId));
        List<ExamRecordVo> examRecordVoList = new ArrayList<>();
        for (ExamRecord examRecord : examRecordList) {
            ExamRecordVo examRecordVo = new ExamRecordVo();
            Exam exam = examMapper.selectOne(new QueryWrapper<Exam>().eq("exam_id",examRecord.getExamId()));
            examRecordVo.setExam(exam);
            User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_id",examRecord.getExamJoinerId()));
            examRecordVo.setUser(user);
            examRecordVo.setExamRecord(examRecord);
            examRecordVoList.add(examRecordVo);
        }
        return examRecordVoList;
    }

    @Override
    public RecordDetailVo getRecordDetail(String recordId) {
        // 获取考试详情的封装对象
        ExamRecord record = examRecordMapper.selectOne(new QueryWrapper<ExamRecord>().eq("exam_record_id",recordId));
        RecordDetailVo recordDetailVo = new RecordDetailVo();
        recordDetailVo.setExamRecord(record);
        // 用户的答案，需要解析
        HashMap<String, List<String>> answersMap = new HashMap<>();
        HashMap<String, String> resultsMap = new HashMap<>();
        assert record != null;
        String answersStr = record.getAnswerOptionIds();
        if("未答卷".equals(answersStr)){
            recordDetailVo.setAnswersMap(null);
            recordDetailVo.setResultsMap(null);
        }else{
            // $分隔题目,因为$在正则中有特殊用途(行尾)，所以需要括起来
            String[] questionArr = answersStr.split("[$]");
            for (String questionStr : questionArr) {
                System.out.println(questionStr);
                // 区分开题目标题和选项
                String[] questionTitleResultAndOption = questionStr.split("_");
                String[] questionTitleAndResult = questionTitleResultAndOption[0].split("@");
                String[] questionOptions = questionTitleResultAndOption[1].split("-");
                // 题目：答案选项
                answersMap.put(questionTitleAndResult[0], Arrays.asList(questionOptions));
                // 题目：True / False
                resultsMap.put(questionTitleAndResult[0], questionTitleAndResult[1]);
            }
            recordDetailVo.setAnswersMap(answersMap);
            recordDetailVo.setResultsMap(resultsMap);
        }

        // 下面再计算正确答案的map
        ExamDetail examDetailVo = getExamDetail(record.getExamId());
        List<String> questionIdList = new ArrayList<>();
        questionIdList.addAll(Arrays.asList(examDetailVo.getRadioIds()));
        questionIdList.addAll(Arrays.asList(examDetailVo.getCheckIds()));
        questionIdList.addAll(Arrays.asList(examDetailVo.getJudgeIds()));
        // 获取所有的问题对象
        List<Question> questionList = questionMapper.selectList(new QueryWrapper<Question>().in("question_id",questionIdList));
        HashMap<String, List<String>> answersRightMap = new HashMap<>();
        for (Question question : questionList) {
            // 记得去掉最后可能出现的特殊字符
            String questionAnswerOptionIdsStr = replaceLastSeparator(question.getQuestionAnswerOptionIds());
            String[] questionAnswerOptionIds = questionAnswerOptionIdsStr.split("-");
            answersRightMap.put(question.getQuestionId(), Arrays.asList(questionAnswerOptionIds));
        }
        recordDetailVo.setAnswersRightMap(answersRightMap);
        return recordDetailVo;
    }

    @Override
    public QuestionDetail getQuestionDetail(String id) {
        Question question = questionMapper.selectOne(new QueryWrapper<Question>().eq("question_id",id));
        QuestionDetail questionDetail = new QuestionDetail();
        questionDetail.setId(id);
        questionDetail.setName(question.getQuestionName());
        questionDetail.setDescription(question.getQuestionDescription());
        String questionAnswerOptionIdsStr = replaceLastSeparator(question.getQuestionAnswerOptionIds());
        String[] questionAnswerOptionIds = questionAnswerOptionIdsStr.split("-");
        questionDetail.setAnswers(Arrays.asList(questionAnswerOptionIds));
        // 问题类型，单选题/多选题/判断题
        questionDetail.setType(questionTypeMapper.selectOne(new QueryWrapper<QuestionType>().eq("question_type_id",question.getQuestionTypeId())).getQuestionTypeDescription());
        // 获取当前问题的选项
        String optionIdsStr = trimMiddleLine(question.getQuestionOptionIds());
        String[] optionIds = optionIdsStr.split("-");
        // 获取选项列表
        List<QuestionOption> optionList = questionOptionMapper.selectList(new QueryWrapper<QuestionOption>().in("question_option_id",Arrays.asList(optionIds)));
        //List<QuestionOption> optionList = questionOptionMapper.selectBatchIds(Arrays.asList(optionIds));
        questionDetail.setOptions(optionList);
        return questionDetail;
    }
    /**
     * 去除字符串最后的，防止split的时候出错
     *
     * @param str 原始字符串
     * @return
     */
    public static String trimMiddleLine(String str) {
        if (str.charAt(str.length() - 1) == '-') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
    @Override
    public ExamRecord judge(String userId, String examId, HashMap<String, List<String>> answersMap) {
        if(answersMap.size()==0){
                    //啥也没答就交
        ExamRecord examRecord = new ExamRecord();
        examRecord.setExamRecordId(IdUtil.simpleUUID());
        examRecord.setExamId(examId);
        // 注意去掉最后可能有的&_-
        examRecord.setAnswerOptionIds("未答卷");
        examRecord.setExamJoinerId(userId);
        examRecord.setExamJoinDate(new Date());
        examRecord.setExamJoinScore(0);
        examRecordMapper.insert(examRecord);
            return examRecord;
        }
        // 开始考试判分啦~~~
        // 1.首先获取考试对象和选项数组
        ExamDetail examDetail = getExamDetail(examId);
        Exam exam = examDetail.getExam();
        // 2.然后获取该考试下所有的题目信息
        List<String> questionIds = new ArrayList<>();
        // 2.1 题目id的数组
        List<String> radioIdList = Arrays.asList(examDetail.getRadioIds());
        List<String> checkIdList = Arrays.asList(examDetail.getCheckIds());
        List<String> judgeIdList = Arrays.asList(examDetail.getJudgeIds());
        questionIds.addAll(radioIdList);
        questionIds.addAll(checkIdList);
        questionIds.addAll(judgeIdList);
        // 2.2 每种题目的分数
        int radioScore = exam.getExamScoreRadio();
        int checkScore = exam.getExamScoreCheck();
        int judgeScore = exam.getExamScoreJudge();
        // 2.3 根据问题id的数组拿到所有的问题对象，供下面步骤用
        List<Question> questionList = questionMapper.selectList(new QueryWrapper<Question>().in("question_id",questionIds));
        Map<String, Question> questionMap = new HashMap<>();
        for (Question question : questionList) {
            questionMap.put(question.getQuestionId(), question);
        }
        // 3.根据正确答案和用户作答信息进行判分
        Set<String> questionIdsAnswer = answersMap.keySet();
        // 存储当前考试每个题目的得分情况
        Map<String, Integer> judgeMap = new HashMap<>();
        // 考生作答地每个题目的选项(题目和题目之间用$分隔，题目有多个选项地话用-分隔,题目和选项之间用_分隔),用于查看考试详情
        // 例子：题目1的id_作答选项1-作答选项2&题目2的id_作答选项1&题目3_作答选项1-作答选项2-作答选项3
        StringBuilder answerOptionIdsSb = new StringBuilder();
        // 用户此次考试的总分
        int totalScore = 0;
        for (String questionId : questionIdsAnswer) {
            // 获取用户作答地这个题的答案信息
            Question question = questionMap.get(questionId);
            // 获取答案选项
            String questionAnswerOptionIds = replaceLastSeparator(question.getQuestionAnswerOptionIds());
            List<String> questionAnswerOptionIdList = Arrays.asList(questionAnswerOptionIds.split("-"));
            Collections.sort(questionAnswerOptionIdList);
            String answerStr = listConcat(questionAnswerOptionIdList);
            // 获取用户作答
            List<String> questionUserOptionIdList = answersMap.get(questionId);
            Collections.sort(questionUserOptionIdList);
            String userStr = listConcat(questionUserOptionIdList);
            // 判断questionAnswerOptionIds和answersMap里面的答案是否相等
            if (answerStr.equals(userStr)) {
                // 说明题目作答正确,下面根据题型给分
                int score = 0;
                if (radioIdList.contains(questionId)) {
                    score = radioScore;
                }
                if (checkIdList.contains(questionId)) {
                    score = checkScore;
                }
                if (judgeIdList.contains(questionId)) {
                    score = judgeScore;
                }
                // 累计本次考试得分
                totalScore += score;
                // True代表题目答对
                answerOptionIdsSb.append(questionId + "@True_" + userStr + "$");
                judgeMap.put(questionId, score);
            } else {
                // 说明题目作答错误,直接判零分,False代表题目答错
                answerOptionIdsSb.append(questionId + "@False_" + userStr + "$");
                judgeMap.put(questionId, 0);
            }
        }
        // 4.计算得分，记录本次考试结果，存到ExamRecord中
        ExamRecord examRecord = new ExamRecord();
        examRecord.setExamRecordId(IdUtil.simpleUUID());
        examRecord.setExamId(examId);
        // 注意去掉最后可能有的&_-
        examRecord.setAnswerOptionIds(replaceLastSeparator(answerOptionIdsSb.toString()));
        examRecord.setExamJoinerId(userId);
        examRecord.setExamJoinDate(new Date());
        examRecord.setExamJoinScore(totalScore);
        examRecordMapper.insert(examRecord);
        return examRecord;
    }
    /**
     * 把字符串最后一个字符-替换掉
     *
     * @param str 原始字符串
     * @return 替换掉最后一个-的字符串
     */
    private String replaceLastSeparator(String str) {
        String lastChar = str.substring(str.length() - 1);
        // 题目和题目之间用$分隔，题目有多个选项地话用-分隔,题目和选项之间用_分隔
        if ("-".equals(lastChar) || "_".equals(lastChar) || "$".equals(lastChar)) {
            str = StrUtil.sub(str, 0, str.length() - 1);
        }
        return str;
    }
    /**
     * 把字符串用-连接起来
     *
     * @param strList 字符串列表
     * @return 拼接好的字符串，记住要去掉最后面的-
     */
    private String listConcat(List<String> strList) {
        StringBuilder sb = new StringBuilder();
        for (String str : strList) {
            sb.append(str);
            sb.append("-");
        }
        return replaceLastSeparator(sb.toString());
    }
}
