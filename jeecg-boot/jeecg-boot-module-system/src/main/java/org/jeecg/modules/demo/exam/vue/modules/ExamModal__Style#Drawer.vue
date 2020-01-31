<template>
  <a-drawer
      :title="title"
      :width="800"
      placement="right"
      :closable="false"
      @close="close"
      :visible="visible"
  >

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
      
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="考试表的主键">
          <a-input placeholder="请输入考试表的主键" v-decorator="['examId', validatorRules.examId ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="考试名称">
          <a-input placeholder="请输入考试名称" v-decorator="['examName', validatorRules.examName ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="考试的预览图">
          <a-input placeholder="请输入考试的预览图" v-decorator="['examAvatar', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="考试描述">
          <a-input placeholder="请输入考试描述" v-decorator="['examDescription', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="当前考试下的题目的id用-连在一起地字符串">
          <a-input placeholder="请输入当前考试下的题目的id用-连在一起地字符串" v-decorator="['examQuestionIds', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="当前考试下的题目单选题的id用-连在一起地字符串">
          <a-input placeholder="请输入当前考试下的题目单选题的id用-连在一起地字符串" v-decorator="['examQuestionIdsRadio', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="当前考试下的题目多选题的id用-连在一起地字符串">
          <a-input placeholder="请输入当前考试下的题目多选题的id用-连在一起地字符串" v-decorator="['examQuestionIdsCheck', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="当前考试下的题目判断题的id用-连在一起地字符串">
          <a-input placeholder="请输入当前考试下的题目判断题的id用-连在一起地字符串" v-decorator="['examQuestionIdsJudge', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="当前考试的总分数">
          <a-input-number v-decorator="[ 'examScore', validatorRules.examScore ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="当前考试每个单选题的分数">
          <a-input-number v-decorator="[ 'examScoreRadio', validatorRules.examScoreRadio ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="当前考试每个多选题的分数">
          <a-input-number v-decorator="[ 'examScoreCheck', validatorRules.examScoreCheck ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="当前考试每个判断题的分数">
          <a-input-number v-decorator="[ 'examScoreJudge', validatorRules.examScoreJudge ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="考试创建者的用户id">
          <a-input placeholder="请输入考试创建者的用户id" v-decorator="['examCreatorId', validatorRules.examCreatorId ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="考试的时间限制，单位为分钟">
          <a-input-number v-decorator="[ 'examTimeLimit', validatorRules.examTimeLimit ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="考试有效期开始时间">
          <a-input placeholder="请输入考试有效期开始时间" v-decorator="['examStartDate', validatorRules.examStartDate ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="考试有效期结束时间">
          <a-input placeholder="请输入考试有效期结束时间" v-decorator="['examEndDate', validatorRules.examEndDate ]" />
        </a-form-item>
		
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: "ExamModal",
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules:{
        examId:{rules: [{ required: true, message: '请输入考试表的主键!' }]},
        examName:{rules: [{ required: true, message: '请输入考试名称!' }]},
        examScore:{rules: [{ required: true, message: '请输入当前考试的总分数!' }]},
        examScoreRadio:{rules: [{ required: true, message: '请输入当前考试每个单选题的分数!' }]},
        examScoreCheck:{rules: [{ required: true, message: '请输入当前考试每个多选题的分数!' }]},
        examScoreJudge:{rules: [{ required: true, message: '请输入当前考试每个判断题的分数!' }]},
        examCreatorId:{rules: [{ required: true, message: '请输入考试创建者的用户id!' }]},
        examTimeLimit:{rules: [{ required: true, message: '请输入考试的时间限制，单位为分钟!' }]},
        examStartDate:{rules: [{ required: true, message: '请输入考试有效期开始时间!' }]},
        examEndDate:{rules: [{ required: true, message: '请输入考试有效期结束时间!' }]},
        },
        url: {
          add: "/exam/exam/add",
          edit: "/exam/exam/edit",
        },
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'examId','examName','examAvatar','examDescription','examQuestionIds','examQuestionIdsRadio','examQuestionIdsCheck','examQuestionIdsJudge','examScore','examScoreRadio','examScoreCheck','examScoreJudge','examCreatorId','examTimeLimit','examStartDate','examEndDate'))
		  //时间格式化
        });

      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            //时间格式化
            
            console.log(formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })



          }
        })
      },
      handleCancel () {
        this.close()
      },


    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>