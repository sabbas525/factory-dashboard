package com.factory.repository;

import com.factory.model.ProductionEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductionEventRepository extends JpaRepository<ProductionEvent, Long> {
    List<ProductionEvent> findByLineIdOrderByTimestampDesc(String lineId);
    List<ProductionEvent> findByEventTypeOrderByTimestampDesc(String eventType);
}
