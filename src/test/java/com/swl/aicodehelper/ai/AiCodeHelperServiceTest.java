package com.swl.aicodehelper.ai;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
}