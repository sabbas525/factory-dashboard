package com.factory.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "production_events")
public class ProductionEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lineId;
    private String eventType; // STATUS_CHANGE, THROUGHPUT_UPDATE, ALERT
    private String payload;
    private LocalDateTime timestamp;

    public ProductionEvent() {}

    public ProductionEvent(String lineId, String eventType, String payload) {
        this.lineId = lineId;
        this.eventType = eventType;
        this.payload = payload;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getLineId() { return lineId; }
    public String getEventType() { return eventType; }
    public String getPayload() { return payload; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
