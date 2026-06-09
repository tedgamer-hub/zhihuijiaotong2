// 更新时间显示
function updateTime() {
    const now = new Date();
    const dateStr = now.toLocaleDateString('zh-CN', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    });
    const timeStr = now.toLocaleTimeString('zh-CN', {
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit',
        hour12: false
    });

    document.getElementById('currentDate').textContent = dateStr;
    document.getElementById('currentTime').textContent = timeStr;
    document.querySelector('.update-time').textContent = `数据更新时间: ${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}-${String(now.getDate()).padStart(2, '0')} ${timeStr}`;
}

// 初始化时间并设置定时更新
updateTime();
setInterval(updateTime, 1000);

// 初始化图表
function initCharts() {
    // 交通流量分析图表
    const trafficFlowChart = echarts.init(document.getElementById('trafficFlowChart'));
    const trafficFlowOption = {
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            right: 10,
            top: 'center',
            textStyle: {
                color: '#eaeaea'
            }
        },
        series: [
            {
                name: '交通流量',
                type: 'pie',
                radius: ['40%', '70%'],
                avoidLabelOverlap: false,
                itemStyle: {
                    borderRadius: 10,
                    borderColor: '#0a1929',
                    borderWidth: 2
                },
                label: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    label: {
                        show: true,
                        fontSize: '18',
                        fontWeight: 'bold'
                    }
                },
                labelLine: {
                    show: false
                },
                data: [
                    { value: 1048, name: '畅通', itemStyle: { color: '#52c41a' } },
                    { value: 735, name: '轻度拥堵', itemStyle: { color: '#fadb14' } },
                    { value: 580, name: '中度拥堵', itemStyle: { color: '#fa8c16' } },
                    { value: 484, name: '严重拥堵', itemStyle: { color: '#f5222d' } },
                    { value: 300, name: '封闭', itemStyle: { color: '#722ed1' } }
                ]
            }
        ]
    };
    trafficFlowChart.setOption(trafficFlowOption);

    // 车辆类型统计图表
    const vehicleTypeChart = echarts.init(document.getElementById('vehicleTypeChart'));
    const vehicleTypeOption = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: ['小型车', '中型车', '大型车', '公交车', '出租车', '货车'],
                axisTick: {
                    alignWithLabel: true
                },
                axisLine: {
                    lineStyle: {
                        color: '#aaa'
                    }
                },
                axisLabel: {
                    color: '#eaeaea'
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                axisLine: {
                    lineStyle: {
                        color: '#aaa'
                    }
                },
                axisLabel: {
                    color: '#eaeaea'
                },
                splitLine: {
                    lineStyle: {
                        color: 'rgba(255, 255, 255, 0.1)'
                    }
                }
            }
        ],
        series: [
            {
                name: '数量',
                type: 'bar',
                barWidth: '60%',
                data: [
                    { value: 10200, itemStyle: { color: '#4cc9f0' } },
                    { value: 5700, itemStyle: { color: '#4895ef' } },
                    { value: 3400, itemStyle: { color: '#4361ee' } },
                    { value: 2800, itemStyle: { color: '#3f37c9' } },
                    { value: 4500, itemStyle: { color: '#3a0ca3' } },
                    { value: 3900, itemStyle: { color: '#480ca8' } }
                ]
            }
        ]
    };
    vehicleTypeChart.setOption(vehicleTypeOption);

    // 24小时交通趋势图表
    const trafficTrendChart = echarts.init(document.getElementById('trafficTrendChart'));
    const trafficTrendOption = {
        tooltip: {
            trigger: 'axis'
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['00:00', '02:00', '04:00', '06:00', '08:00', '10:00', '12:00', '14:00', '16:00', '18:00', '20:00', '22:00'],
            axisLine: {
                lineStyle: {
                    color: '#aaa'
                }
            },
            axisLabel: {
                color: '#eaeaea'
            }
        },
        yAxis: {
            type: 'value',
            axisLine: {
                lineStyle: {
                    color: '#aaa'
                }
            },
            axisLabel: {
                color: '#eaeaea'
            },
            splitLine: {
                lineStyle: {
                    color: 'rgba(255, 255, 255, 0.1)'
                }
            }
        },
        series: [
            {
                name: '交通指数',
                type: 'line',
                stack: '总量',
                smooth: true,
                lineStyle: {
                    width: 3,
                    color: '#4cc9f0'
                },
                areaStyle: {
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                        {
                            offset: 0,
                            color: 'rgba(76, 201, 240, 0.5)'
                        },
                        {
                            offset: 1,
                            color: 'rgba(76, 201, 240, 0)'
                        }
                    ])
                },
                emphasis: {
                    focus: 'series'
                },
                data: [2.1, 1.8, 1.5, 3.2, 8.5, 7.2, 5.8, 6.1, 6.8, 8.9, 6.2, 4.5]
            }
        ]
    };
    trafficTrendChart.setOption(trafficTrendOption);

    // 信号灯运行状态图表
    const trafficLightChart = echarts.init(document.getElementById('trafficLightChart'));
    const trafficLightOption = {
        tooltip: {
            trigger: 'item'
        },
        legend: {
            bottom: '5%',
            left: 'center',
            textStyle: {
                color: '#eaeaea'
            }
        },
        series: [
            {
                name: '信号灯状态',
                type: 'pie',
                radius: ['40%', '70%'],
                avoidLabelOverlap: false,
                itemStyle: {
                    borderRadius: 10,
                    borderColor: '#0a1929',
                    borderWidth: 2
                },
                label: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    label: {
                        show: true,
                        fontSize: '18',
                        fontWeight: 'bold'
                    }
                },
                labelLine: {
                    show: false
                },
                data: [
                    { value: 1048, name: '正常', itemStyle: { color: '#52c41a' } },
                    { value: 235, name: '故障', itemStyle: { color: '#f5222d' } },
                    { value: 580, name: '维护中', itemStyle: { color: '#fadb14' } }
                ]
            }
        ]
    };
    trafficLightChart.setOption(trafficLightOption);

    // 窗口大小改变时，重置图表大小
    window.addEventListener('resize', function() {
        trafficFlowChart.resize();
        vehicleTypeChart.resize();
        trafficTrendChart.resize();
        trafficLightChart.resize();
    });
}

// 初始化地图
function initMap() {
    const mapContainer = document.getElementById('trafficMap');

    // 创建地图标记点
    const markers = [
        { x: 30, y: 40, type: 'accident' },
        { x: 50, y: 60, type: 'construction' },
        { x: 70, y: 30, type: 'congestion' },
        { x: 40, y: 70, type: 'control' },
        { x: 60, y: 50, type: 'breakdown' }
    ];

    // 添加标记点
    markers.forEach(marker => {
        const markerElement = document.createElement('div');
        markerElement.className = `map-marker ${marker.type}`;
        markerElement.style.position = 'absolute';
        markerElement.style.left = `${marker.x}%`;
        markerElement.style.top = `${marker.y}%`;
        markerElement.style.width = '20px';
        markerElement.style.height = '20px';
        markerElement.style.borderRadius = '50%';
        markerElement.style.transform = 'translate(-50%, -50%)';

        // 根据类型设置颜色
        let color;
        switch(marker.type) {
            case 'accident': color = '#f5222d'; break;
            case 'construction': color = '#faad14'; break;
            case 'congestion': color = '#fa8c16'; break;
            case 'control': color = '#722ed1'; break;
            case 'breakdown': color = '#eb2f96'; break;
            default: color = '#4cc9f0';
        }

        markerElement.style.backgroundColor = color;
        markerElement.style.boxShadow = `0 0 10px ${color}`;

        // 添加脉冲效果
        const pulseElement = document.createElement('div');
        pulseElement.className = 'pulse';
        pulseElement.style.position = 'absolute';
        pulseElement.style.width = '100%';
        pulseElement.style.height = '100%';
        pulseElement.style.borderRadius = '50%';
        pulseElement.style.backgroundColor = color;
        pulseElement.style.opacity = '0.6';
        pulseElement.style.animation = 'pulse 1.5s infinite';

        markerElement.appendChild(pulseElement);
        mapContainer.appendChild(markerElement);
    });

    // 添加脉冲动画
    const style = document.createElement('style');
    style.textContent = `
        @keyframes pulse {
            0% {
                transform: scale(1);
                opacity: 0.6;
            }
            70% {
                transform: scale(2);
                opacity: 0;
            }
            100% {
                transform: scale(1);
                opacity: 0;
            }
        }
        
        .map-marker {
            cursor: pointer;
            z-index: 10;
        }
    `;
    document.head.appendChild(style);
}

// 模拟数据更新
function simulateDataUpdates() {
    // 随机更新数据卡片
    setInterval(() => {
        // 更新事故数量
        const accidentCount = document.getElementById('accidentCount');
        let count = parseInt(accidentCount.textContent);
        if (Math.random() > 0.7) {
            count += 1;
            accidentCount.textContent = count;

            // 添加新事件
            addNewIncident();
        }

        // 更新拥堵路段数量
        const congestionCount = document.getElementById('congestionCount');
        let congestion = parseInt(congestionCount.textContent);
        congestion += Math.floor(Math.random() * 3) - 1;
        congestion = Math.max(10, Math.min(30, congestion));
        congestionCount.textContent = congestion;

        // 更新平均车速
        const avgSpeed = document.getElementById('avgSpeed');
        let speed = parseInt(avgSpeed.textContent);
        speed += Math.floor(Math.random() * 5) - 2;
        speed = Math.max(20, Math.min(50, speed));
        avgSpeed.innerHTML = `${speed}<span class="unit">km/h</span>`;

        // 更新交通指数
        const trafficIndex = document.getElementById('trafficIndex');
        let index = parseFloat(trafficIndex.textContent);
        index += (Math.random() * 0.4 - 0.2).toFixed(1);
        index = Math.max(3.0, Math.min(9.0, index)).toFixed(1);
        trafficIndex.textContent = index;
    }, 5000);
}

// 添加新的交通事件
function addNewIncident() {
    const incidentList = document.querySelector('.incident-list');
    const now = new Date();
    const timeString = now.toLocaleTimeString('zh-CN', {
        hour: '2-digit',
        minute: '2-digit',
        hour12: false
    });

    const incidentTypes = ['交通事故', '道路施工', '交通管制', '车辆故障', '交通拥堵'];
    const locations = [
        '人民路与解放大道交叉口',
        '中山北路隧道入口',
        '南京西路商圈',
        '延安高架路出口',
        '浦东大道近张杨路段',
        '世纪大道金融区',
        '虹桥机场附近'
    ];
    const descriptions = [
        '两车相撞，造成轻微拥堵',
        '道路维修，车辆绕行',
        '信号灯故障，交警正在指挥',
        '一辆公交车抛锚，占用一条车道',
        '早高峰拥堵，车辆缓慢行驶',
        '大型活动，实施临时交通管制',
        '货车侧翻，造成严重拥堵'
    ];

    const randomType = incidentTypes[Math.floor(Math.random() * incidentTypes.length)];
    const randomLocation = locations[Math.floor(Math.random() * locations.length)];
    const randomDesc = descriptions[Math.floor(Math.random() * descriptions.length)];
    const isUrgent = Math.random() > 0.7;

    const newIncident = document.createElement('div');
    newIncident.className = `incident-item${isUrgent ? ' urgent' : ''}`;
    newIncident.style.animation = 'fadeIn 0.5s';
    newIncident.innerHTML = `
        <div class="incident-time">${timeString}</div>
        <div class="incident-info">
            <div class="incident-type">${randomType}</div>
            <div class="incident-location">${randomLocation}</div>
            <div class="incident-desc">${randomDesc}</div>
        </div>
    `;

    // 添加到列表顶部
    incidentList.insertBefore(newIncident, incidentList.firstChild);

    // 如果列表项过多，移除最后一项
    if (incidentList.children.length > 6) {
        incidentList.removeChild(incidentList.lastChild);
    }

    // 添加淡入动画样式
    if (!document.querySelector('style#animation-style')) {
        const style = document.createElement('style');
        style.id = 'animation-style';
        style.textContent = `
            @keyframes fadeIn {
                from { opacity: 0; transform: translateY(-10px); }
                to { opacity: 1; transform: translateY(0); }
            }
        `;
        document.head.appendChild(style);
    }
}

// 页面加载完成后初始化
window.addEventListener('load', function() {
    initCharts();
    initMap();
    simulateDataUpdates();
});
