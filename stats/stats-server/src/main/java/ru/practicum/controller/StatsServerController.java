package ru.practicum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.stats.EndpointHitDto;
import ru.practicum.ewm.dto.stats.ViewStatsDto;
import ru.practicum.service.StatsServerService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
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
    public List<ViewStatsDto> getStats(@RequestParam() LocalDateTime start,
                                       @RequestParam() LocalDateTime end,
                                       @RequestParam(required = false) String[] uris,
                                       @RequestParam(required = false) boolean unique
                                 ){
        return statsServerService.getStats(start, end, uris, unique);
    }
}
