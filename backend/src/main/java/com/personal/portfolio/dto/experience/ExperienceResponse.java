package com.personal.portfolio.dto.experience;

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
    private String company;
    private String position;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isCurrent;
    private String location;
    private String companyUrl;
    private String companyLogoUrl;
    private Integer order;
    private Boolean active;
} 