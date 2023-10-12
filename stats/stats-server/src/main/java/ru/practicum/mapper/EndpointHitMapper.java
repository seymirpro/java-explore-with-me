package ru.practicum.mapper;

import ru.practicum.ewm.dto.stats.EndpointHitDto;
import ru.practicum.model.EndpointHit;

public class EndpointHitMapper {
    public static EndpointHit fromEndpointDtoToEndpointHit(EndpointHitDto endpointHitDto) {
        return EndpointHit.builder()
                .uri(endpointHitDto.getUri())
                .app(endpointHitDto.getApp())
                .ip(endpointHitDto.getIp())
                .build();
    }
}
