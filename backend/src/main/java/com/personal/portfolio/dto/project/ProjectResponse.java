package com.personal.portfolio.dto.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {
    private Long id;
    private String name;
    private String description;
    private String technologies;
    private String githubUrl;
    private String demoUrl;
    private String imageUrl;
    private Boolean featured;
    private Integer order;
    private Boolean active;
} 