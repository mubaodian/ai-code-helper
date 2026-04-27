<template>
  <div class="chat-room">
    <div class="chat-header">
      <h1>AI 编程小助手</h1>
      <p class="session-id">会话 ID: {{ memoryId }}</p>
    </div>

    <div class="chat-messages" ref="messagesContainer">
      <div
        v-for="(message, index) in messages"
        :key="index"
        :class="['message', message.role]"
      >
        <div v-if="message.role === 'user'" class="message-content">
          {{ message.content }}
        </div>
        <div v-else class="message-content markdown-content" v-html="renderMarkdown(message.content)"></div>
      </div>
    </div>

    <div class="chat-input-area">
      <div class="input-wrapper">
        <input
          v-model="userInput"
          type="text"
          placeholder="请输入你的问题..."
          @keyup.enter="sendMessage"
          :disabled="isLoading"
          class="input-field"
        />
        <button
          @click="sendMessage"
          :disabled="isLoading || !userInput.trim()"
          class="send-btn"
        >
          {{ isLoading ? '发送中...' : '发送' }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick, computed } from 'vue'
import { marked } from 'marked'
import { chatAPI, generateMemoryId } from '../utils/api'

const memoryId = ref(null)
const messages = ref([])
const userInput = ref('')
const isLoading = ref(false)
const messagesContainer = ref(null)

onMounted(() => {
  memoryId.value = generateMemoryId()
})

const scrollToBottom = async () => {
  await nextTick()
  if (messagesContainer.value) {
    messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
  }
}

const renderMarkdown = (content) => {
  return marked(content)
}

const sendMessage = async () => {
  if (!userInput.value.trim() || isLoading.value) return

  const userMessage = userInput.value.trim()
  userInput.value = ''

  messages.value.push({
    role: 'user',
    content: userMessage
  })

  await scrollToBottom()

  isLoading.value = true
  messages.value.push({
    role: 'ai',
    content: ''
  })

  try {
    await chatAPI.chat(memoryId.value, userMessage, (chunk) => {
      const lastMessage = messages.value[messages.value.length - 1]
      if (lastMessage && lastMessage.role === 'ai') {
        lastMessage.content += chunk
        scrollToBottom()
      }
    })
  } catch (error) {
    console.error('Chat error:', error)
    const lastMessage = messages.value[messages.value.length - 1]
    if (lastMessage && lastMessage.role === 'ai') {
      lastMessage.content = '抱歉，发生了错误。请稍后重试。'
    }
  } finally {
    isLoading.value = false
    await scrollToBottom()
  }
}
</script>

<style scoped>
.chat-room {
  display: flex;
  flex-direction: column;
  height: 100vh;
  background: white;
}

.chat-header {
  padding: 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  text-align: center;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.chat-header h1 {
  font-size: 24px;
  margin-bottom: 5px;
}

.session-id {
  font-size: 12px;
  opacity: 0.9;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.message {
  display: flex;
  animation: slideIn 0.3s ease-out;
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.message.user {
  justify-content: flex-end;
}

.message.ai {
  justify-content: flex-start;
}

.message-content {
  max-width: 70%;
  padding: 12px 16px;
  border-radius: 12px;
  word-wrap: break-word;
  white-space: pre-wrap;
  line-height: 1.5;
}

.message.user .message-content {
  background: #667eea;
  color: white;
  border-bottom-right-radius: 4px;
}

.message.ai .message-content {
  background: #f0f0f0;
  color: #333;
  border-bottom-left-radius: 4px;
}

/* Markdown 样式 */
.markdown-content {
  padding: 12px 16px !important;
}

.markdown-content h1,
.markdown-content h2,
.markdown-content h3,
.markdown-content h4,
.markdown-content h5,
.markdown-content h6 {
  margin: 12px 0 8px 0;
  font-weight: 600;
  line-height: 1.4;
}

.markdown-content h1 {
  font-size: 20px;
  border-bottom: 2px solid #ddd;
  padding-bottom: 8px;
}

.markdown-content h2 {
  font-size: 18px;
}

.markdown-content h3 {
  font-size: 16px;
}

.markdown-content p {
  margin: 8px 0;
  line-height: 1.6;
}

.markdown-content ul,
.markdown-content ol {
  margin: 8px 0;
  padding-left: 24px;
}

.markdown-content li {
  margin: 4px 0;
  line-height: 1.6;
}

.markdown-content code {
  background: #f5f5f5;
  padding: 2px 6px;
  border-radius: 3px;
  font-family: 'Courier New', monospace;
  font-size: 13px;
  color: #d63384;
}

.markdown-content pre {
  background: #2d2d2d;
  color: #f8f8f2;
  padding: 12px;
  border-radius: 6px;
  overflow-x: auto;
  margin: 8px 0;
  line-height: 1.4;
}

.markdown-content pre code {
  background: none;
  color: inherit;
  padding: 0;
  border-radius: 0;
}

.markdown-content blockquote {
  border-left: 4px solid #667eea;
  padding-left: 12px;
  margin: 8px 0;
  color: #666;
  font-style: italic;
}

.markdown-content a {
  color: #667eea;
  text-decoration: none;
}

.markdown-content a:hover {
  text-decoration: underline;
}

.markdown-content table {
  border-collapse: collapse;
  margin: 8px 0;
  width: 100%;
}

.markdown-content table th,
.markdown-content table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

.markdown-content table th {
  background: #f5f5f5;
  font-weight: 600;
}

.markdown-content hr {
  border: none;
  border-top: 2px solid #ddd;
  margin: 12px 0;
}

.chat-input-area {
  padding: 20px;
  background: #f9f9f9;
  border-top: 1px solid #e0e0e0;
}

.input-wrapper {
  display: flex;
  gap: 10px;
  max-width: 100%;
}

.input-field {
  flex: 1;
  padding: 12px 16px;
  border: 1px solid #ddd;
  border-radius: 24px;
  font-size: 14px;
  outline: none;
  transition: border-color 0.3s;
}

.input-field:focus {
  border-color: #667eea;
}

.input-field:disabled {
  background: #f5f5f5;
  cursor: not-allowed;
}

.send-btn {
  padding: 12px 24px;
  background: #667eea;
  color: white;
  border: none;
  border-radius: 24px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: background 0.3s;
}

.send-btn:hover:not(:disabled) {
  background: #5568d3;
}

.send-btn:disabled {
  background: #ccc;
  cursor: not-allowed;
}

/* 滚动条美化 */
.chat-messages::-webkit-scrollbar {
  width: 6px;
}

.chat-messages::-webkit-scrollbar-track {
  background: transparent;
}

.chat-messages::-webkit-scrollbar-thumb {
  background: #ddd;
  border-radius: 3px;
}

.chat-messages::-webkit-scrollbar-thumb:hover {
  background: #999;
}
</style>
