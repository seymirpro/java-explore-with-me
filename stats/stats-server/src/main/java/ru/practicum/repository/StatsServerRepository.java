package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.dto.stats.ViewStatsDto;
import ru.practicum.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsServerRepository extends JpaRepository<EndpointHit, Long> {
    @Query("SELECT new ru.practicum.ewm.dto.stats.ViewStatsDto(e.app, e.uri, COUNT(DISTINCT e.ip)) " +
            "FROM EndpointHit e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "AND (:uris IS NULL OR e.uri IN (:uris)) " +
            "GROUP BY e.app, e.uri")
    List<ViewStatsDto> getStatsUnique(@Param("start") LocalDateTime start,
                                      @Param("end") LocalDateTime end,
                                      @Param("uris") String[] uris);

    @Query("SELECT new ru.practicum.ewm.dto.stats.ViewStatsDto(e.app, e.uri, COUNT(e.ip)) " +
            "FROM EndpointHit e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "AND (:uris IS NULL OR e.uri IN (:uris)) " +
            "GROUP BY e.app, e.uri")
    List<ViewStatsDto> getStatsNonUnique(@Param("start") LocalDateTime start,
                                         @Param("end") LocalDateTime end,
                                         @Param("uris") String[] uris);
}
