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
          label="考试记录表的主键">
          <a-input placeholder="请输入考试记录表的主键" v-decorator="['examRecordId', validatorRules.examRecordId ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="考试参与者的用户id">
          <a-input placeholder="请输入考试参与者的用户id" v-decorator="['examJoinerId', validatorRules.examJoinerId ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="参加考试的时间">
          <a-input placeholder="请输入参加考试的时间" v-decorator="['examJoinDate', validatorRules.examJoinDate ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="完成考试所用的时间,单位分钟">
          <a-input-number v-decorator="[ 'examTimeCost', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="参与考试的实际得分">
          <a-input-number v-decorator="[ 'examJoinScore', validatorRules.examJoinScore ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="考试结果的等级">
          <a-input-number v-decorator="[ 'examResultLevel', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="answerOptionIds">
          <a-input placeholder="请输入answerOptionIds" v-decorator="['answerOptionIds', validatorRules.answerOptionIds ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="examId">
          <a-input placeholder="请输入examId" v-decorator="['examId', validatorRules.examId ]" />
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
    name: "ExamRecordModal",
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
        examRecordId:{rules: [{ required: true, message: '请输入考试记录表的主键!' }]},
        examJoinerId:{rules: [{ required: true, message: '请输入考试参与者的用户id!' }]},
        examJoinDate:{rules: [{ required: true, message: '请输入参加考试的时间!' }]},
        examJoinScore:{rules: [{ required: true, message: '请输入参与考试的实际得分!' }]},
        answerOptionIds:{rules: [{ required: true, message: '请输入answerOptionIds!' }]},
        examId:{rules: [{ required: true, message: '请输入examId!' }]},
        },
        url: {
          add: "/examRecord/examRecord/add",
          edit: "/examRecord/examRecord/edit",
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
          this.form.setFieldsValue(pick(this.model,'examRecordId','examJoinerId','examJoinDate','examTimeCost','examJoinScore','examResultLevel','answerOptionIds','examId'))
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