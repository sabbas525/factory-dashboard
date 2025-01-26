package com.factory.simulator;

import com.factory.model.ProductionEvent;
import com.factory.model.ProductionLine;
import com.factory.repository.ProductionEventRepository;
import com.factory.repository.ProductionLineRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductionSimulator {

    private final ProductionLineRepository lineRepository;
    private final ProductionEventRepository eventRepository;
    private final SimpMessagingTemplate messaging;
    private final Random random = new Random();

    private static final String[] STATUSES = {"RUNNING", "IDLE", "MAINTENANCE", "ERROR"};

    public ProductionSimulator(ProductionLineRepository lineRepository,
                               ProductionEventRepository eventRepository,
                               SimpMessagingTemplate messaging) {
        this.lineRepository = lineRepository;
        this.eventRepository = eventRepository;
        this.messaging = messaging;
    }

    @PostConstruct
    public void initLines() {
        if (lineRepository.count() == 0) {
            lineRepository.saveAll(List.of(
                new ProductionLine("LINE-01", "Assembly Line A", "RUNNING", 120),
                new ProductionLine("LINE-02", "Assembly Line B", "RUNNING", 95),
                new ProductionLine("LINE-03", "Packaging Line", "IDLE", 0),
                new ProductionLine("LINE-04", "Quality Control", "RUNNING", 200)
            ));
        }
    }

    @Scheduled(fixedRate = 3000)
    public void simulateEvents() {
        List<ProductionLine> lines = lineRepository.findAll();
        if (lines.isEmpty()) return;

        ProductionLine line = lines.get(random.nextInt(lines.size()));
        int eventChoice = random.nextInt(10);

        if (eventChoice < 5) {
            // Throughput update
            int newThroughput = line.getStatus().equals("RUNNING") ? 80 + random.nextInt(80) : 0;
            line.setThroughputPerHour(newThroughput);
            lineRepository.save(line);

            ProductionEvent event = new ProductionEvent(line.getLineId(), "THROUGHPUT_UPDATE",
                    "{\"throughput\": %d}".formatted(newThroughput));
            eventRepository.save(event);
            messaging.convertAndSend("/topic/production-events", Map.of(
                "type", "THROUGHPUT_UPDATE", "lineId", line.getLineId(), "throughput", newThroughput));

        } else if (eventChoice < 8) {
            // Status change
            String newStatus = STATUSES[random.nextInt(STATUSES.length)];
            line.setStatus(newStatus);
            if (!newStatus.equals("RUNNING")) line.setThroughputPerHour(0);
            lineRepository.save(line);

            ProductionEvent event = new ProductionEvent(line.getLineId(), "STATUS_CHANGE",
                    "{\"status\": \"%s\"}".formatted(newStatus));
            eventRepository.save(event);
            messaging.convertAndSend("/topic/production-events", Map.of(
                "type", "STATUS_CHANGE", "lineId", line.getLineId(), "status", newStatus));

        } else {
            // Alert
            ProductionEvent event = new ProductionEvent(line.getLineId(), "ALERT",
                    "{\"message\": \"Line %s requires attention\"}".formatted(line.getName()));
            eventRepository.save(event);
            messaging.convertAndSend("/topic/production-events", Map.of(
                "type", "ALERT", "lineId", line.getLineId(), "message", "Line " + line.getName() + " requires attention"));
        }
    }
}
