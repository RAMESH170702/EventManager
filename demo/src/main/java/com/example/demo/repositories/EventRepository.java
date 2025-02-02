package com.example.demo.repositories;

import com.example.demo.models.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByCreatedBy(String userId);
}
