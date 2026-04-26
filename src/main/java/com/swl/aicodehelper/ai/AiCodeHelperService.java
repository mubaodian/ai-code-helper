package com.swl.aicodehelper.ai;

import com.swl.aicodehelper.guardrail.SafeInputGuardrail;
import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.guardrail.InputGuardrails;

import java.util.List;

@InputGuardrails({SafeInputGuardrail.class})
public interface AiCodeHelperService {
    // 学习报告类
    record Report(String name, List<String> suggestionList){}

    @SystemMessage(fromResource = "system-prompt.txt")
    String chat(String userMessage);

    /**
     * 结构化输出
     * @param userMessage 用户提示词
     * @return 学习报告
     */
    @SystemMessage(fromResource = "system-prompt.txt")
    Report chatForReport(String userMessage);

    /**
     *  Rag检索增强
     * @param userMessage
     * @return
     */
    @SystemMessage(fromResource = "system-prompt.txt")
    Result<String> chatWithRag(String userMessage);
}
