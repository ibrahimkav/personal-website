package com.personal.portfolio.dto.contact;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String subject;
    private String message;
    private Boolean isRead;
    private Boolean isReplied;
    private String replyMessage;
    private LocalDateTime replyDate;
    private LocalDateTime createdAt;
    private Boolean active;
} 