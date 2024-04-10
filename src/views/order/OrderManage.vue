<template>
    <div>
        ddgl
    </div>
</template>

<script>
import service from "@/request/index.js";
export default {
    data() {
        return {
            orderList: [],
        }
    },
    created() {
        service.post("/mall/history/get", { page: 1, size: 2 }).then(res => {
            this.orderList = res
            console.log(res)
        })
    }

}
</script>

<style></style>