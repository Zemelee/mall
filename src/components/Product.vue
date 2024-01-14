<!-- Product.vue -->
<template>
  <el-card shadow="hover" :body-style="{ padding: '0px' }">
    <div class="product">
      <div class="goods">
        <img :src="product.picsrc" alt="Product Image" class="goods-img" />
      </div>
      <div class="product-info">
        <h3 class="product-title">{{ product.name }}</h3>
        <p class="product-decription">
          {{ product.description }}

          <!-- <span class="product-price">已售：{{ product.sales }}</span> -->
        </p>
        <p class="product-price">{{ formatPrice(product.price) }}</p>
        <button @click="addToCart" class="add-to-cart-btn">Add to Cart</button>
      </div>
    </div>
  </el-card>
</template>

<script setup>

defineProps({
  product: {
    type: Object,
    required: true,
  },
})

const addToCart = () => {
  // 可以通过 $emit 触发事件，父组件监听该事件并处理添加到购物车的逻辑
  this.$emit("add-to-cart", this.product);
}
const formatPrice = (price) => {
  // 可以根据需要进行价格格式化，比如添加货币符号等
  return `$${price.toFixed(2)}`;
}

</script>




<style lang="less" scoped>
.product {
  border: 1px solid #ddd;
  padding: 5px;
  margin: 5px;
  text-align: center;

  .product-info {
    margin-top: 10px;

    .product-title {
      font-size: 16px;
      margin-bottom: 5px;
    }

    .product-price {
      font-weight: bold;
      font-size: 10px;
      color: #ff6347;
      /* Tomato color, or any other color you prefer for price */
    }
  }
}

.add-to-cart-btn {
  background-color: #4caf50;
  /* Green color, or any other color you prefer for the button */
  color: #fff;
  padding: 8px 12px;
  border: none;
  cursor: pointer;
  font-size: 14px;
}

.goods {
  width: 200px;
  height: 100px;

  .goods-img {
    width: 50%;
    /* 或者使用适当的宽度值 */
    height: auto;
    /* 或者使用适当的高度值 */
  }
}
</style>



