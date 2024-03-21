import * as echarts from 'echarts';
export function Echart(ref,option) {
    // document.querySelector(".bar .chart")
    var myChart = echarts.init(ref)
    window.addEventListener('resize',function(){
        myChart.resize()
    })
    return myChart.setOption(option)
};