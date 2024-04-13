// 引入WebSocket模块
const WebSocket = require('ws');

// 创建WebSocket服务器
const wss = new WebSocket.Server({ port: 3100 });

// 存储连接的系统用户和系统管理员
const users = [];
const admins = [];
wss.on('connection', function connection(ws) {

  // 监听连接建立事件，根据身份将连接添加到对应的用户或管理员列表中
  ws.on('open', function open() {
    console.log('ws');
    const { role } = ws;
    console.log(`${role}连接建立`);
    if (role === 'user') {
      users.push(ws);
    } else if (role === 'admin') {
      admins.push(ws);
    }
  });
  // 监听消息
  ws.on('message', function incoming(message) {
    // 解析消息中的类型和内容
    const { role, content, time } = JSON.parse(message);
    console.log(`收到消息：${message}`);
    if (role == 'user') {
      users.push(ws);
    } else if (role == 'admin') {
      admins.push(ws);
    }
    console.log("users:", users.length, "admins:", admins.length)
    // 判断消息类型并进行转发
    switch (role) {
      case 'user':
        // 将用户消息转发给管理员
        admins.forEach(admin => admin.send(JSON.stringify({ role, content, time })));
        break;
      case 'admin':
        // 将管理员消息转发给用户
        users.forEach(user => user.send(JSON.stringify({ role, content, time })));
        break;
      default:
        console.log('未知消息类型');
    }
  });

  // 监听关闭连接事件
  ws.on('close', function close() {
    // 从存储的用户或管理员列表中移除关闭的连接
    const index = users.indexOf(ws);
    if (index !== -1) {
      users.splice(index, 1);
    } else {
      const index = admins.indexOf(ws);
      if (index !== -1) {
        admins.splice(index, 1);
      }
    }
  });


});
