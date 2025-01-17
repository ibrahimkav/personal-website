package com.personal.portfolio.dto.about;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AboutRequest {

    @NotBlank(message = "Full name cannot be empty")
    @Size(max = 100, message = "Full name cannot exceed 100 characters")
    private String fullName;

    @NotBlank(message = "Title cannot be empty")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @Size(max = 100, message = "Location cannot exceed 100 characters")
    private String location;

    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;

    @Size(max = 20, message = "Phone cannot exceed 20 characters")
    private String phone;

    @Size(max = 200, message = "LinkedIn URL cannot exceed 200 characters")
    private String linkedinUrl;

    @Size(max = 200, message = "GitHub URL cannot exceed 200 characters")
    private String githubUrl;

    @Size(max = 2000, message = "Summary cannot exceed 2000 characters")
    private String summary;

    @Size(max = 500, message = "Image URL cannot exceed 500 characters")
    private String imageUrl;
} 