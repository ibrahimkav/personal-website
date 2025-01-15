package com.personal.portfolio.dto.experience;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceRequest {

    @NotBlank(message = "Company name cannot be empty")
    @Size(max = 100, message = "Company name cannot exceed 100 characters")
    private String company;

    @NotBlank(message = "Position cannot be empty")
    @Size(max = 100, message = "Position cannot exceed 100 characters")
    private String position;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Start date cannot be empty")
    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean isCurrent = false;

    @Size(max = 100, message = "Location cannot exceed 100 characters")
    private String location;

    @Size(max = 500, message = "Company URL cannot exceed 500 characters")
    private String companyUrl;

    @Size(max = 500, message = "Company logo URL cannot exceed 500 characters")
    private String companyLogoUrl;

    private Integer order;
} 