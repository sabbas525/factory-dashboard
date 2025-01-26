package com.factory.controller;

import com.factory.model.ProductionEvent;
import com.factory.model.ProductionLine;
import com.factory.repository.ProductionEventRepository;
import com.factory.repository.ProductionLineRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final ProductionLineRepository lineRepository;
    private final ProductionEventRepository eventRepository;

    public DashboardController(ProductionLineRepository lineRepository,
                               ProductionEventRepository eventRepository) {
        this.lineRepository = lineRepository;
        this.eventRepository = eventRepository;
    }

    @GetMapping("/lines")
    public List<ProductionLine> getAllLines() {
        return lineRepository.findAll();
    }

    @GetMapping("/lines/{lineId}/history")
    public List<ProductionEvent> getLineHistory(@PathVariable String lineId) {
        return eventRepository.findByLineIdOrderByTimestampDesc(lineId);
    }

    @GetMapping("/alerts")
    public List<ProductionEvent> getAlerts() {
        return eventRepository.findByEventTypeOrderByTimestampDesc("ALERT");
    }
}
