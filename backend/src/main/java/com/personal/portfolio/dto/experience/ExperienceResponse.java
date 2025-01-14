package com.personal.portfolio.dto.experience;

import com.personal.portfolio.entity.Experience.ExperienceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceResponse {
    private Long id;
    private String title;
    private String organization;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private ExperienceType type;
    private String technologies;
    private String certificateUrl;
} 