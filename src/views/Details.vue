<template>
  <div class="product">
    <div v-if="details" style="display: flex;">
      <div>
        <h1>{{ details.name }}</h1>
        <div class="product-image">
          <img :src="details.picsrc" />
        </div>
        <p>{{ details.description }}</p>
      </div>


      <div style="margin-left: 120px;margin-top: 80px;">
        <div><!-- 将 id 的值和 attrid 绑定-->
          <el-select v-model="attrid" @change="changeAttr" placeholder="选择规格">
            <el-option v-for="attr in details.attributions" :key="attr.id" :label="attr.attrval" :value="attr.id" />
          </el-select>
        </div>

        <br>
        <el-input-number v-model="numSelect" @change="debouncedAddNumSelect" :min="1" />
        <!-- 通过 find 方法和 id 匹配来获取对应的 attributions 对象 -->
        <div>
          商品单价:
          {{
            attrid == 0
              ? details.price
              : details.price +
              details.attributions.find((a) => a.id === attrid).more
          }}
        </div>
        <div v-if="inventory">库存剩余: {{ inventory }}</div>
        <div>
          <el-button @click="addCart"> 加入购物车 </el-button>
        </div>
        <div>

          <el-button @click="router.push('/profile/cart')"> 跳转购物车 </el-button>
        </div>
      </div>

    </div>

  </div>
</template>

<script setup>
import service from "../request/index.js";
import { ref, onMounted, watch } from "vue";
import { useCartStore } from "@/store/cart.js";
import { useRoute, useRouter } from "vue-router";
import { ElMessage } from "element-plus";
const route = useRoute();
const router = useRouter();
const Cart = useCartStore();
const details = ref(null);
onMounted(async () => {
  details.value = await getById(route.params.id); //包含attributions数组和基本信息
});
// 防抖函数
const debounce = (func, delay) => {
  let timerId;
  return function (...args) {
    clearTimeout(timerId);
    timerId = setTimeout(() => {
      func.apply(this, args);
    }, delay);
  };
}
const attrid = ref(0); // 用户规格选择id
const numSelect = ref(1); // 用户数量选择
let inventory = ref(0)
async function getById(id) {
  return service
    .get(`/mall/product/id=${id}`)
    .then((res) => {
      return res.data;
    })
    .catch((err) => {
      console.log("err:", err);
    });
}
// 监听规格选择,显示库存
watch(attrid, (newAttrId, oldAttrId) => {
  let tempAttrbution = details.value.attributions.find(item => item.id == newAttrId)
  inventory.value = tempAttrbution.inventory
})

const addNumSelect = () => {
  if (attrid.value == 0) {
    ElMessage.warning("未选择规格");
    numSelect.value = 1;
    return;
  }
  const targetSelect = details.value.attributions.find((a) => a.id == attrid.value) //对应的某项attributions
  if (targetSelect.inventory < numSelect.value) {
    ElMessage.warning("库存不足");
    numSelect.value = targetSelect.inventory;
    return
  }
};
const debouncedAddNumSelect = debounce(addNumSelect, 300); // 创建防抖函数
const changeAttr = () => {
  // 防止超过规格库存
  numSelect.value = 1;
};
// 防抖函数
let cartTimeout;
function addCart() {
  clearTimeout(cartTimeout);
  cartTimeout = setTimeout(() => {
    if (attrid.value == 0) {
      ElMessage.error("未选择规格");
      return;
    }
    const newGoods = {
      productid: Number(route.params.id),
      attrid: attrid.value,
      num: numSelect.value,
    };
    let cartList = JSON.parse(localStorage.getItem("cartList")) || [];
    // 查找购物车是否已存在该商品(判断商品ID + 规格ID)
    let productInCart = cartList.find((item) => {
      return (
        item.productid === newGoods.productid && item.attrid === newGoods.attrid
      );
    });
    // 有就直接+1，否则添加到购物车
    if (productInCart) {
      productInCart.num += newGoods.num;
      ElMessage.success("添加成功");
    } else {
      cartList.push(newGoods);
      ElMessage.success("添加成功");
    }
    localStorage.setItem("cartList", JSON.stringify(cartList));
    Cart.updateProducts(cartList)
  }, 300);
}
</script>

<style scoped>
.product {
  position: relative;
  width: 500px;
  /* 商品图像容器宽度 */
  height: 300px;
  /* 商品图像容器高度 */
}

.product-image {
  position: relative;
  width: 100%;
}

.product-image img {
  display: block;
  width: 100%;
  height: auto;
}

.magnifier {
  position: absolute;
  width: 200px;
  /* 放大镜尺寸 */
  height: 200px;
  /* 放大镜尺寸 */
  border: 2px solid #000;
  background-size: cover;
  pointer-events: none;
}
</style>