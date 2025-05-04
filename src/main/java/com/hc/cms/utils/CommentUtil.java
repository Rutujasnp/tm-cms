package com.hc.cms.utils;

import com.hc.cms.dto.response.CommentResponse;
import com.hc.cms.entity.Comment;

public class CommentUtil {

    public static CommentResponse mapToCommentResponse(Comment comment){
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .taskId(comment.getTaskId())
                .userId(comment.getUserId())
                .build();
    }
}
