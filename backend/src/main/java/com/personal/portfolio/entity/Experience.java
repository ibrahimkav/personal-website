package com.personal.portfolio.entity;

import com.personal.portfolio.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "experiences")
@EqualsAndHashCode(callSuper = true)
public class Experience extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String company;

    @Column(nullable = false, length = 100)
    private String position;

    @Column(length = 1000)
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_current")
    private Boolean isCurrent = false;

    @Column(length = 100)
    private String location;

    @Column(name = "company_url", length = 500)
    private String companyUrl;

    @Column(name = "company_logo_url", length = 500)
    private String companyLogoUrl;

    @Column(name = "experience_order")
    private Integer order;
} 