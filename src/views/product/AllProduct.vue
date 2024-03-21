<template>
  <div>
    <el-table border ref="multipleTable" :data="productions" tooltip-effect="dark" style="width: 100%"
      @selection-change="handleSelectionChange">
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
    <!-- Form -->
    <el-button type="text" @click="fn">Dialog</el-button>

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
export default {
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
      formLabelWidth: '120px'
    }
  },
  created() {
    service.post("/mall/product/get", { page: 6, size: 10 }).then((response) => {
      this.productions = response;
    });
  },
  methods: {
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleEdit(index, row) {
      console.log(index, row);
      this.editForm = true;

    },
    handleDelete(index, row) {
      console.log(index, row);
    },
    fn() {
      this.editForm = true;
    }
  }
}
</script>