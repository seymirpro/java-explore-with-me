package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.stats.EndpointHitDto;
import ru.practicum.ewm.dto.stats.ViewStatsDto;
import ru.practicum.service.StatsServerService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static ru.practicum.ewm.dto.stats.Constants.DATE_TIME_PATTERN;


@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsServerController {

    private final StatsServerService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveStatsHit(@RequestBody @Valid EndpointHitDto endpointHitDto) {
        log.info("Save StatsHit {}", endpointHitDto);
        service.saveStat(endpointHitDto);
    }

    @GetMapping("/stats")
    public Collection<ViewStatsDto> getViewStats(
            @RequestParam(value = "start") @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime start,
            @RequestParam(value = "end") @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime end,
            @RequestParam(value = "uris", defaultValue = "") List<String> uris,
            @RequestParam(value = "unique", defaultValue = "false") Boolean unique
    ) {
        log.info("Get viewed stats with startDate {} endDate {}, uris {} unique {}", start, end, uris, unique);
        return service.getStats(start, end, uris, unique);
    }
}