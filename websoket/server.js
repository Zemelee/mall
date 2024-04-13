const mysql = require('mysql');
const express = require('express');
const cors = require('cors');
const app = express();
app.use(cors());
const bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// 创建数据库连接
const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '784397',
    database: 'mall'
});

// 添加记录的接口
app.post('/addChat', (req, res) => {
    console.log(req.body);
    // 获取请求参数
    const ip = req.body.ip;
    const text = req.body.text;
    const location = req.body.location;
    const role = req.body.role;
    const time = req.body.time;
    const date = new Date(time);
    const datetime = date.getFullYear() + "-" +
        (date.getMonth() + 1) + "-" +
        date.getDate() + " " +
        date.getHours() + ":" +
        date.getMinutes() + ":" +
        date.getSeconds();
    // 构建SQL语句
    const sql = 'INSERT INTO chat(ip, text, role, time,location) VALUES (?, ?, ?, ?,?)';
    // 执行SQL语句
    db.query(sql, [ip, text, role, datetime,location], (err, result) => {
        if (err) {
            console.log(err);
            return res.status(500).send('数据库错误');
        }
        res.send('好');
    });

});

// 插入数据ua的 HTTP 接口------------------------------------------------------
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

    db.query(insertQuery, [uid, content, role, time], (error, results, fields) => {
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
    db.query(query, (error, results, fields) => {
        if (error) {
            console.error('Error querying data: ' + error.stack);
            res.status(500).send('Error querying data');
            return;
        }
        res.status(200).json(results);
    });
});


app.listen(3000, () => {
    console.log(`服务启动,端口:3000`);
});
