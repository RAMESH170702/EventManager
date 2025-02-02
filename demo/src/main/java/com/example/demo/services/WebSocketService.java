package com.example.demo.services;

import com.example.demo.models.Event;
import com.example.demo.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private EventRepository eventRepository;

    public void updateAttendees(String eventId, String userId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);

        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            if (!event.getAttendees().contains(userId)) {
                event.getAttendees().add(userId);
                eventRepository.save(event);
            }
            messagingTemplate.convertAndSend("/topic/event/" + eventId, event.getAttendees().size());
        }
    }

}
