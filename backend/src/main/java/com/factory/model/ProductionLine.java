package com.factory.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "production_lines")
public class ProductionLine {

    @Id
    private String lineId;
    private String name;
    private String status; // RUNNING, IDLE, MAINTENANCE, ERROR
    private int throughputPerHour;
    private LocalDateTime lastUpdated;

    public ProductionLine() {}

    public ProductionLine(String lineId, String name, String status, int throughputPerHour) {
        this.lineId = lineId;
        this.name = name;
        this.status = status;
        this.throughputPerHour = throughputPerHour;
        this.lastUpdated = LocalDateTime.now();
    }

    public String getLineId() { return lineId; }
    public String getName() { return name; }
    public String getStatus() { return status; }
    public int getThroughputPerHour() { return throughputPerHour; }
    public LocalDateTime getLastUpdated() { return lastUpdated; }

    public void setStatus(String status) { this.status = status; this.lastUpdated = LocalDateTime.now(); }
    public void setThroughputPerHour(int throughputPerHour) { this.throughputPerHour = throughputPerHour; this.lastUpdated = LocalDateTime.now(); }
}
