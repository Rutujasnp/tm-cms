package com.hc.cms.controller;

import com.hc.cms.dto.request.CommentRequest;
import com.hc.cms.service.CommentService;
import com.hc.cms.service.impl.CommentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@Tag(name = "Comment Controller", description = "APIs for managing comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // Upsert comment
    @PostMapping
    @Operation(summary = "Create or update a comment")
    public ResponseEntity<?> upsertComment(@Valid @RequestBody CommentRequest commentRequest) {
        return commentService.upsertComment(commentRequest);
    }

    // Get comment by task ID
    @GetMapping("/task/{taskId}")
    @Operation(summary = "Get comment by task ID")
    public ResponseEntity<?> getCommentByTaskId(@PathVariable String taskId) {
        return commentService.getCommentByTaskId(taskId);
    }

    // Get comment by comment ID
    @GetMapping("/{commentId}")
    @Operation(summary = "Get comment by comment ID")
    public ResponseEntity<?> getCommentByCommentId(@PathVariable String commentId) {
        return commentService.getCommentByCommentId(commentId);
    }

    // Get all comments by user ID
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all comments by user ID")
    public ResponseEntity<?> getCommentByUserId(@PathVariable String userId) {
        return commentService.getCommentByUserId(userId);
    }

    // Soft delete comment
    @DeleteMapping("/{commentId}")
    @Operation(summary = "Soft delete comment by ID")
    public ResponseEntity<?> deleteComment(@PathVariable String commentId) {
        return commentService.deleteComment(commentId);
    }
}