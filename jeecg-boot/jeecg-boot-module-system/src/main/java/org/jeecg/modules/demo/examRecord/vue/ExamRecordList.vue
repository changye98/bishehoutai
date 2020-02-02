<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="考试记录表的主键">
              <a-input placeholder="请输入考试记录表的主键" v-model="queryParam.examRecordId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="考试参与者的用户id">
              <a-input placeholder="请输入考试参与者的用户id" v-model="queryParam.examJoinerId"></a-input>
            </a-form-item>
          </a-col>
        <template v-if="toggleSearchStatus">
        <a-col :md="6" :sm="8">
            <a-form-item label="参加考试的时间">
              <a-input placeholder="请输入参加考试的时间" v-model="queryParam.examJoinDate"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="完成考试所用的时间,单位分钟">
              <a-input placeholder="请输入完成考试所用的时间,单位分钟" v-model="queryParam.examTimeCost"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="参与考试的实际得分">
              <a-input placeholder="请输入参与考试的实际得分" v-model="queryParam.examJoinScore"></a-input>
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
      <a-button type="primary" icon="download" @click="handleExportXls('考试记录')">导出</a-button>
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
    <examRecord-modal ref="modalForm" @ok="modalFormOk"></examRecord-modal>
  </a-card>
</template>

<script>
  import ExamRecordModal from './modules/ExamRecordModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "ExamRecordList",
    mixins:[JeecgListMixin],
    components: {
      ExamRecordModal
    },
    data () {
      return {
        description: '考试记录管理页面',
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
            title: '考试记录表的主键',
            align:"center",
            dataIndex: 'examRecordId'
           },
		   {
            title: '考试参与者的用户id',
            align:"center",
            dataIndex: 'examJoinerId'
           },
		   {
            title: '参加考试的时间',
            align:"center",
            dataIndex: 'examJoinDate'
           },
		   {
            title: '完成考试所用的时间,单位分钟',
            align:"center",
            dataIndex: 'examTimeCost'
           },
		   {
            title: '参与考试的实际得分',
            align:"center",
            dataIndex: 'examJoinScore'
           },
		   {
            title: '考试结果的等级',
            align:"center",
            dataIndex: 'examResultLevel'
           },
		   {
            title: 'answerOptionIds',
            align:"center",
            dataIndex: 'answerOptionIds'
           },
		   {
            title: 'examId',
            align:"center",
            dataIndex: 'examId'
           },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/examRecord/examRecord/list",
          delete: "/examRecord/examRecord/delete",
          deleteBatch: "/examRecord/examRecord/deleteBatch",
          exportXlsUrl: "examRecord/examRecord/exportXls",
          importExcelUrl: "examRecord/examRecord/importExcel",
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