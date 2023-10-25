package ru.practicum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.exeption.ValidationException;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.EndpointHitMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StatService {

    private final StatRepository repository;


    public void saveStat(EndpointHitDto dto) {
        EndpointHit endpointHit = repository.save(EndpointHitMapper.statsHitDtoToStatHit(dto));
        log.info("Save stat {}", endpointHit);
    }


    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (start.isAfter(end)) {
            log.info("End time can't be before start time");
            throw new ValidationException("End time can't be before start time");
        }

        if (uris.isEmpty()) {
            if (unique) {
                log.info("Get all stats with isUnique {} ", unique);
                return repository.getStatsByUniqueIp(start, end);
            } else {
                log.info("Get all stats with isUnique {} ", unique);
                return repository.getAllStats(start, end);
            }
        } else {
            if (unique) {
                log.info("Get all stats with isUnique {} when uris {} ", unique, uris);
                return repository.getStatsByUrisByUniqueIp(start, end, uris);
            } else {
                log.info("Get all stats with isUnique {} when uris {} ", unique, uris);
                return repository.getAllStatsByUris(start, end, uris);
            }
        }
    }
}
