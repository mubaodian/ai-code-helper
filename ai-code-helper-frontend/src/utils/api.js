const API_BASE_URL = 'http://localhost:8081/api'

export const chatAPI = {
  chat(memoryId, userMessage, onChunk) {
    return new Promise((resolve, reject) => {
      const eventSource = new EventSource(
        `${API_BASE_URL}/ai/chat?memoryId=${memoryId}&userMessage=${encodeURIComponent(userMessage)}`
      )

      eventSource.onmessage = (event) => {
        onChunk(event.data)
      }

      eventSource.onerror = (error) => {
        eventSource.close()
        // 如果连接已关闭，说明是正常完成，不是错误
        if (eventSource.readyState === EventSource.CLOSED) {
          resolve()
        } else {
          reject(error)
        }
      }
    })
  }
}

export const generateMemoryId = () => {
  return Math.floor(Math.random() * 1000000)
}
