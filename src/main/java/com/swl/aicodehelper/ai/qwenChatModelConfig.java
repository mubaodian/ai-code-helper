package com.swl.aicodehelper.ai;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class qwenChatModelConfig {

    @Value("${langchain4j.community.dashscope.chat-model.api-key}")
    private String dashscopeApiKey;

    @Bean
    public ChatModel qwenMaxChatModel() {
        ChatModel qwenChatModel = QwenChatModel.builder()
                .apiKey(dashscopeApiKey)
                .modelName("qwen-max")
                .build();
        return qwenChatModel;
    }
}
