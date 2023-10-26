package ru.practicum.comments.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.dto.NewCommentDto;
import ru.practicum.comments.service.CommentService;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/comments")
@RequiredArgsConstructor
public class CommentAdminController {

    private final CommentService commentService;


    @GetMapping("/{commentId}")
    public CommentDto getByIdAdmin(@PathVariable(value = "commentId") Long commentId) {
        return commentService.getCommentByIdAdmin(commentId);
    }


    @PatchMapping("/{commentId}")
    public CommentDto updateAdmin(@PathVariable(value = "commentId") Long commentId,
                                  @Valid @RequestBody NewCommentDto newCommentDto) {
        return commentService.updateCommentAdmin(commentId, newCommentDto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByIdAdmin(@PathVariable(value = "commentId") Long commentId) {
        commentService.deleteCommentByIdAdmin(commentId);
    }

}
