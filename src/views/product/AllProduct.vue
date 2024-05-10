<template>
  <div>
    <Box>
      <el-button @click="delMul" type="danger" >批量删除</el-button>
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

      <el-table-column prop="sales" label="已售" width="100">
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
    <el-dialog v-model="editForm" title="编辑商品" width="70%">
      <el-form :model="currentRow" ref="productForm" label-width="120px">
        <el-form-item label="ID" prop="id">
          <el-input v-model="currentRow.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="currentRow.name" placeholder="请输入商品名称"></el-input>
        </el-form-item>
        <el-form-item label="商品图片" prop="picsrc">
          <el-input v-model="currentRow.picsrc" placeholder="请输入商品图片链接"></el-input>
        </el-form-item>
        <el-form-item label="商品最低价" prop="price">
          <el-input v-model="currentRow.price" placeholder="请输入商品最低价"></el-input>
        </el-form-item>
        <el-form-item label="商品种类" prop="category">
          <el-select v-model="currentRow.category" placeholder="请选择商品种类">
            <el-option label="电子设备" value="1"></el-option>
            <el-option label="食品" value="2"></el-option>
            <el-option label="服装" value="3"></el-option>
            <el-option label="问卷" value="4"></el-option>
            <el-option label="周边" value="5"></el-option>
            <el-option label="服饰" value="6"></el-option>
            <el-option label="信息" value="7"></el-option>
            <el-option label="二手" value="8"></el-option>
            <el-option label="折扣" value="9"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品描述" prop="description">
          <el-input v-model="currentRow.description" placeholder="请输入商品描述"></el-input>
        </el-form-item>
        <el-form-item label="规格属性" prop="attributions">
          <el-table :data="currentRow.attributions" border style="width: 100%">
            <el-table-column prop="attrval" label="规格属性" width="160">
              <template v-slot="scope">
                <el-input v-model="scope.row.attrval" placeholder="请输入规格属性"></el-input>
              </template>
            </el-table-column>
            <el-table-column prop="inventory" label="库存" width="100">
              <template v-slot="scope">
                <el-input v-model="scope.row.inventory" placeholder="请输入库存"></el-input>
              </template>
            </el-table-column>
            <el-table-column prop="more" label="差价" width="100">
              <template v-slot="scope">
                <el-input v-model="scope.row.more" placeholder="请输入差价"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template v-slot="scope">
                <el-button size="mini" type="danger" @click="delAttribution(scope.$index, scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
      </el-form>

      <Box>
        <el-button type="primary" @click="addAttribution">新增属性</el-button>
        <el-button type="primary" @click="confirmModify">确 定</el-button>
      </Box>
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
      loading: false,
      currentRow: {},
    }
  },
  created() {
    service.post("/mall/product/get", { page: 1, size: 10 }).then((response) => {
      this.productions = response;
      // this.page.total = response.length;
    });
  },
  methods: {
    pageFunc(pageInfo) {
      // 偏移量
      service.post("/mall/product/get", { page: (pageInfo.pageNum), size: pageInfo.pageSize }).then((response) => {
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
      if (this.ids.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选择要删除的商品'
        });
        return;
      }
      const startTime = Date.now();
      this.loading = true;
      let delMsg;
      try {
        delMsg = await service.post("/mall/product/del", this.ids);
        if (delMsg == "ok") {
          this.$message({
            type: 'success',
            message: '删除成功'
          });
          this.$router.go(0);
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
      this.currentRow = row;
      this.editForm = true;
    },
    addAttribution() {
      this.currentRow.attributions.push({ product_id: this.currentRow.id, attrval: "默认规格", inventory: 1, more: 0 });
    },
    handleDelete(index, row) {
      this.$confirm('是否删除此商品?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        const delMsg = await service.post("/mall/product/del", [row.id])
        console.log("delMsg", delMsg)
        if (delMsg == "ok") {
          this.$message({
            type: 'success',
            message: '删除成功'
          });
          setTimeout(() => {
            this.$router.go(0);
          }, 1000);
        } else {
          throw new Error("删除失败");
        }

      })
    },
    delAttribution(index, row) {
      this.currentRow.attributions.splice(index, 1);
      console.log(this.currentRow.attributions);
    },
    confirmModify() {
      console.log(this.currentRow);
      service.post(`/mall/product/modify`, this.currentRow).then((response) => {
        console.log(response);
        if (response === "ok") {
          this.$message.success("修改成功");
          service.post("/mall/product/get", { page: 1, size: 10 }).then((response) => {
            this.productions = response;
          });
          this.editForm = false;
        }
      })
    }
  }

}
</script>