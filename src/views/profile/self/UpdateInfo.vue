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
                <el-input size="small" v-model="user.password" type="password" />
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
import { useRouter } from "vue-router";
import { ref, onMounted, defineEmits } from 'vue';
import { ElMessage } from "element-plus";
import service from '@/request';
import { md5 } from '@/utils/md5';
const router = useRouter();
const emit = defineEmits(['updateData'])
const { info } = defineProps(['info']);

let user = ref({
    user_id: 0,
    username: '',
    password: '',
    address: '',
    phone: ''
});

let originalUser = {};
onMounted(() => {
    user.value.user_id = localStorage.getItem('userid');
    user.value.username = info.username;
    user.value.address = info.address;
    // user.value.password = info.password;
    user.value.phone = info.phone;
    originalUser = { ...user.value };
});

const submitForm = async () => {
    const modified = Object.keys(originalUser).some(key => originalUser[key] !== user.value[key]);

    if (!modified) {
        ElMessage.warning("未修改数据！")
        return; // 不提交数据
    }

    try {
        //如果修改了密码，则hash  未修改则使用原密码的hash
        if (user.value.password == '') {
            user.value.password = info.password;
        } else {
            user.value.password = md5(user.value.password);
        }
        console.log(user.value)
        // return;
        const response = await service.post(`/user/update/${user.value.user_id}`, user.value);

        // 更新成功,传递给父组件
        if (response.data === "ok") {
            service.get(`/user/id=${localStorage.getItem("userid")}`)
                .then((res) => {
                    console.log(1,res.data)
                    emit('updateData', res.data);
                    ElMessage.success("修改成功")
                    //改了密码则跳转到登录页
                    if (user.value.password != info.password) {
                        router.push("/login")
                    }
                }).catch((e) => {
                    console.log(e)
                    ElMessage.error("修改失败1")
                })
        }
    } catch (error) {
        ElMessage.error("修改失败2")
    }
};
</script>
