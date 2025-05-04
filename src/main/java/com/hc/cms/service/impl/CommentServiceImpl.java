package com.hc.cms.service.impl;

import com.hc.cms.dto.request.CommentRequest;
import com.hc.cms.dto.response.CommentResponse;
import com.hc.cms.entity.Comment;
import com.hc.cms.exceptions.CommentNotFoundException;
import com.hc.cms.repository.CommentRepository;
import com.hc.cms.service.CommentService;
import com.hc.cms.utils.CommentUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public ResponseEntity<?> upsertComment(CommentRequest commentRequest) {
        try {
            log.info("Upsert request received for taskId: {}, userId: {}", commentRequest.getTaskId(), commentRequest.getUserId());

            Optional<Comment> existingComment = commentRepository.findByTaskId(commentRequest.getTaskId());
            Comment comment;

            if (existingComment.isPresent()) {
                comment = existingComment.get();
                comment.setId(commentRequest.getId());
                comment.setContent(commentRequest.getContent());
                log.info("Existing comment found for taskId: {}. Updating content.", commentRequest.getTaskId());
            } else {
                comment = Comment.builder()
                        .content(commentRequest.getContent())
                        .taskId(commentRequest.getTaskId())
                        .userId(commentRequest.getUserId())
                        .build();
                log.info("Creating new comment for taskId: {}", commentRequest.getTaskId());
            }

            commentRepository.save(comment);
            log.info("Comment saved successfully for taskId: {}", comment.getTaskId());

            return ResponseEntity.status(HttpStatus.OK).body(CommentUtil.mapToCommentResponse(comment));

        } catch (Exception e) {
            log.error("Failed to save comment: {}, error: {}", commentRequest.getContent(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed saving " + commentRequest.getContent() + " with error " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getCommentByTaskId(String taskId) {
        try {
            log.info("Fetching comment for taskId: {}", taskId);
            List<Comment> comments = commentRepository.findAllByTaskId(taskId);

            List<CommentResponse> commentResponses = comments.stream()
                    .map(CommentUtil::mapToCommentResponse)
                    .toList();

            log.info("Found {} comments for taskId: {}", commentResponses.size(), taskId);
            return ResponseEntity.status(HttpStatus.OK).body(commentResponses);

        } catch (Exception e) {
            log.error("Error retrieving comments for task id: {}, error: {}", taskId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve comments: " + e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> getCommentByCommentId(String commentId) {
        log.info("Fetching comment by commentId: {}", commentId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> {
                    log.warn("Comment not found for id: {}", commentId);
                    return new CommentNotFoundException("Comment not found with given id:" + commentId);
                });

        log.info("Comment retrieved for commentId: {}", commentId);
        return ResponseEntity.status(HttpStatus.OK).body(CommentUtil.mapToCommentResponse(comment));
    }

    @Override
    public ResponseEntity<?> getCommentByUserId(String userId) {
        try {
            log.info("Fetching comments for userId: {}", userId);
            List<Comment> comments = commentRepository.findByUserId(userId);

            List<CommentResponse> commentResponses = comments.stream()
                    .map(CommentUtil::mapToCommentResponse)
                    .toList();

            log.info("Found {} comments for userId: {}", commentResponses.size(), userId);
            return ResponseEntity.status(HttpStatus.OK).body(commentResponses);

        } catch (Exception e) {
            log.error("Error retrieving comments for user id: {}, error: {}", userId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve comments: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteComment(String commentId) {
        try {
            log.info("Attempting to soft delete comment with id: {}", commentId);

            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> {
                        log.warn("Comment not found for deletion with id: {}", commentId);
                        return new CommentNotFoundException("Comment not found for given id:" + commentId);
                    });

            comment.setDeleted(true);
            commentRepository.save(comment);

            log.info("Successfully soft deleted comment with id: {}", commentId);
            return ResponseEntity.status(HttpStatus.OK).body("Comment deleted successfully");

        } catch (Exception e) {
            log.error("Failed to delete comment with id: {}, error: {}", commentId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed deleting comment with error " + e.getMessage());
        }
    }
}
