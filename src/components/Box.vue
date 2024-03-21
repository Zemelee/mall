<template>
    <div class="box-component">
        <slot></slot>
    </div>
</template>
  
<script setup lang='ts'>

</script> 
  
<style scoped>
.box-component {
    padding: 15px;
    margin: 20px;
    border: 1px solid #ddd;
    border-radius: 4px;
    box-shadow: 0 2px 3px rgba(0, 0, 0, .05);
}
</style>