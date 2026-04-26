package com.swl.aicodehelper.ai;

import com.swl.aicodehelper.tools.InterviewQuestionTool;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiCodeHelperServiceFactory {
    @Resource
    private ChatModel qwenMaxChatModel;
    @Resource
    private EmbeddingStoreContentRetriever contentRetriever;
    @Resource
    private McpToolProvider toolProvider;

    @Bean
    public AiCodeHelperService aiCodeHelperService() {
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        return AiServices.builder(AiCodeHelperService.class)
                .chatModel(qwenMaxChatModel)
                .chatMemory(chatMemory) //会话记忆
                .contentRetriever(contentRetriever) //RAG检索增强
                .tools(new InterviewQuestionTool()) //工具调用
                .toolProvider(toolProvider) // MCP 工具调用
                .build();
    }
}
