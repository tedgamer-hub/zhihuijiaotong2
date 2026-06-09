<template>
  <div class="map-container">
    <div ref="mapContainer" class="amap-container"></div>
  </div>
</template>

<script setup>
import {ref, onMounted, onBeforeUnmount, h, render, getCurrentInstance} from 'vue';
import CameraMarker from './CameraMarker/index.vue'
// 定义响应式引用
const mapContainer = ref(null);
const map = ref(null);
// 事故标记列表
const accidentMarkers = ref([]);
// 获取当前Vue实例
const instance = getCurrentInstance();
// 获取事件总线
const $bus = instance?.appContext.config.globalProperties.$bus;

onMounted(() => {
  // 初始化地图
  map.value = new AMap.Map(mapContainer.value, { 
    zoom: 15, 
    viewMode: '3D',
    center: [123.724768, 41.847804], // 沈阳工学院坐标
    mapStyle: 'amap://styles/darkblue', // 使用官方提供的"幻影黑"样式
  });

  // 监听事故详情弹窗事件
  if ($bus) {
    console.log('地图组件已注册事件监听:', 'showAccidentOnMap');
    $bus.on('showAccidentOnMap', handleAccidentOnMap);
  }

  // 确保地图完全加载后再添加元素
    AMap.event.addListenerOnce(map.value, 'complete', () => {
      // 添加智慧交通系统的其他元素
      addTrafficElements();
    });
});



// 添加智慧交通系统的其他元素
const addTrafficElements = () => {
  try {
    // 1. 添加实时监控摄像头点位
    addCameras();
  } catch (error) {
    console.error('添加交通元素时出错:', error);
  }
};



// 添加实时监控摄像头点位
const addCameras = () => {
  try {
    // 摄像头位置数据（沈阳工学院坐标：123.724768, 41.847804）
    const cameras = [
      {position: [123.724768, 41.847804], id: 'cam001', status: 0},
      {position: [123.724768, 41.847804], id: 'cam002', status: 1},
      {position: [123.724768, 41.847804], id: 'cam003', status: 1}
    ];

    cameras.forEach(camera => {
      // 直接创建DOM元素并使用render函数渲染Vue组件
      const container = document.createElement('div');

      // 使用render函数渲染组件
      const vnode = h(CameraMarker, {
        cameraId: camera.id,
        status: camera.status,
        onClick: (id) => {
          console.log(`打开摄像头 ${id} 的视频流`);
          // 这里可以添加打开视频流的逻辑
        }
      });

      render(vnode, container);

      // 创建自定义覆盖物
      const customMarker = new AMap.Marker({
        map: map.value,
        position: new AMap.LngLat(camera.position[0], camera.position[1]),
        content: container,
        offset: new AMap.Pixel(-16, -16), // 偏移量，使标记点居中
        title: `监控摄像头 ${camera.id}`
      });

      // 可以添加点击事件
      customMarker.on('click', () => {
        console.log(`点击了摄像头 ${camera.id}`);
      });
    });

    console.log('成功添加摄像头自定义组件');
  } catch (error) {
    console.error('添加摄像头自定义组件时出错:', error);
  }
};



// 处理地图上的事故标记
const handleAccidentOnMap = (accident) => {
  console.log('=== 地图组件接收到事故标记事件 ===');
  console.log('数据类型:', Array.isArray(accident) ? '数组' : '单个对象');
  console.log('数据内容:', accident);
  console.log('当前已有标记数量:', accidentMarkers.value.length);
  
  // 检查地图实例是否存在
  if (!map.value) {
    console.error('地图实例不存在，无法添加标记');
    return;
  }
  
  // 如果是事故数组，则遍历添加所有事故标记
  if (Array.isArray(accident)) {
    console.log('=== 处理事故数组 ===');
    console.log('事故数组长度:', accident.length);
    console.log('事故数组ID列表:', accident.map(a => a.id));
    
    // 先清除所有旧标记
    console.log('=== 清除所有旧标记 ===');
    console.log('清除前标记数量:', accidentMarkers.value.length);
    accidentMarkers.value.forEach(marker => {
      try {
        marker.setMap(null);
      } catch (e) {
        console.error('清除标记失败:', e);
      }
    });
    accidentMarkers.value = [];
    console.log('清除后标记数量:', accidentMarkers.value.length);
    
    // 遍历添加所有事故标记
    console.log('=== 开始添加新标记 ===');
    let addedCount = 0;
    accident.forEach((acc, index) => {
      console.log(`\n添加第 ${index + 1}/${accident.length} 个事故标记`);
      console.log(`处理事故ID: ${acc.id}`);
      const wasAdded = addAccidentMarker(acc);
      if (wasAdded) {
        addedCount++;
      }
      console.log(`添加结果: ${wasAdded ? '成功' : '失败'}`);
      console.log(`当前已添加: ${addedCount} 个标记`);
    });
    console.log('=== 添加标记完成 ===');
    console.log('原始事故数组长度:', accident.length);
    console.log('成功添加标记数量:', addedCount);
    console.log('最终标记数量:', accidentMarkers.value.length);
    return;
  }
  
  // 单个事故，直接添加标记
  console.log('=== 处理单个事故对象 ===');
  console.log('事故ID:', accident?.id);
  const wasAdded = addAccidentMarker(accident);
  console.log('单个标记添加结果:', wasAdded ? '成功' : '失败');
  console.log('单个标记添加后数量:', accidentMarkers.value.length);
};

// 添加单个事故标记
const addAccidentMarker = (accident) => {
  // 验证事故对象
  if (!accident) {
    console.error('无效的事故对象:', accident);
    return false;
  }
  
  // 根据事故状态获取对应的渐变颜色
  const getSeverityColor = (status) => {
    switch (status) {
      case '严重':
        return {
          mainColor: 'rgba(255, 87, 34, 1)',
          bgColor: '#ffd7cc'
        };
      case '中等':
        return {
          mainColor: 'rgba(255, 193, 7, 1)',
          bgColor: '#fff3e0'
        };
      case '轻微':
        return {
          mainColor: 'rgba(79, 138, 254, 1)',
          bgColor: '#e0f2fe'
        };
      default:
        return {
          mainColor: 'rgba(255, 181, 109, 1)',
          bgColor: '#ffe9d5'
        };
    }
  };
  
  const severityColor = getSeverityColor(accident.accidentDescriptionState);
  
  console.log(`=== 开始添加事故标记 ===`);
  console.log(`处理事故ID: ${accident.id || '未知ID'}`);
  console.log('事故完整数据:', accident);
  
  // 提取并验证坐标，尝试所有可能的字段名
  let lng, lat;
  let lngFieldUsed = null;
  let latFieldUsed = null;
  
  // 首先尝试直接从对象中获取各种可能的坐标字段
  const possibleLngFields = ['lng', 'accidentX', 'accident_x', 'coordinateX', 'longitude', 'accident_x_coordinate', 'x'];
  const possibleLatFields = ['lat', 'accidentY', 'accident_y', 'coordinateY', 'latitude', 'accident_y_coordinate', 'y'];
  
  // 查找有效的经度字段
  console.log('=== 查找经度字段 ===');
  for (const field of possibleLngFields) {
    const value = accident[field];
    console.log(`尝试字段 ${field}: 值=${value}, 类型=${typeof value}`);
    if (value !== undefined && value !== null) {
      lng = parseFloat(value);
      if (!isNaN(lng)) {
        lngFieldUsed = field;
        console.log(`✅ 使用${field}字段作为经度: ${lng}`);
        break;
      } else {
        console.log(`❌ ${field}字段值 ${value} 转换为数字失败`);
      }
    }
  }
  
  // 查找有效的纬度字段
  console.log('=== 查找纬度字段 ===');
  for (const field of possibleLatFields) {
    const value = accident[field];
    console.log(`尝试字段 ${field}: 值=${value}, 类型=${typeof value}`);
    if (value !== undefined && value !== null) {
      lat = parseFloat(value);
      if (!isNaN(lat)) {
        latFieldUsed = field;
        console.log(`✅ 使用${field}字段作为纬度: ${lat}`);
        break;
      } else {
        console.log(`❌ ${field}字段值 ${value} 转换为数字失败`);
      }
    }
  }
  
  console.log('=== 坐标提取结果 ===');
  console.log(`经度: ${lng} (来自${lngFieldUsed || '未找到'})`);
  console.log(`纬度: ${lat} (来自${latFieldUsed || '未找到'})`);
  console.log('转换后坐标:', { lng, lat });
  
  // 使用更宽松的坐标验证条件
  if (isNaN(lng) || isNaN(lat)) {
    console.error('❌ 无效的坐标，跳过添加:', { lng, lat, accidentId: accident.id });
    return false;
  }
  
  // 移除重复标记检查，允许在同一位置显示多个事故
  // 这是为了调试，确保所有事故都能显示出来
  
  // 创建自定义标记
  console.log('=== 创建标记内容 ===');
  const markerContent = document.createElement('div');
  markerContent.innerHTML = `
    <div style="
      font-family: 'icomoon' !important;
      font-size: 24px;
      background: linear-gradient(to bottom, ${severityColor.mainColor}, ${severityColor.bgColor});
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      text-align: center;
      line-height: 1;
      cursor: pointer;
      text-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
    " class="icon-shigu">
    </div>
  `;
  console.log('✅ 标记内容创建成功');
  console.log(`事故状态: ${accident.accidentDescriptionState}, 应用颜色: ${severityColor.mainColor} 到 ${severityColor.bgColor}`);
  
  try {
    // 创建AMap.LngLat实例
    console.log('=== 创建坐标实例 ===');
    const lngLat = new AMap.LngLat(lng, lat);
    console.log('✅ 坐标实例创建成功:', lngLat);
    // 直接使用坐标，不再调用无效的isValid方法
    console.log('坐标值:', {lng, lat});
    
    // 创建AMap.Marker实例
    console.log('=== 创建标记实例 ===');
    const marker = new AMap.Marker({
      map: map.value,
      position: lngLat,
      content: markerContent,
      offset: new AMap.Pixel(-12, -12), // 偏移量，使标记点居中
      title: `事故ID: ${accident.id || '未知'}, ${accident.description || accident.accidentDescription || '未知事故'}`
    });
    console.log('✅ 标记创建成功:', marker);
    console.log('标记位置:', marker.getPosition());
    console.log('标记是否在地图上:', marker.getMap() ? '是' : '否');
    
    // 添加点击事件
    marker.on('click', () => {
      console.log(`点击了事故标记 ${accident.id}`);
      // 点击标记时可以缩放地图到该位置
      map.value.setCenter([lng, lat]);
      map.value.setZoom(18);
      console.log('地图已缩放并定位到标记位置');
      
      // 通过事件总线发送显示事故详情的事件
      console.log('通过事件总线发送事故详情请求');
      if ($bus) {
        $bus.emit('showAccidentDetail', accident);
        console.log('事故详情请求已发送');
      } else {
        console.error('事件总线未找到');
      }
    });
    
    // 将标记添加到列表
    accidentMarkers.value.push(marker);
    console.log('✅ 标记添加到数组，当前数组长度:', accidentMarkers.value.length);
    console.log('✅ 事故标记添加完成:', accident.id);
    return true;
  } catch (error) {
    console.error('❌ 创建标记失败:', error);
    console.error('❌ 失败详情:', error.message);
    console.error('❌ 失败堆栈:', error.stack);
    return false;
  }
}

onBeforeUnmount(() => {
  if (map.value) {
    map.value.destroy();
  }
  
  // 移除事件监听
  if ($bus) {
    $bus.off('showAccidentOnMap', handleAccidentOnMap);
  }
});
</script>

<style scoped>
.map-container {
  width: 100%;
  height: 100%;
  position: fixed;
}

.amap-container {
  width: 100%;
  height: 100%;
}


</style>
