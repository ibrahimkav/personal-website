package com.personal.portfolio.dto.contact;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactReplyRequest {

    @NotBlank(message = "Reply message cannot be empty")
    @Size(max = 1000, message = "Reply message must be less than 1000 characters")
    private String replyMessage;
} 