package com.hc.cms.repository;

import com.hc.cms.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends MongoRepository<Comment, String> {

    Optional<Comment> findByTaskId(String taskId);
    List<Comment> findAllByTaskId(String taskId);

    List<Comment> findByUserId(String userId);
}
