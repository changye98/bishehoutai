<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
      
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="题目的主键">
          <a-input placeholder="请输入题目的主键" v-decorator="['questionId', validatorRules.questionId ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="题目的名字">
          <a-input placeholder="请输入题目的名字" v-decorator="['questionName', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="题目的分数">
          <a-input-number v-decorator="[ 'questionScore', validatorRules.questionScore ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="题目创建者的用户id">
          <a-input placeholder="请输入题目创建者的用户id" v-decorator="['questionCreatorId', validatorRules.questionCreatorId ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="题目难易度级别">
          <a-input-number v-decorator="[ 'questionLevelId', validatorRules.questionLevelId ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="题目的类型，比如单选、多选、判断等">
          <a-input-number v-decorator="[ 'questionTypeId', validatorRules.questionTypeId ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="题目的类型，比如数学、英语、政治等">
          <a-input-number v-decorator="[ 'questionCategoryId', validatorRules.questionCategoryId ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="题目额外的描述">
          <a-input placeholder="请输入题目额外的描述" v-decorator="['questionDescription', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="题目的选项，用选项的id用-连在一起表示答案">
          <a-input placeholder="请输入题目的选项，用选项的id用-连在一起表示答案" v-decorator="['questionOptionIds', validatorRules.questionOptionIds ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="题目的答案，用选项的id用-连在一起表示答案">
          <a-input placeholder="请输入题目的答案，用选项的id用-连在一起表示答案" v-decorator="['questionAnswerOptionIds', validatorRules.questionAnswerOptionIds ]" />
        </a-form-item>
		
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: "QuestionModal",
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
        questionId:{rules: [{ required: true, message: '请输入题目的主键!' }]},
        questionScore:{rules: [{ required: true, message: '请输入题目的分数!' }]},
        questionCreatorId:{rules: [{ required: true, message: '请输入题目创建者的用户id!' }]},
        questionLevelId:{rules: [{ required: true, message: '请输入题目难易度级别!' }]},
        questionTypeId:{rules: [{ required: true, message: '请输入题目的类型，比如单选、多选、判断等!' }]},
        questionCategoryId:{rules: [{ required: true, message: '请输入题目的类型，比如数学、英语、政治等!' }]},
        questionOptionIds:{rules: [{ required: true, message: '请输入题目的选项，用选项的id用-连在一起表示答案!' }]},
        questionAnswerOptionIds:{rules: [{ required: true, message: '请输入题目的答案，用选项的id用-连在一起表示答案!' }]},
        },
        url: {
          add: "/question/question/add",
          edit: "/question/question/edit",
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
          this.form.setFieldsValue(pick(this.model,'questionId','questionName','questionScore','questionCreatorId','questionLevelId','questionTypeId','questionCategoryId','questionDescription','questionOptionIds','questionAnswerOptionIds'))
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

</style>