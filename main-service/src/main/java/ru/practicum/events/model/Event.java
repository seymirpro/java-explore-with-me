package ru.practicum.events.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.category.model.Category;
import ru.practicum.locations.model.Location;
import ru.practicum.users.model.User;
import ru.practicum.util.enums.EventState;

import javax.persistence.*;
import java.time.LocalDateTime;

import static ru.practicum.util.Constants.DATE_DEFAULT;
import static ru.practicum.util.enums.EventState.PENDING;

@Entity
@Table(name = "events")
@Getter
@Setter
@ToString
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(nullable = false, length = 2000)
    private String annotation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false, length = 7000)
    private String description;

    @Column(name = "event_date", nullable = false)
    @DateTimeFormat(pattern = DATE_DEFAULT)
    private LocalDateTime eventDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    @ToString.Exclude
    private Location location;

    @Column(nullable = false)
    private Boolean paid;

    @Column(name = "participant_limit", nullable = false)
    private Integer participantLimit;

    @Transient
    private Long confirmedRequests = 0L;

    @Column(name = "request_moderation", nullable = false)
    private Boolean requestModeration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id", nullable = false)
    private User initiator;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private EventState state = PENDING;

    @Column(name = "created_on", nullable = false)
    @DateTimeFormat(pattern = DATE_DEFAULT)
    private LocalDateTime createdOn = LocalDateTime.now();

    @Column(name = "published_on")
    @DateTimeFormat(pattern = DATE_DEFAULT)
    private LocalDateTime publishedOn;
}