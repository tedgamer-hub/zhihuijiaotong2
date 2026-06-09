# 智慧交通事故检测系统

这是一个智慧交通事故检测系统的前后端项目，包含 Spring Boot 后端和 Vue 3 / Vite 前端。项目面向交通事故数据管理、设备数据接入、可视化大屏展示等场景，适合作为教学、演示和二次开发的基础版本。

## 技术栈

- 后端：Java 8、Spring Boot 2.7、Spring Web、Spring Data JPA、MySQL、Huawei Cloud IoTDA SDK
- 前端：Vue 3、Vite、Vue Router、Element Plus、ECharts、Axios
- 构建工具：Maven、npm

## 目录结构

```text
.
├── SpringbootIntelliDetect/
│   └── IntelliDetect/IntelliDetect/   # Spring Boot 后端主项目
├── zhihuijiaotong_vue3/               # Vue 3 前端主项目
├── README.md
├── LICENSE
└── .gitignore
```

> `SpringbootIntelliDetect/zhihuijiaotong_vue3/` 是旧的重复前端副本，不作为开源主代码发布。

## 后端启动

```bash
cd SpringbootIntelliDetect/IntelliDetect/IntelliDetect
mvn spring-boot:run
```

后端默认端口为 `8080`，接口上下文路径为 `/IntelliDetect`。

运行前需要准备 MySQL 数据库，并通过环境变量配置连接信息：

```bash
DB_URL=jdbc:mysql://127.0.0.1:3306/intellidetect?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8&allowPublicKeyRetrieval=true
DB_USERNAME=root
DB_PASSWORD=your_database_password
```

如果需要接入华为云 IoTDA，请参考 `SpringbootIntelliDetect/IntelliDetect/IntelliDetect/src/main/resources/application.example.properties` 配置相关环境变量。

## 前端启动

```bash
cd zhihuijiaotong_vue3
npm install
npm run dev
```

生产环境接口地址通过 Vite 环境变量配置：

```bash
VITE_API_BASE_URL=https://your-domain.example.com/backend/IntelliDetect
```

本地开发默认使用空 `baseURL`，可通过 Vite 代理或同源后端进行调试。

## 构建

前端构建：

```bash
cd zhihuijiaotong_vue3
npm run build
```

后端编译：

```bash
cd SpringbootIntelliDetect/IntelliDetect/IntelliDetect
mvn test
```

## 开源安全说明

本仓库不会提交真实数据库密码、云服务 AK/SK、七牛云密钥、`.env` 文件、构建产物或依赖目录。请在本地或部署环境中使用环境变量管理敏感配置。

如果历史本地文件中曾包含真实密钥，建议在对应云服务控制台立即轮换旧密钥。
