<template>
  <div>
    <el-pagination :current-page="currentPages" :page-size="pageSize" :page-sizes="[10, 20, 30, 40]" :total="pageTotal"
      :layout="layout" @size-change="sizeChange" @current-change="handleCurrentChange" @prev-click="prev"
      @next-click="next" />
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentPages: this.currentPage,
      pageData: {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
      }
    }
  },
  props: {
    pageTotal: {
      type: Number,
      default: 0, //总页数
    },
    layout: {
      type: String,
      default: " total, sizes, prev, pager, next, jumper, ->, slot", //分页组件会展示的功能项
    },
    pageSizes: {
      type: Array,
      default: [10, 20, 30, 40, 50],
    },
    currentPage: {
      type: Number,
      default: 1, //指定跳转到多少页
    },
    pageSize: {
      type: Number,
      default: 30, //每页展示的条数，根据自己实际，且会带入请求
    },
    pageNum: {
      type: Number,
      default: 1, //第几页数据，根据自己实际，且会带入请求
    }
  },
  watch: {
    currentPage: function (newPage) {
      this.currentPages = newPage;
    }
  },
  methods: {
    //选择每页显示数量
    sizeChange(val) {
      this.pageData.pageSize = val;
      this.$emit("pageFunc", this.pageData);
    },
    //选择某一页
    handleCurrentChange(val) {
      this.currentPages = val;
      this.pageData.pageNum = val;
      this.$emit("pageFunc", this.pageData);
    },
    //上一页
    prev() {
      this.$emit("pageFunc", this.pageData);
    },
    //下一页
    next() {
      this.$emit("pageFunc", this.pageData);
    }
  }
}
</script>