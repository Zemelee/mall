<template>
    <div style="margin: 0 auto;width: 80%;">
        <h2>修改信息</h2>
        <el-form :model="user" label-width="auto" style="max-width: 600px">
            <el-form-item label="ID">
                <el-input size="small" disabled v-model="user.user_id" />
            </el-form-item>
            <el-form-item label="用户名">
                <el-input size="small" v-model="user.username" />
            </el-form-item>
            <el-form-item label="密码">
                <el-input size="small" v-model="user.password" />
            </el-form-item>
            <el-form-item label="地址">
                <el-input size="small" v-model="user.address" />
            </el-form-item>
            <el-form-item label="电话">
                <el-input size="small" v-model="user.phone" />
            </el-form-item>
            <el-form-item style="margin: 0 auto; display: block; width: 100%">
                <el-button type="primary" @click="submitForm">提交</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script setup>
import { ref, onMounted  } from 'vue';
import service from '@/request';
const { info } = defineProps(['info']);
const user = ref({
    user_id: localStorage.getItem('userid'),
    username: '',
    password: '',
    address: '',
    phone: '',
})
const submitForm = async () => {
    try {
        const response = await service.post(`/user/update/${user.value.user_id}`, user.value);
        // 更新成功,传递给父组件
        if (response.data === "ok") {
            service.get(`/user/id=${localStorage.getItem("userid")}`)
            .then((res) => {
                
                // emit('update-info', res.data);
            })
        }
    } catch (error) {
        console.log('Failed to update user information');
    }
};
onMounted(() => {
    // 如果有需要，可以在这里加载用户初始数据
});

</script>