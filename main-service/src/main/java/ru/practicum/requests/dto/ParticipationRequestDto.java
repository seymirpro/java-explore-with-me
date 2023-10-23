package ru.practicum.requests.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.utils.EventRequestStatus;

import java.time.LocalDateTime;

import static ru.practicum.utils.Constants.DATE_DEFAULT;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class ParticipationRequestDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_DEFAULT)
    private LocalDateTime created;

    private Long event;

    private Long id;

    private Long requester;

    private EventRequestStatus status;
}
