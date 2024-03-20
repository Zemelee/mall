<template>
  <div>
    <el-container class="el-container" style="height: 100vh;">
      <el-header style="height: 10%">
        <Header style="border:1px solid blue;" @send-category="shift" @send-keyword="searchProduction"></Header>
      </el-header>
      <el-main class="el-main" style="border: 1px solid red;">
        <el-form :inline="true">
          <el-radio-group v-model="sortParam" style="margin: 0 10px 0 10px;">
            <el-radio label="asc" size="large">价格升序</el-radio>
            <el-radio label="des" size="large">价格降序</el-radio>
          </el-radio-group>
          <el-input v-model="minPrice" placeholder="最低价"  size="small"/>
          <el-input v-model="maxPrice" placeholder="最高价"  size="small"/>
          <el-button @click="reset">重置</el-button>
          <el-button @click="filterByPrice">过滤</el-button>
        </el-form>
        <div class="products-display">
          <Product v-for="(item, index) in products.list" :product="item" :key="index" @click="goDetails(item.id)" />
        </div>
      </el-main>
      <el-footer class="el-footer">
        <Footer />
      </el-footer>
    </el-container>
  </div>
</template>


<script setup>
import service from "../../request/index.js";
import Header from "./Header.vue";
import Footer from "./Footer.vue";
import Product from "../../components/Product.vue";
// import Product from "@/components/Product.vue";
import { ref, reactive, onMounted, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { ElMessage } from "element-plus";

const route = useRoute();
const router = useRouter();
const products = reactive({
  list: [],
});
const headCategory = ref(1);
onMounted(async () => {
  try {
    // 按种类请求product
    const res = await service.get(`/mall/product/category=1`);
    products.list = res.data;
  } catch (err) {
    if (err.response.status == 401) {
      ElMessage.error("非法登录");
      router.push("/login");
    }
  }
});
// 排序、过滤、重置
const sortParam = ref("asc");
const sortByPrice = (param) => {
  if (param === "asc") {
    products.list.sort((a, b) => a.price - b.price);
  } else if (param === "des") {
    products.list.sort((a, b) => b.price - a.price);
  }
};

// 监听 sortParam 的变化，调用 sortByPrice 函数实现排序
watch(sortParam, (newValue) => {
  sortByPrice(newValue);
});

const minPrice = ref(0);
const maxPrice = ref(99999);
const filterByPrice = () => {
  if(minPrice.value && maxPrice.value){
    products.list = products.list.filter((item) => item.price >= minPrice.value && item.price <= maxPrice.value);
  }else if(minPrice.value){
    products.list = products.list.filter((item) => item.price >= minPrice.value);
  }else if(maxPrice.value){
    products.list = products.list.filter((item) => item.price <= maxPrice.value);
  }
};


const reset = async () => {
  const res = await service.get(`/mall/product/category=${headCategory.value}`);
  products.list = res.data;
};

async function searchProduction(keyword) {
  console.log(keyword.value);
  // 按关键字请求product
  await service
    .get(`/mall/product/search=${keyword.value}`)
    .then((res) => {
      products.list = res.data;
    })
    .catch((err) => {
      if (err.response.status == 401) {
        ElMessage.error("非法登录");
        router.push("/login");
      } else if (err.response.status == 404) {
        ElMessage.error("查无此商品，后续上线...");
      }
    });
}

async function shift(category) {
  headCategory.value = category;
  // 按种类请求product
  await service
    .get(`/mall/product/category=${category}`)
    .then((res) => {
      products.list = res.data;
    })
    .catch((err) => {
      if (err.response.status == 401) {
        ElMessage.error("非法登录");
        router.push("/login");
      }
    });
}

const goDetails = (id) => {
  router.push({ name: "details", params: { id: id }, target: "_blank" });
}
</script>

<style scoped>
.el-container {
  /* width: 90vw; */

}

.el-footer {
  position: relative;
  background-color: aliceblue;
}


.el-main {
  .products-display {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
  }
}

.el-main::after {
  content: "";
  flex: auto;
}
</style>