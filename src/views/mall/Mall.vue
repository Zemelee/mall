<template>
  <div>
    <el-container class="el-container" style="height: 100vh;">
      <el-header style="height: 10%">
        <Header style="border:1px solid blue;" @send-category="shift" @send-keyword="searchProduction"></Header>
      </el-header>

      <el-main class="el-main" style="border: 1px solid red;">
        <el-form :inline="true">
          <el-form-item style="margin-right: 20px;">
            <el-switch size="small" v-model="showCarousel" :active-action-icon="View" :inactive-action-icon="Hide" />&nbsp;&nbsp;{{ $t("Head.category.recommend") }}
          </el-form-item>
          <el-form-item style="margin-right: 20px;">
            <el-radio-group v-model="sortParam">
              <el-radio label="asc" size="large">{{ $t("Mall.aes") }}</el-radio>
              <el-radio label="des" size="large">{{ $t("Mall.des") }}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item :label="$t('Mall.priceRange')" prop="priceRange">
            <el-row type="flex" justify="start">
              <el-col :span="8">
                <el-input v-model="minPrice" :placeholder="$t('Mall.minPrice')" size="small" />
              </el-col>
              <el-col :span="1" style="line-height: 36px; text-align: center;">-</el-col>
              <el-col :span="8">
                <el-input v-model="maxPrice" :placeholder="$t('Mall.maxPrice')" size="small" />
              </el-col>
            </el-row>
          </el-form-item>
          <el-form-item>
            <el-button @click="reset">{{ $t("Mall.reset") }}</el-button>
            <el-button @click="filterByPrice">{{ $t("Mall.filter") }}</el-button>
          </el-form-item>
        </el-form>
        <transition name="fade">
          <el-carousel v-show="showCarousel" :interval="4000" type="card" height="200px">
            <el-carousel-item v-for="(recommendItem, index) in recommendList" :key="index"
              @click="goDetails(recommendItem.id)">
              <div>
                <div> <img :src="recommendItem.picsrc" style="max-width: 100%; max-height: 100%;"></div>
                <div class="recommend-text" style="color:blue">{{ recommendItem.name }}</div>
              </div>
            </el-carousel-item>
          </el-carousel>
        </transition>
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
import './style.css'
import service from "../../request/index.js";
import Header from "./Header.vue";
import Footer from "./Footer.vue";
import Product from "../../components/Product.vue";
import { Hide, View } from '@element-plus/icons-vue'
import { ref, reactive, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import { useI18n } from "vue-i18n";
const { t, locale } = useI18n();
const router = useRouter();
const products = reactive({
  list: [],
});
const recommendList = ref([]);
const headCategory = ref(1);
onMounted(async () => {
  try {
    // 按种类请求product
    const res1 = await service.get(`/mall/product/recommend/uid=${localStorage.getItem("userid")}`);
    console.log(res1)
    recommendList.value = res1.data;
    const res2 = await service.get(`/mall/product/category=1`);
    products.list = res2.data;
  } catch (err) {
    console.log(err);
    if (err.response.status == 401) {
      ElMessage.error("非法登录");
      router.push("/login");
    }
  }
});

const showCarousel = ref(true)
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
  if (minPrice.value && maxPrice.value) {
    products.list = products.list.filter((item) => item.price >= minPrice.value && item.price <= maxPrice.value);
  } else if (minPrice.value) {
    products.list = products.list.filter((item) => item.price >= minPrice.value);
  } else if (maxPrice.value) {
    products.list = products.list.filter((item) => item.price <= maxPrice.value);
  }
};


const reset = async () => {
  const res = await service.get(`/mall/product/category=${headCategory.value}`);
  products.list = res.data;
  maxPrice.value = 99999;
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
  service.post(`/user/click`, { uid: localStorage.getItem("userid"), pid: id })
  router.push({ name: "details", params: { id: id }, target: "_blank" });
}
</script>

<style scoped lang="less">
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

/* 过渡动画样式 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>