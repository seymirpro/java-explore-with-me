package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.dto.stats.ViewStatsDto;
import ru.practicum.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsServerRepository extends JpaRepository<EndpointHit, Long> {
    @Query("select new ru.practicum.ewm.dto.stats.ViewStatsDto(s.app, s.uri, count(distinct s.ip))" +
            "from EndpointHit as s " +
            "where s.timestamp between :start and :end " +
            "group by s.app, s.uri " +
            "order by count(distinct(s.ip)) desc")
    List<ViewStatsDto> getStatsByUniqueIp(LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.ewm.dto.stats.ViewStatsDto(s.app, s.uri, count(s.ip))" +
            "from EndpointHit as s " +
            "where s.timestamp between :start and :end " +
            "group by s.app, s.uri " +
            "order by count(s.ip) desc")
    List<ViewStatsDto> getAllStats(LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.ewm.dto.stats.ViewStatsDto(s.app, s.uri, count(distinct s.ip))" +
            "from EndpointHit as s " +
            "where s.timestamp between :start and :end " +
            "and s.uri in :uris " +
            "group by s.app, s.uri " +
            "order by count(distinct(s.ip)) desc")
    List<ViewStatsDto> getStatsByUrisByUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select new ru.practicum.ewm.dto.stats.ViewStatsDto(s.app, s.uri, count(s.ip))" +
            "from EndpointHit as s " +
            "where s.timestamp between :start and :end " +
            "and s.uri in :uris " +
            "group by s.app, s.uri " +
            "order by count(s.ip) desc")
    List<ViewStatsDto> getAllStatsByUris(LocalDateTime start, LocalDateTime end, List<String> uris);
}
