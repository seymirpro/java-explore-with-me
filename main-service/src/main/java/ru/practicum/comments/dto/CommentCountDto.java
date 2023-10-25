package ru.practicum.comments.dto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommentCountDto {

    private Long eventId;
    private Long count;

}
