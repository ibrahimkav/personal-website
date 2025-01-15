package com.personal.portfolio.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {

    @NotBlank(message = "Project name cannot be empty")
    @Size(max = 100, message = "Project name cannot exceed 100 characters")
    private String name;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @Size(max = 500, message = "Technologies cannot exceed 500 characters")
    private String technologies;

    @Size(max = 500, message = "GitHub URL cannot exceed 500 characters")
    private String githubUrl;

    @Size(max = 500, message = "Demo URL cannot exceed 500 characters")
    private String demoUrl;

    @Size(max = 500, message = "Image URL cannot exceed 500 characters")
    private String imageUrl;

    private Boolean featured = false;

    private Integer order;
} 