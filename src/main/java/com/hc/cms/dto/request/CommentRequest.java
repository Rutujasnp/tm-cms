package com.hc.cms.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequest {

    private String id;

    @NotNull(message = "User ID is required")
    private String content;

    @NotNull(message = "User ID is required")
    private String taskId;

    private String userId;


}
