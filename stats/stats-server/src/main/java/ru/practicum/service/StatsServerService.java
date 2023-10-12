package ru.practicum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.stats.EndpointHitDto;
import ru.practicum.ewm.dto.stats.ViewStatsDto;
import ru.practicum.mapper.EndpointHitMapper;
import ru.practicum.model.EndpointHit;
import ru.practicum.repository.StatsServerRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatsServerService {
    private StatsServerRepository statsServerRepository;

    @Autowired
    public StatsServerService(StatsServerRepository statsServerRepository) {
        this.statsServerRepository = statsServerRepository;
    }

    public void addHit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = EndpointHitMapper.fromEndpointDtoToEndpointHit(endpointHitDto);
        endpointHit.setTimestamp(LocalDateTime.now());
        statsServerRepository.save(endpointHit);
    }

    public List<ViewStatsDto> getStats(LocalDateTime start,
                                       LocalDateTime end,
                                       List<String> uris,
                                       boolean unique) {
        if (uris == null) {
            if (unique) {
                return statsServerRepository.getStatsByUniqueIp(start, end);
            } else {
                return statsServerRepository.getAllStats(start, end);
            }
        } else {
            if (unique) {
                return statsServerRepository.getStatsByUrisByUniqueIp(start, end, uris);
            } else {
                return statsServerRepository.getAllStatsByUris(start, end, uris);
            }
        }
    }
}
