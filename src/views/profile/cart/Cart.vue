<template>
  <div>
    <h3>MALL-CART</h3>
    <main>
      <form @submit.prevent="submitOrder">
        <div v-for="product in Cart.products" :key="product.id">
          <div v-for="attr in product.attributions" :key="attr.id">
            <div v-if="attr.num" class="product">
              <hr>
              <input type="checkbox"
                :value="{ productid: product.id, attrid: attr.id, inventory: attr.inventory, num: attr.num, price: attr.more + product.price }"
                v-model="selectedItems" />
              <img :src="product.picsrc" :alt="product.description" />
              <div class="product-info">
                <span>{{ product.name }}</span>-<span>{{ attr.attrval }}</span> -
                <span>{{ (attr.more + product.price).toFixed(2) }} * {{ attr.num }}
                  = {{ (attr.num * (product.price +
                    attr.more)).toFixed(2)
                  }}</span>
              </div>
              <el-button class="add" @click="add(attr.id)">+</el-button>
              <el-button class="sub" @click="sub(attr.id)">-</el-button>
              <el-button class="sub" @click="del(attr.id)">删除</el-button>
            </div>

          </div>
        </div>
        <div class="total">
          <span>总价</span>
          <span>{{ totalPrice.toFixed(2) }}</span>
        </div>
        <el-button type="submit">提交订单</el-button>
      </form>
    </main>
  </div>
</template>

<script setup>
import { useRoute, useRouter } from "vue-router";
import { useCartStore } from "../../../store/cart.js";
import { ref, computed } from "vue";
import { ElMessage } from "element-plus";
const route = useRoute();
const router = useRouter();

const Cart = useCartStore();
const cartList = JSON.parse(Cart.cartList);  // 商品、属性、数量
console.log("cartList:", cartList)
const ids = [...new Set(cartList.map((item) => item.productid))];
const products = Cart.products;  // 商品的详细信息

// 获取商品列表
Cart.fetchProducts(ids).then(() => {
  //获取到了购物车的详细数据数据products
  Cart.updateProducts(cartList)
  console.log("products:", products);
});



const add = (attrid) => {
  const item = selectedItems.value.find(item => item.attrid === attrid);
  if (item && item.num < item.inventory) {
    item.num += 1;
    Cart.addProduct(attrid);
  }
   else if (item && item.num >= item.inventory) {
  ElMessage.warning("库存不足");
  }
};
const sub = (attrid) => {
  const item = selectedItems.value.find(item => item.attrid === attrid)
  if (item && item.num > 1) {
    Cart.subtractProduct(attrid);
    item.num -= 1;
  } else if (item && item.num === 1) {
    ElMessage.warning("数量已减至1!");
  }
};
const del = (attrid) => {
  Cart.deleteProduct(attrid);
  selectedItems.value = selectedItems.value.filter(item => item.attrid !== attrid);
};




const selectedItems = ref([]);
const submitOrder = () => {
  console.log('Selected items:', selectedItems.value);

};

const totalPrice = computed(() => {
  let sum = 0;
  selectedItems.value.forEach(item => {
    // 假设item对象中有一个price属性代表单价
    sum += item.num * item.price; // 这里假设product是通过id从products数据中获取的商品对象，包含price属性
  });
  return sum;
});



</script>

<style lang="less" scoped>
img {
  width: 75px;
  height: 75px;
}
</style>