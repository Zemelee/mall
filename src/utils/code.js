// 生成数字+字母的验证码
export function generateCode() {
    let code = ''
    const codeLength = 4 // 验证码长度
    const random = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z')

    for (let i = 0; i < codeLength; i++) {
        let index = Math.floor(Math.random() * 36)
        code += random[index]
    }
    return code
}

// 生成验证码图片
export function generateCodeImg(code) {
    const canvas = document.createElement('canvas')
    const ctx = canvas.getContext('2d')
    canvas.width = 100
    canvas.height = 40
    ctx.textBaseline = 'middle'
    ctx.font = 'bold 20px Arial'
    ctx.fillStyle = randomColor(180, 240)
    ctx.fillRect(0, 0, canvas.width, canvas.height)
    ctx.fillStyle = randomColor(50, 180)
    ctx.fillText(code, 0, canvas.height / 2)
    return canvas.toDataURL('image/png')
}

// 随机颜色
function randomColor(min, max) {
    const r = Math.floor(Math.random() * (max - min + 1) + min)
    const g = Math.floor(Math.random() * (max - min + 1) + min)
    const b = Math.floor(Math.random() * (max - min + 1) + min)
    return 'rgb(' + r + ',' + g + ',' + b + ')'
}