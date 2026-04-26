package com.swl.aicodehelper.Controller;

import com.swl.aicodehelper.ai.AiCodeHelperService;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class AiController {
    @Resource
    private AiCodeHelperService aiCodeHelperService;

    @GetMapping(value = "/chat",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chat(int memoryId,String userMessage) {
        return aiCodeHelperService.chatStream(memoryId,userMessage);
    }
}
