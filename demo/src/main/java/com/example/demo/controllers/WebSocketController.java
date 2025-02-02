package com.example.demo.controllers;

import com.example.demo.services.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

    @Autowired
    private WebSocketService webSocketService;

    @MessageMapping("/event/join")
    @SendTo("/topic/event")
    public void joinEvent(String eventId, String userId) {
        webSocketService.updateAttendees(eventId, userId);
    }
}
