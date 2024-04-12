const mysql = require('mysql');
const express = require('express');
const cors = require('cors');
const app = express();
app.use(cors());
const bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

const port = 3110; // 定义服务器端口

// 创建数据库连接
const connection = mysql.createConnection({
    host: 'localhost', // 数据库地址
    user: 'root', // 数据库用户名
    password: '784397', // 数据库密码
    database: 'mall' // 数据库名称
});

// 连接到数据库
connection.connect((err) => {
    if (err) {
        console.error('Error connecting to database: ' + err.stack);
        return;
    }
    console.log('Connected to database as id ' + connection.threadId);
});

// 插入数据的 HTTP 接口
app.post('/chatua/add', (req, res) => {
    console.log(req.body)
    const uid = req.body.uid;
    const content = req.body.content;
    const role = req.body.role;
    const date = new Date(req.body.time);
    const time = date.getFullYear() + "-" +
        (date.getMonth() + 1) + "-" +
        date.getDate() + " " +
        date.getHours() + ":" +
        date.getMinutes() + ":" +
        date.getSeconds();
    const insertQuery = 'INSERT INTO chatua (uid, content, role, time) VALUES (?, ?, ?, ?)';

    connection.query(insertQuery, [uid, content, role, time], (error, results, fields) => {
        if (error) {
            console.error('Error inserting data: ' + error.stack);
            res.status(500).send('Error inserting data');
            return;
        }
        console.log('Inserted ' + results.affectedRows + ' row(s)');
        res.status(200).send('ok');
    });
});

// 查询数据的 HTTP 接口
app.get('/chatua/query', (req, res) => {
    const query = `SELECT * FROM chatua where uid = ${req.query.uid}`;
    connection.query(query, (error, results, fields) => {
        if (error) {
            console.error('Error querying data: ' + error.stack);
            res.status(500).send('Error querying data');
            return;
        }
        res.status(200).json(results);
    });
});

// 监听指定端口
app.listen(port, () => {
    console.log(`Server is listening at http://localhost:${port}`);
});
