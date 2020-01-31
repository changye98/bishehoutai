<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="考试表的主键">
              <a-input placeholder="请输入考试表的主键" v-model="queryParam.examId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="考试名称">
              <a-input placeholder="请输入考试名称" v-model="queryParam.examName"></a-input>
            </a-form-item>
          </a-col>
        <template v-if="toggleSearchStatus">
        <a-col :md="6" :sm="8">
            <a-form-item label="考试的预览图">
              <a-input placeholder="请输入考试的预览图" v-model="queryParam.examAvatar"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="考试描述">
              <a-input placeholder="请输入考试描述" v-model="queryParam.examDescription"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="当前考试下的题目的id用-连在一起地字符串">
              <a-input placeholder="请输入当前考试下的题目的id用-连在一起地字符串" v-model="queryParam.examQuestionIds"></a-input>
            </a-form-item>
          </a-col>
          </template>
          <a-col :md="6" :sm="8" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('考试')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <exam-modal ref="modalForm" @ok="modalFormOk"></exam-modal>
  </a-card>
</template>

<script>
  import ExamModal from './modules/ExamModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "ExamList",
    mixins:[JeecgListMixin],
    components: {
      ExamModal
    },
    data () {
      return {
        description: '考试管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
           },
		   {
            title: '考试表的主键',
            align:"center",
            dataIndex: 'examId'
           },
		   {
            title: '考试名称',
            align:"center",
            dataIndex: 'examName'
           },
		   {
            title: '考试的预览图',
            align:"center",
            dataIndex: 'examAvatar'
           },
		   {
            title: '考试描述',
            align:"center",
            dataIndex: 'examDescription'
           },
		   {
            title: '当前考试下的题目的id用-连在一起地字符串',
            align:"center",
            dataIndex: 'examQuestionIds'
           },
		   {
            title: '当前考试下的题目单选题的id用-连在一起地字符串',
            align:"center",
            dataIndex: 'examQuestionIdsRadio'
           },
		   {
            title: '当前考试下的题目多选题的id用-连在一起地字符串',
            align:"center",
            dataIndex: 'examQuestionIdsCheck'
           },
		   {
            title: '当前考试下的题目判断题的id用-连在一起地字符串',
            align:"center",
            dataIndex: 'examQuestionIdsJudge'
           },
		   {
            title: '当前考试的总分数',
            align:"center",
            dataIndex: 'examScore'
           },
		   {
            title: '当前考试每个单选题的分数',
            align:"center",
            dataIndex: 'examScoreRadio'
           },
		   {
            title: '当前考试每个多选题的分数',
            align:"center",
            dataIndex: 'examScoreCheck'
           },
		   {
            title: '当前考试每个判断题的分数',
            align:"center",
            dataIndex: 'examScoreJudge'
           },
		   {
            title: '考试创建者的用户id',
            align:"center",
            dataIndex: 'examCreatorId'
           },
		   {
            title: '考试的时间限制，单位为分钟',
            align:"center",
            dataIndex: 'examTimeLimit'
           },
		   {
            title: '考试有效期开始时间',
            align:"center",
            dataIndex: 'examStartDate'
           },
		   {
            title: '考试有效期结束时间',
            align:"center",
            dataIndex: 'examEndDate'
           },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/exam/exam/list",
          delete: "/exam/exam/delete",
          deleteBatch: "/exam/exam/deleteBatch",
          exportXlsUrl: "exam/exam/exportXls",
          importExcelUrl: "exam/exam/importExcel",
       },
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
    methods: {
     
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>