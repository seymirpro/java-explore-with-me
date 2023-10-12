package ru.practicum.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.stats.EndpointHitDto;
import ru.practicum.ewm.dto.stats.ViewStatsDto;
import ru.practicum.service.StatsServerService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
public class StatsServerController {
    private StatsServerService statsServerService;

    @Autowired
    public StatsServerController(StatsServerService statsServerService) {
        this.statsServerService = statsServerService;
    }

    @PostMapping("/hit")
    public ResponseEntity<Void> addHit(@RequestBody EndpointHitDto endpointHitDto) {
        statsServerService.addHit(endpointHitDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> getStats(@RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                       @RequestParam() @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                       @RequestParam(required = false) List<String> uris,
                                       @RequestParam(required = false) boolean unique
    ) {
        log.info("start {}", start);
        log.info("end {}", end);
        log.info("uris {}", uris);
        log.info("unique {}", unique);
        return statsServerService.getStats(start, end, uris, unique);
    }
}
