<template>
  <div class="block">
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="page"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="size"
      layout="total, sizes, prev, pager, next, jumper"
    >
    </el-pagination>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: {},
      size: 10, //每页显示数量
      page: 1,
    };
  },

  methods: {
    handleSizeChange(val) {
      this.size = val;
      this.page = 1;
    },
    handleCurrentChange(val) {
      this.page = val;
    },
  },
};
</script>

<style>
</style>