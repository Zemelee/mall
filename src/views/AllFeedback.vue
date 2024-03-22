<template>
  <div>
    <Box>
      <div class="operate">
        <el-col :span="4">
          <el-button type="primary" @click="batchHandling">批量处理</el-button></el-col>
        <el-col :span="6">
          <div class="checkbox-group">
            <!-- '待处理': 0,'已处理': 1 -->
            <el-checkbox-group v-model="hadleType">
              <el-checkbox label="已处理" name="type" />
              <el-checkbox label="待处理" name="type" />
            </el-checkbox-group>
          </div>
        </el-col>
        <el-col :span="6">
          <el-input placeholder="输入反馈关键词" v-model="keyword" @keyup.enter="searchFeedback" /></el-col>
        <el-button type="primary" @click="searchFeedback">
          搜索</el-button>
      </div>
    </Box>
    <el-table v-loading="loading" :data="feedbacks" border style="width: 100%"
      :default-sort="{ prop: 'submitTime', order: 'descending' }" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column prop="username" label="用户昵称" width="90">
      </el-table-column>
      <el-table-column prop="feedback" label="反馈内容" width="100">
      </el-table-column>
      <el-table-column prop="submitTime" sortable label="反馈时间" width="200">
        <template #default="scope">
          {{ new Date(scope.row.submitTime).toISOString().slice(0, 19).replace("T", " ") }}
        </template>
      </el-table-column>


      <el-table-column prop="email" label="邮箱" width="100">
      </el-table-column>


      <el-table-column label="状态" width="100">
        <template #default="scope">
          <!-- 假设scope.row.status表示状态，已处理为'handled' -->
          <el-tag :type="scope.row.status === 'handled' ? 'success' : ''">{{ scope.row.status === 'handled' ? '已处理' :
          '待处理' }}</el-tag>
        </template>
      </el-table-column>


      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button size="mini" @click="handleLook(scope.row)">查看</el-button>
          <!-- 根据状态判断是否禁用按钮 -->
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)"
            :disabled="scope.row.status != '0'">{{ scope.row.status == '0' ? '待处理' : '已处理' }}</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <el-dialog v-model="dialogVisible">
    <div>

      <div class="feedback-detail">
        <div class="feedback-detail-content">
          <div>昵称：{{ currentRow.username }}</div>
          <div>邮箱：{{ currentRow.email }}</div>
        </div>
        <div class="feedback-detail">
          <div class="feedback-detail-content">{{ currentRow.feedback }}</div>
        </div>
        <hr>
        <div class="feedback-detail">
          <div class="feedback-detail-content">
            <div>时间：{{ new Date(currentRow.submitTime).toISOString().slice(0, 19).replace("T", " ") }}</div>
            <div>处理状态：undefined</div>
          </div>
        </div>
      </div>
    </div>
  </el-dialog>

</template>

<script>
import service from '@/request';
import Paginations from '@/components/Paginations'
import Box from '@/components/Box'
export default {
  components: { Paginations, Box },
  data() {
    return {
      feedbacks: [],
      hadleType: ['待处理'],
      times: [],//依据时间处理
      keyword: "",
      loading: false,
      dialogVisible: false,
      currentRow: {}//当前行数据
    }
  },
  created() {
    service.get("/user/feedback/get").then((response) => {
      this.feedbacks = response;
    });
  },
  computed: {
    handleType() {
      const mapping = {
        '待处理': 0,
        '已处理': 1
      };
      return this.hadleType.map(item => mapping[item])
    }
  },
  methods: {
    handleSelectionChange(val) {
      this.times = [];
      for (let i = 0; i < val.length; i++) {
        this.times.push(val[i].submitTime)
      }
    },
    // 搜索反馈内容
    searchFeedback() {
      this.loading = true;
      const param = {
        keyword: this.keyword,
        type: this.handleType
      }
      service.get("/user/feedback/search", param).then((response) => {
        this.feedbacks = response;
        this.loading = false;
      }).catch(() => {
        this.loading = false;
        this.$message({
          type: 'error',
          message: '搜索出错'
        });
      });
    },
    handleLook(row) {
      this.currentRow = row;
      console.log(this.currentRow);
      this.dialogVisible = true;
    },

    handleDelete(index, row) {
      this.$confirm('是否标记为已处理?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        service.post("/user/feedback/handle", [row.submitTime]).then(res => {
          if (res == "ok") {
            this.$message({
              type: 'success',
              message: '删除成功'
            });
          } else {
            this.$message({
              type: 'error',
              message: '删除失败'
            });
          }
        })
      })
    },

    // 根据时间处理反馈
    batchHandling() {
      if (this.times.length == 0) {
        this.$message.warning("请选择反馈项");
      } else {
        this.$confirm('是否批量处理?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          console.log(this.times);
          service.post("/user/feedback/handle", this.times).then(res => {
            console.log(res);
            if (res == "ok") {
              this.$message({
                type: 'success',
                message: '批量处理成功'
              });
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消批量处理'
          });
        });

      }
    }
  }

}
</script>

<style>
.operate {
  display: flex;
  align-items: center;
  justify-content: space-around;
  width: 700px;
  margin-bottom: 10px;
  margin-top: 10px;
}
</style>