package com.personal.portfolio.entity;

import com.personal.portfolio.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contacts")
@EqualsAndHashCode(callSuper = true)
public class Contact extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(nullable = false, length = 200)
    private String subject;

    @Column(nullable = false, length = 1000)
    private String message;

    @Column(name = "is_read")
    private Boolean isRead = false;

    @Column(name = "is_replied")
    private Boolean isReplied = false;

    @Column(name = "reply_message", length = 1000)
    private String replyMessage;

    @Column(name = "reply_date")
    private java.time.LocalDateTime replyDate;
} 