package com.hc.cms.service;

import com.hc.cms.dto.request.CommentRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface CommentService {


    ResponseEntity<?> upsertComment(@Valid CommentRequest commentRequest);

    ResponseEntity<?> getCommentByTaskId(String taskId);

    ResponseEntity<?> getCommentByCommentId(String commentId);

    ResponseEntity<?> getCommentByUserId(String userId);

    ResponseEntity<?> deleteComment(String commentId);
}
