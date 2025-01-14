package com.personal.portfolio.entity;

import com.personal.portfolio.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "experiences")
public class Experience extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String organization;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExperienceType type;

    private String technologies;

    private String certificateUrl;

    public enum ExperienceType {
        WORK,
        EDUCATION
    }
} 