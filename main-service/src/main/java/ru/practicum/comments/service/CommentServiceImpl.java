package ru.practicum.comments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.dto.CommentMapper;
import ru.practicum.comments.dto.NewCommentDto;
import ru.practicum.comments.model.Comment;
import ru.practicum.comments.repository.CommentRepository;
import ru.practicum.events.model.Event;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.handler.NotFoundException;
import ru.practicum.handler.ValidateException;
import ru.practicum.users.model.User;
import ru.practicum.users.repository.UserRepository;
import ru.practicum.util.Pagination;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.comments.dto.CommentMapper.mapToComment;
import static ru.practicum.comments.dto.CommentMapper.mapToCommentDto;
import static ru.practicum.util.enums.EventState.PUBLISHED;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public CommentDto createCommentPrivate(Long userId, Long eventId, NewCommentDto newCommentDto) {
        CommentDto commentDto = CommentMapper.mapToComment(newCommentDto);

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%d hasn't found", eventId)));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%d hasn't  found.", userId)));

        if (!event.getState().equals(PUBLISHED)) {
            throw new ValidateException("Event hasn't published yet.");
        }
        Comment comment = commentRepository.save(mapToComment(user, event, commentDto));

        return mapToCommentDto(comment);
    }

    @Transactional
    @Override
    public CommentDto updateCommentByIdPrivate(Long userId, Long commentId, NewCommentDto newCommentDto) {
        CommentDto commentDto = CommentMapper.mapToComment(newCommentDto);
        Comment comment = commentRepository.findByIdAndAuthorId(commentId, userId)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id= hasn't found.", commentId)));

        comment.setText(commentDto.getText());
        comment.setUpdated(LocalDateTime.now());

        return mapToCommentDto(commentRepository.save(comment));
    }

    @Transactional(readOnly = true)
    @Override
    public CommentDto getCommentByIdPrivate(Long userId, Long commentId) {
        Comment comment = commentRepository.findByIdAndAuthorId(commentId, userId)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id=%d hasn't found.", commentId)));

        return mapToCommentDto(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> getCommentsByEventIdPrivate(Long userId, Long eventId, Integer from, Integer size) {
        eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%d hasn't found", eventId)));

        return commentRepository.findAllByEventId(eventId, new Pagination(from, size, Sort.unsorted()))
                .stream()
                .map(CommentMapper::mapToCommentDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> getCommentsByAuthorIdPrivate(Long userId, Integer from, Integer size) {

        return commentRepository.findAllByAuthorId(userId, new Pagination(from, size, Sort.unsorted()))
                .stream()
                .map(CommentMapper::mapToCommentDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteCommentByIdPrivate(Long userId, Long commentId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format("User with id=%d hasn't found", userId));
        }
        if (!commentRepository.existsById(commentId)) {
            throw new NotFoundException(String.format("Comment with id=%d hasn't found", commentId));
        }
        commentRepository.deleteById(commentId);

    }

    @Transactional(readOnly = true)
    @Override
    public CommentDto getCommentByIdAdmin(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id=%d hasn't  found.", commentId)));
        return mapToCommentDto(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public CommentDto updateCommentAdmin(Long commentId, NewCommentDto newCommentDto) {
        CommentDto commentDto = CommentMapper.mapToComment(newCommentDto);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id=%d hasn't  found.", commentId)));
        comment.setText(commentDto.getText());
        comment.setUpdated(LocalDateTime.now());

        return mapToCommentDto(commentRepository.save(comment));
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> getCommentsPublic(Long eventId, String text, Integer from, Integer size) {

        return commentRepository.findAllEventIdAndByText(eventId, text, new Pagination(from, size, Sort.unsorted())).stream()
                .map(CommentMapper::mapToCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCommentByIdAdmin(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new NotFoundException(String.format("Comment with id=%d hasn't found", commentId));
        }
        commentRepository.deleteById(commentId);
    }
}
