<template>
  <div class="echarts">
    <header>
      <h1>MALL商城后台管理系统</h1>

      <div class="showTime">{{ time1 }}</div>
    </header>
    <section class="mainBox">
      <div class="column">
        <div class="panel bar">
          <h2>GVM（万）</h2>
          <div class="chart" ref="chart1"></div>
          <div class="panel-footer"></div>
        </div>
        <div class="panel bar ">
          <h2>消费评分</h2>
          <div style="position:realtive;top:-20px" class="chart" ref="chart3"></div>
          <div class="panel-footer"></div>
        </div>
      </div>

      <div class="column">
        <div class="no">
          <div class="no-hd">
            <ul>
              <li>{{ userOnline }}</li>
              <li>{{userCount}}</li>
            </ul>
          </div>
          <div class="no-bd">
            <ul>
              <li>在线人数</li>
              <li>用户总量</li>
            </ul>
          </div>
        </div>
        <!-- 中间图 -->
        <div class="map">
          <div class="map1"></div>
          <div class="map2"></div>
          <div class="map3"></div>
          <div class="map4" ref="map4">
            <div class="map44" ref="map44"></div>
          </div>
        </div>
      </div>
      <div class="column">
        <div class="panel bar1">
          <h2>用户分布</h2>
          <div class="chart" ref="chart2"></div>
          <div class="panel-footer"></div>
        </div>
        <div class="panel bar">
          <h2>本周交易额（万）</h2>
          <div class="chart" ref="chart4"></div>
          <div class="panel-footer"></div>
        </div>
      </div>
    </section>
    <br />
  </div>
</template>

<script>
import service from '@/request/index.js';
import { Echart } from "./Echart.js";
export default {
  data() {
    return {
      userCount:0,
      t: 0,
      time1: 0,
      userOnline: 0
    };
  },
  async created() {
    

    const count = await service.get("/user/count")
    this.userCount = count
    this.t = setTimeout(this.time, 1000);
    this.userOnline = setInterval(() => {
      this.userOnline =  Math.floor(Math.random() * (count)) + 1;
    }, 5000);
  },
  mounted() {
    var option1 = {
      color: ["#2f89cf"],
      grid: {
        left: "0%",
        top: "10px",
        right: "-1%",
        bottom: "4%",
        containLabel: true,
      },
      tooltip: {
        trigger: "axis",
        axisPointer: {
          type: "shadow",
        },
      },
      xAxis: {
        type: "category",
        data: [
          "手机",
          "服饰",
          "食品",
          "问卷",
          "装饰",
        ],
      },
      yAxis: {
        type: "value",
        splitLine: {
          lineStyle: {
            color: "rgba(255,255,255,0.1)",
          },
        },
      },
      series: [
        {
          data: [120, 200, 150, 80, 70, 110],
          type: "bar",
          barWidth: "35%",
        },
      ],
    };
    Echart(this.$refs.chart1, option1);
    var option2 = {
      grid: {
        left: "0%",
        top: "0px",
        right: "-1%",
        bottom: "4%",
        containLabel: true,
      },
      xAxis: {
        show: false,
      },
      yAxis: [
        {
          type: "category",
          data: ["华西", "华北", "华南", "华东"],
          // y轴样式
          axisLine: {
            show: false,
          },
          axisTick: {
            show: false,
          },
          axisLabel: {
            color: "#fff",
          },
        },
      ],
      series: [
        {
          type: "bar",
          yAxisIndex: 0,
          data: [8, 10, 14, 22],
          // 第一组柱子圆角
          itemStyle: {
            barBorderRadius: 20,
          },
          //柱子间距
          barCategoryGap: 50,
          barWidth: 10,
          // 柱子文字
          label: {
            show: true,
            position: "inside",
            formatter: "{c}%", //自动解析为数据
          },
        },
      ],
    };
    Echart(this.$refs.chart2, option2);
    const option3 = {
      grid: {
        left: "0%",
        top: "5px",
        right: "-1%",
        bottom: "4px",
        containLabel: true,
      },
      series: [
        {
          type: "gauge",
          startAngle: 180,
          endAngle: 0,
          center: ["50%", "95%"],
          radius: "130%",
          min: 0,
          max: 1,
          splitNumber: 8,
          axisLine: {
            lineStyle: {
              width: 6,
              color: [
                [0.25, "#FF6E76"],
                [0.5, "#FDDD60"],
                [0.75, "#58D9F9"],
                [1, "#7CFFB2"],
              ],
            },
          },
          pointer: {
            icon: "path://M12.8,0.7l12,40.1H0.7L12.8,0.7z",
            length: "19%",
            width: 15,
            offsetCenter: [0, "-50%"],
            itemStyle: {
              color: "inherit",
            },
          },
          axisTick: {
            length: 10,
            lineStyle: {
              color: "#fff",
              width: 2,
            },
          },
          splitLine: {
            length: 13,
            lineStyle: {
              color: "inherit",
              width: 5,
            },
          },
          axisLabel: {
            color: "#fff",
            fontSize: 20,
            distance: -60,
            rotate: "tangential",
            formatter: function (value) {
              if (value === 0.875) {
                return "A";
              } else if (value === 0.625) {
                return "B";
              } else if (value === 0.375) {
                return "C";
              } else if (value === 0.125) {
                return "D";
              }
              return "";
            },
          },
          title: {
            offsetCenter: [0, "-10%"],
            fontSize: 10,
            color: "#fff"
          },
          detail: {
            fontSize: 30,
            offsetCenter: [0, "-35%"],
            valueAnimation: true,
            formatter: function (value) {
              return Math.round(value * 100) + "";
            },
            color: "inherit",
          },
          data: [
            {
              value: 0.7,
              name: "平均消费(k)",
            },
          ],
        },
      ],
    };
    Echart(this.$refs.chart3, option3);
    // 
    const option4 = {
      grid: {
        left: "0%",
        top: "5px",
        right: "-1%",
        bottom: "4%",
        containLabel: true,
      },
      xAxis: {
        type: "category",
        data: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
      },
      yAxis: {
        type: "value",
      },
      series: [
        {
          data: [120, 200, 150, 80, 70, 110, 130],
          type: "line",
          symbol: "triangle",
          symbolSize: 20,
          lineStyle: {
            color: "#5470C6",
            width: 4,
            type: "dashed",
          },
          itemStyle: {
            borderWidth: 3,
            borderColor: "#EE6666",
            color: "yellow",
          },
        },
      ],
    };
    Echart(this.$refs.chart4, option4);
    // Schema:
    // date,AQIindex,PM2.5,PM10,CO,NO2,SO2
    const dataBJ = [
      [55, 9, 56, 0.46, 18, 6, 1],
      [25, 11, 21, 0.65, 34, 9, 2],
      [56, 7, 63, 0.3, 14, 5, 3],
      [33, 7, 29, 0.33, 16, 6, 4],
      [42, 24, 44, 0.76, 40, 16, 5],
      [82, 58, 90, 1.77, 68, 33, 6],
      [74, 49, 77, 1.46, 48, 27, 7],
      [78, 55, 80, 1.29, 59, 29, 8],
      [267, 216, 280, 4.8, 108, 64, 9],
      [185, 127, 216, 2.52, 61, 27, 10],
      [39, 19, 38, 0.57, 31, 15, 11],
      [41, 11, 40, 0.43, 21, 7, 12],
      [64, 38, 74, 1.04, 46, 22, 13],
      [108, 79, 120, 1.7, 75, 41, 14],
      [108, 63, 116, 1.48, 44, 26, 15],
      [33, 6, 29, 0.34, 13, 5, 16],
      [94, 66, 110, 1.54, 62, 31, 17],
      [186, 142, 192, 3.88, 93, 79, 18],
      [57, 31, 54, 0.96, 32, 14, 19],
      [22, 8, 17, 0.48, 23, 10, 20],
      [39, 15, 36, 0.61, 29, 13, 21],
      [94, 69, 114, 2.08, 73, 39, 22],
      [99, 73, 110, 2.43, 76, 48, 23],
      [31, 12, 30, 0.5, 32, 16, 24],
      [42, 27, 43, 1, 53, 22, 25],
      [154, 117, 157, 3.05, 92, 58, 26],
      [234, 185, 230, 4.09, 123, 69, 27],
      [160, 120, 186, 2.77, 91, 50, 28],
      [134, 96, 165, 2.76, 83, 41, 29],
      [52, 24, 60, 1.03, 50, 21, 30],
      [46, 5, 49, 0.28, 10, 6, 31],
    ];
    const dataGZ = [
      [26, 37, 27, 1.163, 27, 13, 1],
      [85, 62, 71, 1.195, 60, 8, 2],
      [78, 38, 74, 1.363, 37, 7, 3],
      [21, 21, 36, 0.634, 40, 9, 4],
      [41, 42, 46, 0.915, 81, 13, 5],
      [56, 52, 69, 1.067, 92, 16, 6],
      [64, 30, 28, 0.924, 51, 2, 7],
      [55, 48, 74, 1.236, 75, 26, 8],
      [76, 85, 113, 1.237, 114, 27, 9],
      [91, 81, 104, 1.041, 56, 40, 10],
      [84, 39, 60, 0.964, 25, 11, 11],
      [64, 51, 101, 0.862, 58, 23, 12],
      [70, 69, 120, 1.198, 65, 36, 13],
      [77, 105, 178, 2.549, 64, 16, 14],
      [109, 68, 87, 0.996, 74, 29, 15],
      [73, 68, 97, 0.905, 51, 34, 16],
      [54, 27, 47, 0.592, 53, 12, 17],
      [51, 61, 97, 0.811, 65, 19, 18],
      [91, 71, 121, 1.374, 43, 18, 19],
      [73, 102, 182, 2.787, 44, 19, 20],
      [73, 50, 76, 0.717, 31, 20, 21],
      [84, 94, 140, 2.238, 68, 18, 22],
      [93, 77, 104, 1.165, 53, 7, 23],
      [99, 130, 227, 3.97, 55, 15, 24],
      [146, 84, 139, 1.094, 40, 17, 25],
      [113, 108, 137, 1.481, 48, 15, 26],
      [81, 48, 62, 1.619, 26, 3, 27],
      [56, 48, 68, 1.336, 37, 9, 28],
      [82, 92, 174, 3.29, 0, 13, 29],
      [106, 116, 188, 3.628, 101, 16, 30],
      [118, 50, 0, 1.383, 76, 11, 31],
    ];
    const dataSH = [
      [91, 45, 125, 0.82, 34, 23, 1],
      [65, 27, 78, 0.86, 45, 29, 2],
      [83, 60, 84, 1.09, 73, 27, 3],
      [109, 81, 121, 1.28, 68, 51, 4],
      [106, 77, 114, 1.07, 55, 51, 5],
      [109, 81, 121, 1.28, 68, 51, 6],
      [106, 77, 114, 1.07, 55, 51, 7],
      [89, 65, 78, 0.86, 51, 26, 8],
      [53, 33, 47, 0.64, 50, 17, 9],
      [80, 55, 80, 1.01, 75, 24, 10],
      [117, 81, 124, 1.03, 45, 24, 11],
      [99, 71, 142, 1.1, 62, 42, 12],
      [95, 69, 130, 1.28, 74, 50, 13],
      [116, 87, 131, 1.47, 84, 40, 14],
      [108, 80, 121, 1.3, 85, 37, 15],
      [134, 83, 167, 1.16, 57, 43, 16],
      [79, 43, 107, 1.05, 59, 37, 17],
      [71, 46, 89, 0.86, 64, 25, 18],
      [97, 71, 113, 1.17, 88, 31, 19],
      [84, 57, 91, 0.85, 55, 31, 20],
      [87, 63, 101, 0.9, 56, 41, 21],
      [104, 77, 119, 1.09, 73, 48, 22],
      [87, 62, 100, 1, 72, 28, 23],
      [168, 128, 172, 1.49, 97, 56, 24],
      [65, 45, 51, 0.74, 39, 17, 25],
      [39, 24, 38, 0.61, 47, 17, 26],
      [39, 24, 39, 0.59, 50, 19, 27],
      [93, 68, 96, 1.05, 79, 29, 28],
      [188, 143, 197, 1.66, 99, 51, 29],
      [174, 131, 174, 1.55, 108, 50, 30],
      [187, 143, 201, 1.39, 89, 53, 31],
    ];
    const lineStyle = {
      width: 1,
      opacity: 0.5,
    };
    const option7 = {
      legend: {
        bottom: 5,
        data: ["Beijing", "Shanghai", "Guangzhou"],
        itemGap: 20,
        textStyle: {
          color: "#fff",
          fontSize: 14,
        },
        selectedMode: "single",
      },
      radar: {
        indicator: [
          { name: "<100", max: 300 },
          { name: "<1k", max: 250 },
          { name: "<5k", max: 300 },
          { name: "<1w", max: 5 },
          { name: "<5w", max: 200 },
          { name: ">5w", max: 100 },
        ],
        shape: "circle",
        splitNumber: 5,
        axisName: {
          color: "rgb(238, 197, 102)",
        },
        splitLine: {
          lineStyle: {
            color: [
              "rgba(238, 197, 102, 0.1)",
              "rgba(238, 197, 102, 0.2)",
              "rgba(238, 197, 102, 0.4)",
              "rgba(238, 197, 102, 0.6)",
              "rgba(238, 197, 102, 0.8)",
              "rgba(238, 197, 102, 1)",
            ].reverse(),
          },
        },
        splitArea: {
          show: false,
        },
        axisLine: {
          lineStyle: {
            color: "rgba(238, 197, 102, 0.5)",
          },
        },
      },
      series: [
        {
          name: "Beijing",
          type: "radar",
          lineStyle: lineStyle,
          data: dataBJ,
          symbol: "none",
          itemStyle: {
            color: "#F9713C",
          },
          areaStyle: {
            opacity: 0.1,
          },
        },
        {
          name: "Shanghai",
          type: "radar",
          lineStyle: lineStyle,
          data: dataSH,
          symbol: "none",
          itemStyle: {
            color: "#B3E4A1",
          },
          areaStyle: {
            opacity: 0.05,
          },
        },
        {
          name: "Guangzhou",
          type: "radar",
          lineStyle: lineStyle,
          data: dataGZ,
          symbol: "none",
          itemStyle: {
            color: "rgb(238, 197, 102)",
          },
          areaStyle: {
            opacity: 0.05,
          },
        },
      ],
    };
    Echart(this.$refs.map4, option7);
  },
  methods: {
    time() {
      clearTimeout(this.t);
      var dt = new Date();
      var y = dt.getFullYear();
      var mt = dt.getMonth() + 1;
      var day = dt.getDate();
      var h = dt.getHours();
      var m = dt.getMinutes();
      var s = dt.getSeconds();
      this.t = setTimeout(this.time, 1000);
      this.time1 =
        "当前时间：" +
        y +
        "年" +
        mt +
        "月" +
        day +
        "日" +
        "-" +
        h +
        "时" +
        m +
        "分" +
        s +
        "秒";
    },
  },
};
</script>

<style lang="less" scoped>
* {
  li {
    list-style: none;
  }

  // 生命字体
  @font-face {
    font-family: electronicFont;
    src: url("../../../public/DS-DIGIT.TTF");
  }

  margin: 0;
  padding: 0;
  height: auto;
  box-sizing: border-box;

  .echarts {
    background: url("../../../public/bg.jpg") no-repeat center top;
    background-size: 100% 100%;

    header {
      position: relative;
      height: 1.25rem;
      line-height: 1.25rem;
      background: url("../../../public/head_bg.png");
      background-size: 100% 100%;

      h1 {
        font-size: 0.475rem;
        color: #fff;
        text-align: center;
        line-height: 1rem;
      }

      .showTime {
        position: absolute;
        right: 0.375rem;
        top: 0;
        line-height: 0.875rem;
        color: rgba(255, 255, 255, 0.7);
        font-size: 0.25rem;
      }
    }

    .mainBox {
      display: flex;
      min-width: 1024px;
      max-width: 1920px;
      margin: 0 auto;
      padding: 0.125rem 0.125rem 0;

      .column {
        flex: 3;

        .no {
          background-color: rgba(101, 132, 226, 0.1);
          padding: 0.1875rem;

          .no-hd {
            position: relative;

            &::before {
              position: absolute;
              top: 0;
              left: 0;
              content: "";
              height: 10px;
              width: 30px;
              border-top: 2px solid #02a6b5;
              border-left: 2px solid #02a6b5;
            }

            &::after {
              position: absolute;
              bottom: 0;
              right: 0;
              content: "";
              height: 10px;
              width: 30px;
              border-bottom: 2px solid #02a6b5;
              border-right: 2px solid #02a6b5;
            }

            ul {
              display: flex;

              li {
                position: relative;
                flex: 1;
                line-height: 1rem;
                font-size: 0.875rem;
                color: #ffeb7b;
                text-align: center;
                font-family: "electronicFont";
              }

              &::after {
                position: absolute;
                top: 25%;
                right: 50%;
                content: "";
                height: 50%;
                width: 1px;
                background: rgba(255, 255, 255, 0.2);
              }
            }
          }

          .no-bd {
            ul {
              display: flex;

              li {
                flex: 1;
                text-align: center;
                color: rgba(255, 255, 255, 0.7);
                font-size: 0.225rem;
                height: 0.5rem;
                line-height: 0.5rem;
                padding: 0.125rem;
              }
            }
          }
        }

        .map {
          position: relative;
          height: 8.375rem;

          .map1 {
            width: 6.457rem;
            height: 6.457rem;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background: url("../../../public/map.png");
            background-size: 100%;
            opacity: 0.3;
          }

          .map2 {
            width: 8.0367rem;
            height: 8.0367rem;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background: url("../../../public/lbx.png");
            background-size: 100%;
            animation: rotate1 15s linear infinite;
            background-size: 100% 100%;
          }

          .map3 {
            width: 7.075rem;
            height: 7.075rem;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background: url("../../../public/jt.png");
            background-size: 100%;
            animation: rotate2 15s linear infinite;
            opacity: 0.6;
            background-size: 100% 100%;
          }

          .map4 {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 8.375rem;

            .map44 {
              z-index: 1;
              width: 100%;
              height: 100%;
            }
          }

          @keyframes rotate1 {
            from {
              transform: translate(-50%, -50%) rotate(0deg);
            }

            to {
              transform: translate(-50%, -50%) rotate(360deg);
            }
          }

          @keyframes rotate2 {
            from {
              transform: translate(-50%, -50%) rotate(360deg);
            }

            to {
              transform: translate(-50%, -50%) rotate(0deg);
            }
          }
        }
      }

      .column:nth-child(2) {
        flex: 5;
        margin: 0 0.125rem 0.1875rem;
      }

      .panel {
        position: relative;
        height: 4.375rem;
        padding: 0 0.1875rem 0.5rem;
        border: 1px solid rgba(25, 186, 139, 0.17);
        margin-bottom: 0.1875rem;
        background: url("../../../public/line.png");

        .chart1 {
          width: 100%;
          height: 100%;
        }

        &::before {
          position: absolute;
          top: 0;
          left: 0;
          width: 10px;
          height: 10px;
          content: "";
          border-left: 2px solid #02a6b5;
          border-top: 2px solid #02a6b5;
        }

        &::after {
          position: absolute;
          top: 0;
          right: 0;
          width: 10px;
          height: 10px;
          content: "";
          border-right: 2px solid #02a6b5;
          border-top: 2px solid #02a6b5;
        }

        h2 {
          height: 0.6rem;
          color: #fff;
          line-height: 0.6rem;
          text-align: center;
          font-size: 0.25rem;
          font-weight: 400;
        }

        .chart {
          height: 3rem;
        }

        .panel-footer {
          position: absolute;
          bottom: 0;
          left: 0;
          width: 100%;

          &::before {
            position: absolute;
            bottom: 0;
            left: 0;
            width: 10px;
            height: 10px;
            content: "";
            border-left: 2px solid #02a6b5;
            border-bottom: 2px solid #02a6b5;
          }

          &::after {
            position: absolute;
            bottom: 0;
            right: 0;
            width: 10px;
            height: 10px;
            content: "";
            border-right: 2px solid #02a6b5;
            border-bottom: 2px solid #02a6b5;
          }
        }
      }
    }
  }
}
</style>