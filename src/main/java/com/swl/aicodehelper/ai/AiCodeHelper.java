package com.swl.aicodehelper.ai;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AiCodeHelper {
    @Resource
    private ChatModel qwenMaxChatModel;

    /**
     * 简单对话
     * @param message
     * @return
     */
    public String chat(String message){
        ChatResponse chatResponse = qwenMaxChatModel.chat(new UserMessage(message));
        AiMessage aiMessage = chatResponse.aiMessage();
        log.info("AI Response:{}",aiMessage.toString());
        return aiMessage.text();
    }

    /**
     * 简单对话-自定义用户消息(多模态)
     * @param
     * @return
     */
    public String chatWithMessage(UserMessage userMessage){
        ChatResponse chatResponse = qwenMaxChatModel.chat(userMessage);
        AiMessage aiMessage = chatResponse.aiMessage();
        log.info("AI Response:{}",aiMessage.toString());
        return aiMessage.text();
    }
}
