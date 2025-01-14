package com.personal.portfolio.entity;

import com.personal.portfolio.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "projects")
public class Project extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    private String technologies;

    private String githubUrl;

    private String demoUrl;

    private String imageUrl;

    private Boolean featured = false;

    @Column(name = "project_order")
    private Integer order;
} 