<template>
  <div>
    <Box>
      <el-button @click="delMul">批量删除</el-button>
    </Box>
    <el-table v-loading="loading" border ref="multipleTable" :data="productions" tooltip-effect="dark"
      style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column prop="id" label="ID" width="100">
      </el-table-column>
      <el-table-column prop="picsrc" label="图片" width="60">
        <template v-slot="scope">
          <img :src="scope.row.picsrc" style="width: 50px;height: 50px">
        </template>
      </el-table-column>
      <el-table-column prop="name" label="名称" width="130">
      </el-table-column>
      <el-table-column prop="price" label="最低价" width="160">
        <template v-slot="scope">
          <el-tag>￥{{ scope.row.price }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="category" label="种类" width="160">
        <template v-slot="scope">
          <el-tag v-if="scope.row.category == 1">电子设备</el-tag>
          <el-tag v-if="scope.row.category == 2">食品</el-tag>
          <el-tag v-if="scope.row.category == 3">服装</el-tag>
          <el-tag v-if="scope.row.category == 4">问卷</el-tag>
          <el-tag v-if="scope.row.category == 5">其他</el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="sales" label="已售" width="160">
      </el-table-column>
      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <Box v-if="productions.length !== 0">
      <Paginations :page-size="page.pageSize" :layout="layout" :current-page="page.pageNum" :pageTotal="page.total"
        @pageFunc="pageFunc"></Paginations>
    </Box>

    <!-- Form -->
    <el-dialog title="收货地址" v-model="editForm">
      <el-form :model="form">
        <el-form-item label="活动名称" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="活动区域" :label-width="formLabelWidth">
          <el-select v-model="form.region" placeholder="请选择活动区域">
            <el-option label="区域一" value="shanghai"></el-option>
            <el-option label="区域二" value="beijing"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div class="dialog-footer">
      </div>
    </el-dialog>
  </div>

</template>

<script>
import service from '@/request';
import Paginations from '@/components/Paginations'
import Box from '@/components/Box'
export default {
  components: { Paginations, Box },
  data() {
    return {
      productions: [],
      multipleSelection: [],
      editForm: false,
      productForm: {
        id: '',
        name: '',
        picsrc: '',
        price: 0,
        category: 0,
        sales: 0,
        description: '',
        state: 0
      },
      formLabelWidth: '120px',
      // 放置页码及相关数据
      page: {
        pageNum: 1, //当前页
        pageSize: 10, //每页条目数
        total: 30, // 记录总数
        totalPage: 1, //页数
      },
      layout: "total, sizes, prev, pager, next, jumper, ->, slot", //分页组件会展示的功能项
      ids: [],//表格多选时的数据id
      loading: false
    }
  },
  created() {
    service.post("/mall/product/get", { page: 1, size: 10 }).then((response) => {
      this.productions = response;
    });
  },
  methods: {
    pageFunc(pageInfo) {
      // 偏移量
      service.post("/mall/product/get", { page: (pageInfo.pageNum) * pageInfo.pageSize, size: pageInfo.pageSize }).then((response) => {
        this.productions = response;
      });
    },
    handleSelectionChange(val) {
      this.ids = [];
      for (let item of val) {
        const index = this.ids.findIndex(id => id === item.id);
        if (index === -1) {
          this.ids.push(item.id);
        }
      }
    },
    async delMul() {
      const startTime = Date.now();
      this.loading = true;
      let delMsg;
      try {
        delMsg = await service.post("/mall/product/del", this.ids);
        if (delMsg === "ok") {
          const response = await service.post("/mall/product/get", { page: 1, size: 10 });
          this.productions = response;
        } else {
          throw new Error("删除失败");
        }
      } catch (error) {
        console.error(error);
      } finally {
        setTimeout(() => {
          this.loading = false;
        }, 3000);
        const elapsedTime = Date.now() - startTime;
        if (elapsedTime < 3000 && delMsg !== "ok") {
          setTimeout(() => {
            this.$message.error("删除失败");
          }, 3000 - elapsedTime);
        }
      }
    },
    handleEdit(index, row) {
      console.log(index, row);
      this.editForm = true;
    },
    handleDelete(index, row) {
      console.log(index, row);
    },
  }
}
</script>