package com.campuschatbot.controller;

import com.campuschatbot.model.ChatResponse;
import com.campuschatbot.service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:8080")
public class ChatbotController {

    @Autowired
    private ChatbotService chatbotService;

    @PostMapping
    public ChatResponse getChatResponse(@RequestBody ChatRequest request) {
        String response = chatbotService.getResponse(request.getMessage(), request.getUserId());
        return new ChatResponse(response);
    }
}

class ChatRequest {
    private String message;
    private String userId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}