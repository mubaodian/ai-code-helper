package com.swl.aicodehelper.ai;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class qwenChatModelConfig {

    @Value("${langchain4j.community.dashscope.chat-model.api-key}")
    private String dashscopeApiKey;
    @Resource
    private ChatModelListener chatModelListener;

    @Bean
    public ChatModel qwenMaxChatModel() {
        ChatModel qwenChatModel = QwenChatModel.builder()
                .apiKey(dashscopeApiKey)
                .modelName("qwen-max")
                .listeners(List.of(chatModelListener))
                .build();
        return qwenChatModel;
    }
}
