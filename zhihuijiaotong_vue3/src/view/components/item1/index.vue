<template>
  <div class="accident-list-container">
    <div class="header-section">
      <h3 class="title">最近事故信息</h3>
      <button @click="showAllAccidents" class="query-all-btn">查询全部事故</button>
    </div>
    <div class="accident-list">
      <div 
        :class="['accident-item', getStatusClass(accident.accidentDescriptionState)]" 
        v-for="(accident, index) in accidents" 
        :key="accident.id"
        @click="showAccidentDetail(accident)"
      >
        <div class="accident-time">
          {{ accident.accidentDescriptionTime }}
        </div>
        <div class="accident-content">

          <div class="accident-type">{{ accident.accidentDescription }}</div>
          <div class="accident-location">
            坐标: {{ accident.accidentX }}, {{ accident.accidentY }}
          </div>
          <div class="accident-status">
            状态: <span :class="getStatusClass(accident.accidentDescriptionState)">
              {{ accident.accidentDescriptionState }}
            </span>
          </div>
        </div>
      </div>
      <div v-if="accidents.length === 0" class="no-accidents">
        暂无事故记录
      </div>
    </div>
    
    <!-- 全部事故列表弹窗 -->
    <pop ref="allAccidentsPop" title="全部事故列表">
      <div class="all-accidents-grid" style="display: grid !important; grid-template-columns: repeat(auto-fill, 320px) !important; gap: 15px !important; width: 100% !important; padding: 15px !important; justify-content: start;">
        <div 
          :class="['accident-grid-item', getStatusClass(accident.accidentDescriptionState)]" 
          v-for="(accident, index) in allAccidents" 
          :key="accident.id"
          @click="showAccidentDetail(accident)"
        >
            <!-- 事故头部：时间和状态指示器 -->
            <div class="accident-grid-header">
              <div class="accident-grid-time">
                {{ accident.accidentDescriptionTime }}
              </div>
              <div 
                :class="['status-indicator', getStatusClass(accident.accidentDescriptionState)]"
                :title="accident.accidentDescriptionState"
              ></div>
            </div>
            
            <!-- 事故内容：图标、类型和严重程度 -->
            <div class="accident-grid-content">
              <!-- 事故照片显示 -->
              <div class="accident-grid-image" style="width: 100% !important; height: 120px !important; display: flex !important; align-items: center; justify-content: center; border: 2px solid red !important;">
                <img v-if="accident.imageUrl" :src="accident.imageUrl" alt="事故照片" style="width: 100% !important; height: 120px !important; object-fit: cover !important;" />
                <div v-else class="no-image" style="width: 100% !important; height: 120px !important; display: flex !important; align-items: center; justify-content: center;">
                  📷 暂无照片
                </div>
              </div>
              <div class="accident-grid-icon-section">
                <div :class="['accident-type-icon', getAccidentTypeClass(accident.accidentDescription)]">
                  {{ getAccidentTypeIcon(accident.accidentDescription) }}
                </div>
                <div class="accident-severity-badge" :class="getSeverityClass(accident.accidentDescriptionState)">
                  {{ getSeverityText(accident.accidentDescriptionState) }}
                </div>
              </div>
              
              <div class="accident-grid-info">
                <div class="accident-grid-type">{{ accident.accidentDescription }}</div>
                <div class="accident-grid-location">
                  <span class="location-icon">📍</span>
                  坐标: {{ accident.accidentX.toFixed(4) }}, {{ accident.accidentY.toFixed(4) }}
                </div>
                <div class="accident-grid-status">
                  <span class="status-label">状态:</span>
                  <span :class="getStatusClass(accident.accidentDescriptionState)">
                    {{ accident.accidentDescriptionState }}
                  </span>
                </div>
              </div>
            </div>
            
            <!-- 事故底部：操作或附加信息 -->
            <div class="accident-grid-footer">
              <div class="accident-id">
                ID: {{ accident.id }}
              </div>
              <div class="accident-action">
                <span class="action-icon">📋</span>
                查看详情
              </div>
            </div>
          </div>
          <div v-if="allAccidents.length === 0" class="no-accidents">
            <div class="no-accidents-icon">📭</div>
            暂无事故记录
          </div>
        </div>
    </pop>
    
    <!-- 事故详情弹窗 -->
    <pop ref="accidentDetailPop" title="事故详情">
      <div v-if="currentAccident" class="accident-detail-content">
        <div class="detail-row">
          <label :style="{ fontSize: '18px', fontWeight: 'bold' }">事故描述:</label>
          <span :style="{ display: 'block', marginTop: '5px', wordBreak: 'break-all', fontSize: '18px' }">{{ currentAccident.accidentDescription }}</span>
        </div>

        <!-- 视频下载 -->
        <div v-if="currentAccident.videoUrl" class="detail-row">
          <label :style="{ fontSize: '18px', fontWeight: 'bold' }">视频下载:</label>
          <div class="video-section">
            <button 
      class="download-btn"
      @click="downloadVideo"
      :disabled="isDownloading"
      :style="{
        padding: '12px 24px',
        backgroundColor: '#4361ee',
        color: 'white',
        border: 'none',
        borderRadius: '8px',
        fontSize: '18px',
        fontWeight: 'bold',
        cursor: isDownloading ? 'not-allowed' : 'pointer',
        boxShadow: '0 2px 8px rgba(67, 97, 238, 0.3)',
        transition: 'all 0.3s ease',
        opacity: isDownloading ? '0.7' : '1'
      }"
      @mouseenter="{}"
      @mouseleave="{}"
      @mousedown="{}"
      @mouseup="{}"
    >
      {{ isDownloading ? '下载中...' : '下载视频' }}
    </button>
          </div>
        </div>
        
        <!-- 发送指令按钮 -->
        <div class="detail-row">
          <label :style="{ fontSize: '18px', fontWeight: 'bold' }">操作:</label>
          <div class="video-section">
            <button @click="sendCommand(1)" class="download-btn command-blue" :style="{
              padding: '12px 24px',
              backgroundColor: '#637ef0',
              color: 'white',
              border: 'none',
              borderRadius: '6px',
              fontSize: '18px',
              fontWeight: 'bold',
              cursor: 'pointer',
              boxShadow: '0 2px 6px rgba(99, 126, 240, 0.3)',
              transition: 'all 0.3s ease',
              marginBottom: '8px',
              width: '100%'
            }">发送前方事故拥堵</button>
            <button @click="sendCommand(2)" class="download-btn command-red" :style="{
              padding: '12px 24px',
              backgroundColor: '#ff6b75',
              color: 'white',
              border: 'none',
              borderRadius: '6px',
              fontSize: '18px',
              fontWeight: 'bold',
              cursor: 'pointer',
              boxShadow: '0 2px 6px rgba(255, 107, 117, 0.3)',
              transition: 'all 0.3s ease',
              marginBottom: '8px',
              width: '100%'
            }">发送交通事故检测</button>
            <button @click="sendCommand(0)" class="download-btn command-yellow" :style="{
              padding: '12px 24px',
              backgroundColor: '#ffc34d',
              color: 'white',
              border: 'none',
              borderRadius: '6px',
              fontSize: '18px',
              fontWeight: 'bold',
              cursor: 'pointer',
              boxShadow: '0 2px 6px rgba(255, 195, 77, 0.3)',
              transition: 'all 0.3s ease',
              width: '100%'
            }">发送待机</button>
          </div>
        </div>

        <div class="detail-row">
          <table style="width: 100%; border-collapse: collapse; margin-top: 10px; font-size: 27px; font-weight: bold;">
            <tr style="border-bottom: 1px solid rgba(255, 255, 255, 0.1);">
              <td style="padding: 10px 8px; text-align: left; color: rgba(255, 255, 255, 0.8); width: 30%; font-size: 27px;">发生时间:</td>
              <td style="padding: 10px 8px; text-align: left; color: white; font-size: 27px;">{{ currentAccident.accidentDescriptionTime }}</td>
            </tr>
            <tr style="border-bottom: 1px solid rgba(255, 255, 255, 0.1);">
              <td style="padding: 10px 8px; text-align: left; color: rgba(255, 255, 255, 0.8); font-size: 27px;">事故状态:</td>
              <td style="padding: 10px 8px; text-align: left; color: white; font-size: 27px;">
                <span :class="getStatusClass(currentAccident.accidentDescriptionState)">
                  {{ currentAccident.accidentDescriptionState }}
                </span>
              </td>
            </tr>
            <tr>
              <td style="padding: 10px 8px; text-align: left; color: rgba(255, 255, 255, 0.8); font-size: 27px;">事故坐标:</td>
              <td style="padding: 10px 8px; text-align: left; color: white; font-size: 27px;">X: {{ currentAccident.accidentX }}, Y: {{ currentAccident.accidentY }}</td>
            </tr>
          </table>
        </div>

        <div class="detail-row">
          <label :style="{ fontSize: '27px', fontWeight: 'bold' }">事故图片:</label>
          <div class="accident-image">
            <img v-if="currentAccident.imageUrl" :src="currentAccident.imageUrl" alt="事故图片" style="max-width: 100%; height: auto;" />
            <span v-else style="font-size: '27px';">暂无图片</span>
          </div>
        </div>
      </div>
      <div v-else class="loading-text">加载中...</div>
    </pop>
  </div>
</template>

<script>
import { ref, onMounted, onBeforeUnmount, getCurrentInstance } from 'vue';
import request from '@/api/request';
import pop from '@/components/pop/pop.vue';



export default {
  name: "accident-list",
  components: { pop },
  setup() {
    const accidents = ref([]);
    const allAccidents = ref([]); // 全部事故数据
    const accidentDetailPop = ref(null);
    const allAccidentsPop = ref(null); // 全部事故弹窗
    const currentAccident = ref(null);
    // 事故详情缓存对象，减少重复请求
    const accidentDetailCache = ref({});
    // 下载状态
    const isDownloading = ref(false);
    // 获取当前Vue实例
    const instance = getCurrentInstance();
    // 获取事件总线
    const $bus = instance?.appContext.config.globalProperties.$bus;


    
    const getLatestAccidents = async () => {
      try {
        const response = await request({
          url: '/api/v1/accidents',
          method: 'GET',
          params: {
            page: 1,
            size: 1000
          }
        });
        
        console.log('=== API响应开始 ===');
        console.log('API Response:', response);
        console.log('API Response Code:', response.code);
        console.log('API Response Data:', response.data);
        console.log('API Response Data Records:', response.data?.records);
        if (response.data?.records) {
          console.log('API返回事故总数:', response.data.records.length);
          console.log('所有事故ID:', response.data.records.map(r => r.id));
        }
        
        // Check if response and response.data exist
        if (response) {
          // Check if response has a code property (standard API response)
          if (response.code === 200) {
            // Ensure response.data exists
            const data = response.data || {};
            let records = data.records || [];
            // Ensure records is an array
            records = Array.isArray(records) ? records : [];
            console.log('处理前记录数量:', records.length);
            
            // Convert coordinate field names from API to frontend format
            console.log('=== 开始处理坐标转换 ===');
            records = records.map(record => {
              console.log(`处理记录ID: ${record.id}`);
              console.log(`原始坐标数据: accident_x=${record.accident_x}, accident_y=${record.accident_y}, lng=${record.lng}, lat=${record.lat}`);
              
              // 使用空值合并运算符代替逻辑或，确保0被正确处理为有效值
              const accidentX = parseFloat(record.accident_x ?? record.accidentX ?? record.coordinateX ?? record.lng ?? record.longitude ?? record.coordinate?.x ?? null);
              const accidentY = parseFloat(record.accident_y ?? record.accidentY ?? record.coordinateY ?? record.lat ?? record.latitude ?? record.coordinate?.y ?? null);
              
              // 地图中心坐标（沈阳工学院）
              const centerLng = 123.724768;
              const centerLat = 41.847804;
              
              // 如果API返回了有效的坐标，就使用API的坐标
              // 否则生成随机坐标，围绕中心点±0.01度范围内（约1公里）
              let finalLng, finalLat;
              if (!isNaN(accidentX) && !isNaN(accidentY)) {
                finalLng = accidentX;
                finalLat = accidentY;
                console.log(`使用API返回的坐标: ${finalLng}, ${finalLat}`);
              } else {
                // 生成随机坐标，围绕中心点
                finalLng = centerLng + (Math.random() - 0.5) * 0.02;
                finalLat = centerLat + (Math.random() - 0.5) * 0.02;
                console.log(`生成随机坐标: ${finalLng}, ${finalLat}`);
              }
              
              const result = {
                ...record,
                // 使用有效的坐标
                accidentX: finalLng,
                accidentY: finalLat,
                // 确保标记时可以使用lng/lat字段
                lng: finalLng,
                lat: finalLat
              };
              
              console.log(`转换后坐标: accidentX=${result.accidentX}, accidentY=${result.accidentY}, lng=${result.lng}, lat=${result.lat}`);
              return result;
            });
            
            // Sort records by accident time descending to get latest accidents
            console.log('=== 开始按时间排序 ===');
            records.sort((a, b) => {
              const dateA = a.accidentDescriptionTime ? new Date(a.accidentDescriptionTime) : NaN;
              const dateB = b.accidentDescriptionTime ? new Date(b.accidentDescriptionTime) : NaN;
              // Handle invalid dates gracefully
              if (!isNaN(dateA) && !isNaN(dateB)) {
                return dateB - dateA;
              }
              // If only dateA is valid, put it first
              if (!isNaN(dateA)) return -1;
              // If only dateB is valid, put it first
              if (!isNaN(dateB)) return 1;
              // If neither is valid, keep original order
              return 0;
            });
            
            // Take the latest 10 records for display in the list
            accidents.value = records.slice(0, 10);
            console.log('列表显示最新10条事故:', accidents.value.length, '个事故');
            console.log('列表事故ID:', accidents.value.map(a => a.id));
            
            // 过滤所有事故记录中有效坐标的事故，允许0作为有效值
            console.log('=== 开始过滤所有事故的有效坐标 ===');
            const validAccidents = records.filter(accident => {
              const hasValidCoords = typeof accident.accidentX === 'number' && typeof accident.accidentY === 'number' && !isNaN(accident.accidentX) && !isNaN(accident.accidentY);
              console.log('事故ID:', accident.id, '有效坐标:', hasValidCoords, 'X:', accident.accidentX, 'Y:', accident.accidentY);
              return hasValidCoords;
            });
            
            console.log('=== 有效坐标事故结果 ===');
            console.log('有效事故数量:', validAccidents.length);
            console.log('有效事故详细信息:', validAccidents);
            console.log('有效事故ID列表:', validAccidents.map(a => a.id));
            
            // 登录成功后自动发送所有事故到地图
            if ($bus) {
              console.log('=== 发送事故到地图组件 ===');
              console.log('发送事故数量:', validAccidents.length);
              console.log('发送事故ID:', validAccidents.map(a => a.id));
              $bus.emit('showAccidentOnMap', validAccidents);
              console.log('自动发送所有事故到地图，共', validAccidents.length, '个事故');
            }
          } else {
            // Treat non-200 status codes as errors
            throw new Error(response.message || `API error: ${response.code}`);
          }
        } else {
          // Handle cases where response structure doesn't match expectations
          throw new Error('API response structure is unexpected');
        }
      } catch (error) {
        console.error('Error fetching accidents:', error);
        console.error('Error message:', error.message);
        console.error('Error stack:', error.stack);
        console.error('Error config:', error.config);
        // Clear accidents list if there's an error
        accidents.value = [];
        // 错误时清空地图标记
        if ($bus) {
          $bus.emit('showAccidentOnMap', []);
          console.log('获取事故失败，清空地图标记');
        }
      }
    };
    
    const getStatusClass = (status) => {
      switch (status) {
        case '处理中':
          return 'status-processing';
        case '已处理':
          return 'status-resolved';
        case '待处理':
          return 'status-pending';
        case '轻微':
          return 'status-minor';
        case '中等':
          return 'status-medium';
        case '严重':
          return 'status-severe';
        default:
          return '';
      }
    };
    
    // 获取事故类型图标
    const getAccidentTypeIcon = (type) => {
      const typeLower = type.toLowerCase();
      if (typeLower.includes('碰撞') || typeLower.includes('追尾') || typeLower.includes('刮擦')) {
        return '🚗💥';
      } else if (typeLower.includes('拥堵') || typeLower.includes('阻塞')) {
        return '🚙🚗🚕';
      } else if (typeLower.includes('故障') || typeLower.includes('抛锚')) {
        return '⚠️🔧';
      } else if (typeLower.includes('行人') || typeLower.includes('非机动车')) {
        return '🚶‍♂️🚲';
      } else if (typeLower.includes('施工') || typeLower.includes('维修')) {
        return '🏗️🚧';
      } else if (typeLower.includes('天气') || typeLower.includes('雨雪') || typeLower.includes('雾')) {
        return '🌧️❄️🌫️';
      } else {
        return '🚨';
      }
    };
    
    // 获取事故类型样式类
    const getAccidentTypeClass = (type) => {
      const typeLower = type.toLowerCase();
      if (typeLower.includes('碰撞') || typeLower.includes('追尾') || typeLower.includes('刮擦')) {
        return 'type-collision';
      } else if (typeLower.includes('拥堵') || typeLower.includes('阻塞')) {
        return 'type-traffic';
      } else if (typeLower.includes('故障') || typeLower.includes('抛锚')) {
        return 'type-breakdown';
      } else if (typeLower.includes('行人') || typeLower.includes('非机动车')) {
        return 'type-pedestrian';
      } else if (typeLower.includes('施工') || typeLower.includes('维修')) {
        return 'type-construction';
      } else if (typeLower.includes('天气') || typeLower.includes('雨雪') || typeLower.includes('雾')) {
        return 'type-weather';
      } else {
        return 'type-other';
      }
    };
    
    // 获取严重程度样式类
    const getSeverityClass = (status) => {
      switch (status) {
        case '轻微':
          return 'severity-minor';
        case '中等':
          return 'severity-medium';
        case '严重':
          return 'severity-severe';
        default:
          return '';
      }
    };
    
    // 获取严重程度文本
    const getSeverityText = (status) => {
      switch (status) {
        case '轻微':
          return '轻微';
        case '中等':
          return '中等';
        case '严重':
          return '严重';
        default:
          return '';
      }
    };

    // 获取事故详情（带缓存）
    const fetchAccidentDetail = async (id) => {
      // 设置缓存过期时间为5分钟
      const cacheExpireTime = 5 * 60 * 1000;
      const now = Date.now();
      const cachedData = accidentDetailCache.value[id];
      
      try {
        // 如果缓存存在且未过期，直接使用缓存数据
        if (cachedData && (now - cachedData.timestamp < cacheExpireTime)) {
          currentAccident.value = cachedData.data;
          return;
        }
        
        // 否则请求新数据
        const response = await request({
          url: `/api/v1/accidents/${id}`,
          method: 'GET'
        });

        // Check if response exists and has expected structure
        if (response) {
          if (response.code === 200) {
            // Check if response.data exists
            const detailData = response.data;
            if (detailData) {
              // 打印返回的详细数据结构
              console.log('事故详情返回数据:', JSON.stringify(detailData, null, 2));
              // 根据后端返回结构，实际事故数据可能在data字段中
              const accidentData = detailData.data || detailData;
              // Convert coordinate field names
              accidentData.accidentX = accidentData.accident_x || accidentData.accidentX;
              accidentData.accidentY = accidentData.accident_y || accidentData.accidentY;
              // 合并列表数据和详情数据，确保坐标信息不丢失
              const mergedData = {
                ...currentAccident.value,
                ...accidentData,
                // 确保坐标始终从详情数据或列表数据中获取
                accidentX: accidentData.accidentX || accidentData.coordinateX || currentAccident.value.accidentX,
                accidentY: accidentData.accidentY || accidentData.coordinateY || currentAccident.value.accidentY
              };
              currentAccident.value = mergedData;
              // 更新缓存
              accidentDetailCache.value[id] = {
                data: mergedData,
                timestamp: now
              };
              // 事故详情更新，地图标记保持不变
              console.log('事故详情更新，地图标记保持不变')
            } else {
              throw new Error('API response data is missing');
            }
          } else {
            throw new Error(response.message || `API error: ${response.code}`);
          }
        } else {
          throw new Error('API response is undefined');
        }
      } catch (error) {
        console.error('Error fetching accident detail:', error);
        // 请求失败时，如果有缓存则使用缓存数据，否则保持当前值不变
        if (cachedData) {
          currentAccident.value = cachedData.data;
        } else if (!currentAccident.value) {
          // 只有在当前值为null时才设置为null，避免覆盖已有的有效数据
          currentAccident.value = null;
        }
      }
    };

    // 显示事故详情弹窗
    const showAccidentDetail = async (accident) => {
      // 检查是否有缓存数据
      const cachedData = accidentDetailCache.value[accident.id];
      
      // 立即设置当前事故数据（缓存或列表数据）
      currentAccident.value = cachedData ? cachedData.data : accident;
      
      // 立即显示弹窗
      if (accidentDetailPop.value) {
        accidentDetailPop.value.getShow();
      }
      
      // 查看事故详情，地图标记保持不变
      console.log('查看事故详情，地图标记保持不变');
      
      // 异步请求最新数据
      await fetchAccidentDetail(accident.id);
    };
    
    onMounted(() => {
      getLatestAccidents();
      // Refresh every 30 seconds
      setInterval(getLatestAccidents, 30000);
      
      // 添加事件总线监听，用于接收地图组件发来的事故详情请求
      $bus.on('showAccidentDetail', (accident) => {
        console.log('从地图组件接收到事故详情请求:', accident);
        showAccidentDetail(accident);
      });
    });
    
    // 在组件销毁前移除事件监听
    onBeforeUnmount(() => {
      $bus.off('showAccidentDetail');
    });
    

    
    // 下载视频
    const downloadVideo = () => {
      if (!currentAccident.value?.videoUrl) return;
      
      isDownloading.value = true;
      
      const videoUrl = currentAccident.value.videoUrl;
      const filename = `accident_video_${currentAccident.value.id}.mp4`;
      
      const a = document.createElement('a');
      a.href = videoUrl;
      a.download = filename;
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
      
      // 模拟下载完成，实际项目中可根据实际情况调整
      setTimeout(() => {
        isDownloading.value = false;
      }, 1000);
    };
    
    // 发送指令到后端
    const sendCommand = async (value = 1) => {
      try {
        const response = await request({
          url: '/api/v1/iot/send-command',
          method: 'POST',
          data: {
            value: value
          }
        });
        
        if (response.code === 200) {
          alert(`指令(value:${value})发送成功`);
          console.log(`指令(value:${value})发送成功:`, response);
        } else {
          throw new Error(response.message || `API error: ${response.code}`);
        }
      } catch (error) {
        console.error(`发送指令(value:${value})失败:`, error);
        alert(`指令(value:${value})发送失败: ` + (error.message || '未知错误'));
      }
    };
    
    // 查询并显示全部事故列表
    const showAllAccidents = async () => {
      try {
        const response = await request({
          url: '/api/v1/accidents-info',
          method: 'GET',
          params: {
            page: 1,
            size: 1000 // 获取全部事故，使用较大的size值
          }
        });
        
        console.log('=== 查询全部事故响应开始 ===');
        console.log('API Response:', response);
        
        if (response && response.code === 200) {
          const data = response.data || {};
          let records = data.records || [];
          records = Array.isArray(records) ? records : [];
          console.log('查询到的全部事故数量:', records.length);
          
          // 转换坐标格式
          records = records.map(record => {
            // 使用空值合并运算符代替逻辑或，确保0被正确处理为有效值
            const accidentX = parseFloat(record.accident_x ?? record.accidentX ?? record.coordinateX ?? record.lng ?? record.longitude ?? record.coordinate?.x ?? null);
            const accidentY = parseFloat(record.accident_y ?? record.accidentY ?? record.coordinateY ?? record.lat ?? record.latitude ?? record.coordinate?.y ?? null);
            
            // 地图中心坐标（沈阳工学院）
            const centerLng = 123.724768;
            const centerLat = 41.847804;
            
            // 如果API返回了有效的坐标，就使用API的坐标
            // 否则生成随机坐标，围绕中心点±0.01度范围内（约1公里）
            let finalLng, finalLat;
            if (!isNaN(accidentX) && !isNaN(accidentY)) {
              finalLng = accidentX;
              finalLat = accidentY;
            } else {
              // 生成随机坐标，围绕中心点
              finalLng = centerLng + (Math.random() - 0.5) * 0.02;
              finalLat = centerLat + (Math.random() - 0.5) * 0.02;
            }
            
            return {
              ...record,
              // 使用有效的坐标
              accidentX: finalLng,
              accidentY: finalLat,
              // 确保标记时可以使用lng/lat字段
              lng: finalLng,
              lat: finalLat
            };
          });
          
          // 按时间降序排序
          records.sort((a, b) => {
            const dateA = a.accidentDescriptionTime ? new Date(a.accidentDescriptionTime) : NaN;
            const dateB = b.accidentDescriptionTime ? new Date(b.accidentDescriptionTime) : NaN;
            if (!isNaN(dateA) && !isNaN(dateB)) {
              return dateB - dateA;
            }
            if (!isNaN(dateA)) return -1;
            if (!isNaN(dateB)) return 1;
            return 0;
          });
          
          // 更新全部事故列表数据
          allAccidents.value = records;
          console.log('已获取全部事故列表，共', records.length, '条记录');
          
          // 打开全部事故弹窗
          if (allAccidentsPop.value) {
            allAccidentsPop.value.getShow();
          }
          
          // 过滤有效坐标的事故并发送到地图
          const validAccidents = records.filter(accident => {
            const hasValidCoords = typeof accident.accidentX === 'number' && typeof accident.accidentY === 'number' && !isNaN(accident.accidentX) && !isNaN(accident.accidentY);
            return hasValidCoords;
          });
          
          if ($bus) {
            $bus.emit('showAccidentOnMap', validAccidents);
            console.log('发送全部事故到地图，共', validAccidents.length, '个事故');
          }
        }
      } catch (error) {
        console.error('查询全部事故失败:', error);
        alert('查询全部事故失败: ' + (error.message || '未知错误'));
      }
    };
    
    return {
      accidents,
      allAccidents,
      accidentDetailPop,
      allAccidentsPop,
      currentAccident,
      getStatusClass,
      getAccidentTypeIcon,
      getAccidentTypeClass,
      getSeverityClass,
      getSeverityText,
      fetchAccidentDetail,
      showAccidentDetail,
      downloadVideo,
      sendCommand,
      showAllAccidents
    };
  }
};
</script>

<style lang="scss" scoped>
.accident-list-container {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  
  .header-section {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 15px;
    margin: 15px 0 10px;
  }
  
  .title {
    font-size: 27px;
    color: #DBF6FF;
    margin: 0;
  }
  
  .query-all-btn {
    padding: 12px 24px;
    background-color: #4cc9f0;
    color: #fff;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    font-size: 21px;
    
    &:hover {
      background-color: #3bafda;
    }
  }
  
  .accident-list {
    flex: 1;
    padding: 0 15px 15px;
    overflow-y: auto;
  }
  
  .accident-item {
    border-radius: 6px;
    padding: 10px;
    margin-bottom: 10px;
    cursor: pointer;
    border-left: 3px solid #4cc9f0;
  }
  
  .accident-item.status-minor {
    background: rgba(4, 206, 247, 0.1);
    border-left-color: #04CEF7;
  }
  
  .accident-item.status-medium {
    background: rgba(255, 165, 0, 0.1);
    border-left-color: #ffa500;
  }
  
  .accident-item.status-severe {
    background: rgba(255, 107, 107, 0.1);
    border-left-color: #ff6b6b;
  }
  
  .accident-time {
    font-size: 21px;
    color: #aaa;
    margin-bottom: 8px;
  }
  
  .accident-content {
    
    .accident-id {
      font-size: 21px;
      color: #4cc9f0;
      margin-bottom: 5px;
    }
    
    .accident-type {
      font-weight: bold;
      font-size: 24px;
      margin-bottom: 5px;
      color: #fff;
    }
    
    .accident-location {
      font-size: 21px;
      color: #aaa;
      margin-bottom: 3px;
    }
    
    .accident-status {
        font-size: 21px;
        color: #aaa;
        
        .status-processing {
          color: #ffa500;
        }
        
        .status-resolved {
          color: #04CEF7;
        }
        
        .status-pending {
          color: #ff6b6b;
        }
        
        .status-minor {
          color: #04CEF7;
        }
        
        .status-medium {
          color: #ffa500;
        }
        
        .status-severe {
          color: #ff6b6b;
        }
      }
  
  .no-accidents {
    text-align: center;
    color: #aaa;
    padding: 20px;
  }

  /* 事故详情弹窗样式 */
  .accident-detail-content {
    width: 100%;
  }

  .detail-row {
    display: flex;
    margin-bottom: 15px;
    width: 100%;
    align-items: flex-start;
  }

  .detail-row label {
    width: 120px;
    font-weight: bold;
    color: #ffffff;
    margin-right: 20px;
    text-align: right;
    flex-shrink: 0;
    font-size: 18px; /* 放大事故描述四个字的字体大小 */
  }

  .detail-row span {
    color: #cccccc;
    flex: 1;
    text-align: left;
    word-break: break-all;
    display: block; /* 确保描述内容换行显示 */
    margin-top: 5px; /* 添加与标签的间距 */
  }

  .accident-image {
    flex: 1;
    text-align: left;
    
    img {
      max-width: 100%;
      max-height: 300px;
      border-radius: 4px;
      margin-top: 10px;
    }
    
    span {
      color: #666;
    }
  }


  /* 视频下载样式 */
  .video-section {
    display: flex;
    flex-direction: column;
    gap: 10px;
    align-items: flex-start;
  }
  
  .download-btn {
    padding: 12px 24px;
    background-color: #4361ee;
    color: #fff;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    font-size: 16px;
    font-weight: 500;
    box-shadow: 0 2px 8px rgba(67, 97, 238, 0.3);
    transition: all 0.3s ease;
    
    &:hover {
      background-color: #3a0ca3;
      box-shadow: 0 4px 12px rgba(67, 97, 238, 0.4);
      transform: translateY(-2px);
    }
    
    &:active {
      transform: translateY(0);
      box-shadow: 0 2px 6px rgba(67, 97, 238, 0.3);
    }
    
    &:disabled {
      background-color: #94d3ef;
      cursor: not-allowed;
      box-shadow: none;
      transform: none;
    }
  }
  
  /* 发送指令按钮颜色 */
  .video-section .download-btn.command-blue {
    background-color: #4cc9f0 !important;
    
    &:hover {
      background-color: #3bafda !important;
    }
    
    &:disabled {
      background-color: #94d3ef !important;
    }
  }
  
  .video-section .download-btn.command-red {
    background-color: #ff6b6b !important;
    
    &:hover {
      background-color: #ff5252 !important;
    }
    
    &:disabled {
      background-color: #ffb3b3 !important;
    }
  }

  .loading-text {
    text-align: center;
    color: #aaa;
    padding: 20px;
  }
  
  /* 覆盖pop组件的默认slot样式 */
  .popWinMain .popWinMainInner > .slot {
    display: block !important;
    height: auto !important;
    max-height: calc(100% - 100px) !important;
    padding: 0 !important;
    overflow-y: auto !important;
  }
  
  /* 全部事故网格样式 */
  .all-accidents-grid {
    width: 100% !important;
    display: grid !important;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)) !important;
    gap: 15px !important;
    justify-items: stretch !important;
    align-content: start !important;
    padding: 15px !important;
    overflow-y: auto !important;
    max-height: 600px !important;
    
    /* 自定义滚动条样式 */
    &::-webkit-scrollbar {
      width: 8px;
    }
    
    &::-webkit-scrollbar-track {
      background: rgba(255, 255, 255, 0.1);
      border-radius: 4px;
    }
    
    &::-webkit-scrollbar-thumb {
      background: rgba(76, 201, 240, 0.6);
      border-radius: 4px;
    }
    
    &::-webkit-scrollbar-thumb:hover {
      background: rgba(76, 201, 240, 0.9);
    }
  }
  
  /* 事故网格项样式 */
  .accident-grid-item {
    border-radius: 12px;
    padding: 15px;
    cursor: pointer;
    border: 2px solid rgba(255, 255, 255, 0.1);
    transition: all 0.3s ease;
    background: linear-gradient(135deg, rgba(0, 0, 0, 0.3) 0%, rgba(0, 0, 0, 0.15) 100%);
    backdrop-filter: blur(8px);
    display: flex;
    flex-direction: column;
    min-height: 180px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
    position: relative;
    overflow: hidden;
    width: 100%;
    
    /* 增加一个轻微的发光边框效果 */
    &::before {
      content: '';
      position: absolute;
      top: -2px;
      left: -2px;
      right: -2px;
      bottom: -2px;
      border-radius: 12px;
      background: linear-gradient(45deg, transparent, transparent, rgba(255, 255, 255, 0.1), transparent, transparent);
      z-index: -1;
      opacity: 0;
      transition: opacity 0.3s ease;
    }
    
    &:hover {
      transform: translateY(-4px);
      box-shadow: 0 8px 24px rgba(0, 0, 0, 0.4);
      border-color: rgba(255, 255, 255, 0.3);
      
      &::before {
        opacity: 1;
      }
    }
    
    &.status-processing {
      border-color: rgba(255, 165, 0, 0.6);
      background: linear-gradient(135deg, rgba(255, 165, 0, 0.15) 0%, rgba(0, 0, 0, 0.15) 100%);
      box-shadow: 0 4px 12px rgba(255, 165, 0, 0.2);
    }
    
    &.status-resolved {
      border-color: rgba(4, 206, 247, 0.6);
  }
  
  /* 分隔符样式 */
  .accident-grid-item + .accident-grid-item {
    margin-top: 30px;
    
    &::before {
      content: "=";
      position: absolute;
      top: -15px;
      left: 50%;
      transform: translateX(-50%);
      font-size: 30px;
      color: rgba(76, 201, 240, 0.8);
      text-shadow: 0 0 15px rgba(76, 201, 240, 0.6);
      font-weight: bold;
      z-index: 10;
    }
  }
      background: linear-gradient(135deg, rgba(4, 206, 247, 0.15) 0%, rgba(0, 0, 0, 0.15) 100%);
      box-shadow: 0 4px 12px rgba(4, 206, 247, 0.2);
    }
    
    &.status-pending {
      border-color: rgba(255, 107, 107, 0.6);
      background: linear-gradient(135deg, rgba(255, 107, 107, 0.15) 0%, rgba(0, 0, 0, 0.15) 100%);
      box-shadow: 0 4px 12px rgba(255, 107, 107, 0.2);
    }
    
    &.status-minor {
      border-color: rgba(4, 206, 247, 0.6);
      background: linear-gradient(135deg, rgba(4, 206, 247, 0.15) 0%, rgba(0, 0, 0, 0.15) 100%);
      box-shadow: 0 4px 12px rgba(4, 206, 247, 0.2);
    }
    
    &.status-medium {
      border-color: rgba(255, 165, 0, 0.6);
      background: linear-gradient(135deg, rgba(255, 165, 0, 0.15) 0%, rgba(0, 0, 0, 0.15) 100%);
      box-shadow: 0 4px 12px rgba(255, 165, 0, 0.2);
    }
    
    &.status-severe {
      border-color: rgba(255, 107, 107, 0.6);
      background: linear-gradient(135deg, rgba(255, 107, 107, 0.15) 0%, rgba(0, 0, 0, 0.15) 100%);
      box-shadow: 0 4px 12px rgba(255, 107, 107, 0.2);
    }
  }
  
  /* 事故头部样式 */
  .accident-grid-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 15px;
    padding-bottom: 10px;
    border-bottom: 1px solid rgba(255, 255, 255, 0.15);
  }
  
  .accident-grid-time {
    font-size: 18px;
    color: #fff;
    background: rgba(255, 255, 255, 0.15);
    padding: 4px 8px;
    border-radius: 6px;
    backdrop-filter: blur(5px);
    font-weight: 500;
  }
  
  /* 状态指示器 */
  .status-indicator {
    width: 14px;
    height: 14px;
    border-radius: 50%;
    animation: pulse 2s infinite;
    box-shadow: 0 0 0 2px rgba(255, 255, 255, 0.1);
    
    &.status-processing {
      background-color: #ffa500;
      box-shadow: 0 0 0 2px rgba(255, 165, 0, 0.3), 0 0 10px rgba(255, 165, 0, 0.2);
      animation: pulse-yellow 2s infinite;
    }
    
    &.status-resolved {
      background-color: #04CEF7;
      box-shadow: 0 0 0 2px rgba(4, 206, 247, 0.3), 0 0 10px rgba(4, 206, 247, 0.2);
      animation: none;
    }
    
    &.status-pending {
      background-color: #ff6b6b;
      box-shadow: 0 0 0 2px rgba(255, 107, 107, 0.3), 0 0 10px rgba(255, 107, 107, 0.2);
      animation: pulse-red 2s infinite;
    }
    
    &.status-minor {
      background-color: #04CEF7;
      box-shadow: 0 0 0 2px rgba(4, 206, 247, 0.3), 0 0 10px rgba(4, 206, 247, 0.2);
      animation: pulse-blue 2s infinite;
    }
    
    &.status-medium {
      background-color: #ffa500;
      box-shadow: 0 0 0 2px rgba(255, 165, 0, 0.3), 0 0 10px rgba(255, 165, 0, 0.2);
      animation: pulse-yellow 2s infinite;
    }
    
    &.status-severe {
      background-color: #ff6b6b;
      box-shadow: 0 0 0 2px rgba(255, 107, 107, 0.3), 0 0 10px rgba(255, 107, 107, 0.2);
      animation: pulse-red 2s infinite;
    }
  }
  
  /* 脉冲动画 */
  @keyframes pulse-yellow {
    0% {
      box-shadow: 0 0 0 0 rgba(255, 165, 0, 0.4), 0 0 10px rgba(255, 165, 0, 0.2);
    }
    70% {
      box-shadow: 0 0 0 10px rgba(255, 165, 0, 0), 0 0 20px rgba(255, 165, 0, 0.1);
    }
    100% {
      box-shadow: 0 0 0 0 rgba(255, 165, 0, 0), 0 0 10px rgba(255, 165, 0, 0.2);
    }
  }
  
  @keyframes pulse-red {
    0% {
      box-shadow: 0 0 0 0 rgba(255, 107, 107, 0.4), 0 0 10px rgba(255, 107, 107, 0.2);
    }
    70% {
      box-shadow: 0 0 0 10px rgba(255, 107, 107, 0), 0 0 20px rgba(255, 107, 107, 0.1);
    }
    100% {
      box-shadow: 0 0 0 0 rgba(255, 107, 107, 0), 0 0 10px rgba(255, 107, 107, 0.2);
    }
  }
  
  @keyframes pulse-blue {
    0% {
      box-shadow: 0 0 0 0 rgba(4, 206, 247, 0.4), 0 0 10px rgba(4, 206, 247, 0.2);
    }
    70% {
      box-shadow: 0 0 0 10px rgba(4, 206, 247, 0), 0 0 20px rgba(4, 206, 247, 0.1);
    }
    100% {
      box-shadow: 0 0 0 0 rgba(4, 206, 247, 0), 0 0 10px rgba(4, 206, 247, 0.2);
    }
  }
  
  /* 事故内容样式 */
  .accident-grid-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    margin-bottom: 15px;
    background: rgba(0, 0, 0, 0.2);
    padding: 12px;
    border-radius: 8px;
    border: 1px solid rgba(255, 255, 255, 0.1);
    width: 100% !important;
    box-sizing: border-box;
  }
  
  /* 事故照片样式 */
  .accident-grid-image {
    width: 100% !important;
    height: 120px !important;
    margin: 0 0 6px 0 !important;
    border-radius: 6px;
    overflow: hidden;
    background: rgba(0, 0, 0, 0.3);
    display: flex !important;
    align-items: center;
    justify-content: center;
    border: 2px solid red !important;
    box-sizing: border-box;
    flex-shrink: 0;
  }
  
  .accident-grid-image img {
    width: 100% !important;
    height: 120px !important;
    object-fit: cover;
    transition: transform 0.3s ease;
    display: block !important;
    max-width: 100% !important;
    max-height: 120px !important;
    min-width: 100% !important;
    min-height: 120px !important;
  }
  
  /* 确保所有网格项宽度一致 */
  .accident-grid-item {
    width: 100% !important;
    box-sizing: border-box;
  }
  
  .accident-grid-item:hover .accident-grid-image img {
    transform: scale(1.05);
  }
  
  .accident-grid-image .no-image {
    color: rgba(255, 255, 255, 0.6);
    font-size: 21px;
    text-align: center;
    padding: 20px;
  }
  
  /* 图标区域 */
  .accident-grid-icon-section {
    display: flex;
    align-items: center;
    margin-bottom: 12px;
    gap: 15px;
  }
  
  /* 事故类型图标 */
  .accident-type-icon {
    font-size: 32px;
    width: 60px;
    height: 60px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    background: rgba(255, 255, 255, 0.15);
    border: 2px solid rgba(255, 255, 255, 0.25);
    backdrop-filter: blur(10px);
    transition: all 0.3s ease;
    
    .accident-grid-item:hover & {
      transform: scale(1.1);
      background: rgba(255, 255, 255, 0.2);
    }
    
    &.type-collision {
      background: rgba(255, 107, 107, 0.25);
      border-color: rgba(255, 107, 107, 0.6);
      box-shadow: 0 0 15px rgba(255, 107, 107, 0.2);
    }
    
    &.type-traffic {
      background: rgba(255, 165, 0, 0.25);
      border-color: rgba(255, 165, 0, 0.6);
      box-shadow: 0 0 15px rgba(255, 165, 0, 0.2);
    }
    
    &.type-weather {
      background: rgba(4, 206, 247, 0.25);
      border-color: rgba(4, 206, 247, 0.6);
      box-shadow: 0 0 15px rgba(4, 206, 247, 0.2);
    }
    
    &.type-other {
      background: rgba(153, 102, 255, 0.25);
      border-color: rgba(153, 102, 255, 0.6);
      box-shadow: 0 0 15px rgba(153, 102, 255, 0.2);
    }
    
    &.type-traffic {
      background: rgba(255, 165, 0, 0.2);
      border-color: #ffa500;
    }
    
    &.type-breakdown {
      background: rgba(236, 201, 75, 0.2);
      border-color: #ecc94b;
    }
    
    &.type-pedestrian {
      background: rgba(72, 187, 120, 0.2);
      border-color: #48bb78;
    }
    
    &.type-construction {
      background: rgba(147, 197, 253, 0.2);
      border-color: #93c5fd;
    }
    
    &.type-weather {
      background: rgba(96, 165, 250, 0.2);
      border-color: #60a5fa;
    }
    
    &.type-other {
      background: rgba(167, 139, 250, 0.2);
      border-color: #a78bfa;
    }
  }
  
  /* 严重程度徽章 */
  .accident-severity-badge {
    padding: 3px 10px;
    border-radius: 12px;
    font-size: 16.5px;
    font-weight: bold;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    backdrop-filter: blur(5px);
    transition: all 0.3s ease;
    
    &.severity-minor {
      background: linear-gradient(135deg, rgba(4, 206, 247, 0.25), rgba(4, 206, 247, 0.15));
      color: #04CEF7;
      border: 1px solid rgba(4, 206, 247, 0.6);
      box-shadow: 0 2px 6px rgba(4, 206, 247, 0.2);
    }
    
    &.severity-medium {
      background: linear-gradient(135deg, rgba(255, 165, 0, 0.25), rgba(255, 165, 0, 0.15));
      color: #ffa500;
      border: 1px solid rgba(255, 165, 0, 0.6);
      box-shadow: 0 2px 6px rgba(255, 165, 0, 0.2);
    }
    
    &.severity-severe {
      background: linear-gradient(135deg, rgba(255, 107, 107, 0.25), rgba(255, 107, 107, 0.15));
      color: #ff6b6b;
      border: 1px solid rgba(255, 107, 107, 0.6);
      box-shadow: 0 2px 6px rgba(255, 107, 107, 0.2);
    }
    
    .accident-grid-item:hover & {
      transform: scale(1.05);
      box-shadow: 0 3px 8px rgba(0, 0, 0, 0.3);
    }
  }
  
  /* 信息区域 */
  .accident-grid-info {
    flex: 1;
  }
  
  .accident-grid-type {
    font-weight: bold;
    font-size: 22.5px;
    margin-bottom: 9px;
    color: #fff;
    line-height: 1.4;
    text-shadow: 0 1px 3px rgba(0, 0, 0, 0.3);
    background: rgba(255, 255, 255, 0.1);
    padding: 4px 8px;
    border-radius: 6px;
    display: inline-block;
  }
  
  .accident-grid-location {
    font-size: 18px;
    color: rgba(255, 255, 255, 0.8);
    margin-bottom: 8px;
    display: flex;
    align-items: center;
    gap: 6px;
    background: rgba(0, 0, 0, 0.2);
    padding: 3px 8px;
    border-radius: 4px;
    border: 1px solid rgba(255, 255, 255, 0.1);
    
    .location-icon {
      font-size: 16.5px;
      color: #04CEF7;
    }
  }
  
  .accident-grid-status {
    font-size: 18px;
    color: rgba(255, 255, 255, 0.8);
    display: flex;
    align-items: center;
    gap: 6px;
    background: rgba(0, 0, 0, 0.2);
    padding: 3px 8px;
    border-radius: 4px;
    border: 1px solid rgba(255, 255, 255, 0.1);
    
    .status-label {
      color: rgba(255, 255, 255, 0.6);
      font-weight: 500;
    }
    
    .status-processing {
      color: #ffa500;
      font-weight: 600;
    }
    
    .status-resolved {
      color: #04CEF7;
      font-weight: 600;
    }
    
    .status-pending {
      color: #ff6b6b;
      font-weight: 600;
    }
    
    .status-minor {
      color: #04CEF7;
      font-weight: 600;
    }
    
    .status-medium {
      color: #ffa500;
      font-weight: 600;
    }
    
    .status-severe {
      color: #ff6b6b;
      font-weight: 600;
    }
  }
  
  /* 事故底部样式 */
  .accident-grid-footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-top: 12px;
    border-top: 1px solid rgba(255, 255, 255, 0.15);
    font-size: 11px;
    color: rgba(255, 255, 255, 0.7);
  }
  
  .accident-id {
    font-family: 'Courier New', monospace;
    background: rgba(255, 255, 255, 0.1);
    padding: 3px 6px;
    border-radius: 4px;
    color: rgba(255, 255, 255, 0.8);
    border: 1px solid rgba(255, 255, 255, 0.15);
  }
  
  .accident-action {
    display: flex;
    gap: 8px;
  }
  
  .accident-action-btn {
    background: linear-gradient(135deg, rgba(255, 255, 255, 0.15), rgba(255, 255, 255, 0.08));
    border: 1px solid rgba(255, 255, 255, 0.25);
    color: #fff;
    padding: 5px 10px;
    border-radius: 6px;
    cursor: pointer;
    font-size: 11px;
    font-weight: 500;
    transition: all 0.3s ease;
    backdrop-filter: blur(5px);
    
    &:hover {
      background: linear-gradient(135deg, rgba(255, 255, 255, 0.25), rgba(255, 255, 255, 0.15));
      border-color: rgba(255, 255, 255, 0.4);
      transform: translateY(-1px);
      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
    }
    
    &:active {
      transform: translateY(0);
    }
    align-items: center;
    gap: 5px;
    color: #04CEF7;
    cursor: pointer;
    
    &:hover {
      text-decoration: underline;
    }
    
    .action-icon {
      font-size: 10px;
    }
  }
  
  /* 空状态样式 */
  .no-accidents {
    grid-column: 1 / -1;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 40px 0;
    color: #888;
    text-align: center;
    
    .no-accidents-icon {
      font-size: 48px;
      margin-bottom: 15px;
      opacity: 0.5;
    }
  }
}
</style>
