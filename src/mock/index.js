import Mock from 'mockjs'
const data = Mock.mock({
    // 'name|min-max':"text"
    'string|1-4': "你好"
})

const apidata = Mock.mock('/api/user', 'get', () => {
    return {
        'status|3': 200,
        msg:"成功"
    }
})
console.log(data);