package ru.practicum.ewm.dto.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EndpointHitDto {
    private String app;
    private String uri;
    private String ip;
}
