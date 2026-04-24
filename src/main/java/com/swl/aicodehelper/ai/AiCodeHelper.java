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

    public String chat(String message){
        ChatResponse chatResponse = qwenMaxChatModel.chat(new UserMessage(message));
        AiMessage aiMessage = chatResponse.aiMessage();
        log.info("AI Response:{}",aiMessage.toString());
        return aiMessage.text();
    }
}
