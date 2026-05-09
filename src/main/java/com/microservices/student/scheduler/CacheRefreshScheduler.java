package com.microservices.student.scheduler;

import com.microservices.student.service.DepartmentCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CacheRefreshScheduler {

    @Autowired
    private DepartmentCacheService cacheService;

    // Load on startup
    @EventListener(ApplicationReadyEvent.class)
    public void loadOnStartup() {
        log.info("Loading department cache on startup...");
        cacheService.refreshValidCodes();
    }

    // Refresh every 30 minutes
    @Scheduled(fixedRate = 1800000)
    public void scheduledRefresh() {
        log.info("Scheduled cache refresh...");
        cacheService.refreshValidCodes();
    }
}