package com.swl.aicodehelper.ai;

import dev.langchain4j.rag.content.Content;
import dev.langchain4j.service.Result;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AiCodeHelperServiceTest {
    @Resource
    private AiCodeHelperService aiCodeHelperService;

    @Test
    void chat() {
        String result = aiCodeHelperService.chat("你好，我叫张三");
        assertNotNull(result);
    }

    @Test
    void chatWithMemory(){
        String result = aiCodeHelperService.chat("你好，我叫张三");
        System.out.println(result);
        result = aiCodeHelperService.chat("我是谁来着");
        System.out.println(result);
    }

    @Test
    void chatForReport() {
        String message = "你好，我是张三，学习编程两年半，请你帮我制定学习计划";
        AiCodeHelperService.Report result = aiCodeHelperService.chatForReport(message);
        assertNotNull(result);
    }

    @Test
    void chatWithRag() {
        String message = "专门学习Java？有哪些常见面试题";
        Result<String> result = aiCodeHelperService.chatWithRag(message);
        String content = result.content();
        List<Content> sources = result.sources(); // RAG引用的资源
        System.out.println(content);
        System.out.println(sources);
    }

    @Test
    void chatWithTools(){
        String result = aiCodeHelperService.chat("有哪些常见的计算机网络面试题？");
        System.out.println(result);
    }
}