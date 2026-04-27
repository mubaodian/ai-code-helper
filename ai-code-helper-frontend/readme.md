# AI 编程小助手 - 前端项目

## 项目说明

这是一个基于 Vue3 + Vite + Axios 的前端项目，用于与后端 AI 编程小助手服务进行交互。

## 功能特性

- 🎯 聊天室界面，支持实时对话
- 🔄 SSE 流式传输，实时显示 AI 回复
- 💾 自动生成会话 ID，区分不同对话
- 🎨 现代化 UI 设计，响应式布局
- ⌨️ 支持 Enter 快捷发送消息

## 项目结构

```
ai-code-helper-frontend/
├── index.html              # 入口 HTML
├── package.json            # 项目配置
├── vite.config.js          # Vite 配置
├── src/
│   ├── main.js             # 应用入口
│   ├── App.vue             # 根组件
│   ├── components/
│   │   └── ChatRoom.vue    # 聊天室组件
│   ├── utils/
│   │   └── api.js          # API 调用工具
│   └── assets/
│       └── styles.css      # 全局样式
└── readme.md               # 项目说明
```

## 快速开始

### 1. 安装依赖

```bash
npm install
```

### 2. 启动开发服务器

```bash
npm run dev
```

浏览器会自动打开 http://localhost:5173

### 3. 构建生产版本

```bash
npm run build
```

## 配置说明

### 后端接口配置

在 `src/utils/api.js` 中修改 `API_BASE_URL`：

```javascript
const API_BASE_URL = 'http://localhost:8081/api'
```

## 技术栈

- **Vue 3** - 渐进式 JavaScript 框架
- **Vite** - 下一代前端构建工具
- **Axios** - HTTP 客户端库
- **EventSource** - SSE 流式传输

## 核心功能说明

### 聊天流程

1. 页面加载时自动生成唯一的 `memoryId`
2. 用户输入消息并发送
3. 前端通过 EventSource 建立 SSE 连接
4. 后端通过 SSE 流式返回 AI 回复
5. 前端实时显示 AI 回复内容

### SSE 连接

使用原生 `EventSource` API 建立 SSE 连接，自动处理流式数据接收和错误处理。

## 浏览器兼容性

- Chrome/Edge 最新版本
- Firefox 最新版本
- Safari 最新版本

## 注意事项

- 确保后端服务运行在 `http://localhost:8081`
- 后端需要支持 CORS 跨域请求
- SSE 连接需要后端返回 `Content-Type: text/event-stream`
