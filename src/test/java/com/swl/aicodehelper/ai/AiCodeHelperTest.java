package com.swl.aicodehelper.ai;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class AiCodeHelperTest {
    @Resource
    private AiCodeHelper aiCodeHelper;
    @Test
    void chat() {
        String result = aiCodeHelper.chat("你好,我是张三");
    }
}