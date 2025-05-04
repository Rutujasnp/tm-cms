package com.hc.cms.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comments")
public class Comment extends AuditableEntity {

    @NotBlank(message = "Comment content cannot be empty")
    private String content;

    @NotNull(message = "Task ID is required")
    @Indexed
    private String taskId;

    @NotNull(message = "User ID is required")
    private String userId;


    // Optional fields
    //private String repliedCommentId;  // Reference to parent comment if this is a reply

    // private Integer upvoteCount;

    //private Integer downvoteCount;

}
