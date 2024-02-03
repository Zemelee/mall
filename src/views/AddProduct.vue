<template>
    <div>
      <h2>Add Product</h2>
  
      <form @submit.prevent="submitForm">
        <label for="name">Name:</label>
        <input v-model="product.name" type="text" id="name" required />
  
        <label for="price">Price:</label>
        <input v-model="product.price" type="number" id="price" required />
  
        <label for="category">Category:</label>
        <input v-model="product.category" type="number" id="category" required />
  
        <label for="picsrc">picsrc:</label>
        <input v-model="product.picsrc" type="number" id="picsrc" required />
  
        <label for="description">Description:</label>
        <textarea
          v-model="product.description"
          id="description"
          required
        ></textarea>
  
        <h3>Specifications</h3>
        <div v-for="(attribution, index) in product.attributions" :key="index">
          <label v-if="index === 0" for="attrval">Attribute Value:</label>
          <input
            v-model="attribution.attrval"
            type="text"
            id="attrval"
            required
          />
  
          <label for="inventory">Inventory:</label>
          <input
            v-model="attribution.inventory"
            type="number"
            id="inventory"
            required
          />
  
          <label for="more">more:</label>
          <input v-model="attribution.more" type="number" id="more" required />
        </div>
  
        <button type="button" @click="addAttribution">Add Specification</button>
        <button type="submit">Submit</button>
      </form>
    </div>
    <el-button @click="test">test</el-button>
  </template>
  
  <script>
  import service from "@/request/index.js";
  import axios from "axios";
  
  export default {
    data() {
      return {
        product: {
          name: "",
          price: 0,
          category: 0,
          description: "",
          picsrc: "",
          attributions: [{ attrval: "", inventory: 0, more: 0 }],
        },
      };
    },
    methods: {
      addAttribution() {
        this.product.attributions.push({ attrval: "", inventory: 0, more: 0 });
      },
  
      submitForm() {
        service
          .post("/mall/product/add", this.product)
          .then((response) => {
            console.log(response.data);
            // handle success, e.g., redirect to another page
          })
          .catch((error) => {
            console.error(error);
            // handle error
          });
      },
  
      test() {
        axios
          .post("http://localhost:8081/user/recharge", {
            id: 1,
            amount: 100,
          })
          .then((e) => {
            console.log(res);
          })
          .catch((e) => {
            console.log(e);
          });
      },
    },
  };
  </script>
  
  