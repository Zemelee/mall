// server.js
const express = require('express');
const cors = require('cors');
const app = express();
app.use(cors());
app.get('/api/users', (req, res) => {
    // 延迟 4 秒后返回
    setTimeout(() => {
        const users = [{ id: 1, name: 'asynchronous' }, { id: 2, name: 'await' }]
        res.json(users)
    }, 4000)
})
app.listen(3000, () => {
    console.log('API server started on port 3000')
})