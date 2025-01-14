package com.personal.portfolio.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.portfolio.entity.*;
import com.personal.portfolio.entity.Experience.ExperienceType;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

public class TestUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String convertObjectToJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] convertObjectToJsonBytes(Object object) {
        try {
            return objectMapper.writeValueAsBytes(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseResponse(MvcResult result, Class<T> responseClass) {
        try {
            return objectMapper.readValue(result.getResponse().getContentAsString(), responseClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static User createTestUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("test@example.com");
        user.setFullName("Test User");
        user.setRole(User.Role.ADMIN);
        user.setEmailVerified(true);
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    public static About createTestAbout() {
        About about = new About();
        about.setId(1L);
        about.setFullName("Test User");
        about.setTitle("Software Engineer");
        about.setDescription("Experienced software engineer with a passion for building scalable applications.");
        about.setLocation("Test City, Test Country");
        about.setEmail("test@example.com");
        about.setPhone("+1234567890");
        about.setLinkedinUrl("https://linkedin.com/in/testuser");
        about.setGithubUrl("https://github.com/testuser");
        about.setSummary("Test summary");
        about.setProfileImageUrl("https://example.com/profile.jpg");
        about.setActive(true);
        about.setCreatedAt(LocalDateTime.now());
        about.setUpdatedAt(LocalDateTime.now());
        return about;
    }

    public static Experience createTestExperience() {
        Experience experience = new Experience();
        experience.setId(1L);
        experience.setTitle("Senior Software Engineer");
        experience.setOrganization("Test Company");
        experience.setDescription("Led development of multiple projects.");
        experience.setStartDate(LocalDate.now().minusYears(2));
        experience.setEndDate(LocalDate.now());
        experience.setLocation("Test City, Test Country");
        experience.setType(ExperienceType.WORK);
        experience.setTechnologies("Java, Spring Boot, React");
        experience.setCertificateUrl("https://example.com/certificate.pdf");
        experience.setActive(true);
        experience.setCreatedAt(LocalDateTime.now());
        experience.setUpdatedAt(LocalDateTime.now());
        return experience;
    }

    public static Project createTestProject() {
        Project project = new Project();
        project.setId(1L);
        project.setName("Test Project");
        project.setDescription("A test project description");
        project.setTechnologies("Java, Spring Boot, React");
        project.setGithubUrl("https://github.com/testuser/test-project");
        project.setDemoUrl("https://example.com/demo");
        project.setImageUrl("https://example.com/image.jpg");
        project.setFeatured(false);
        project.setOrder(1);
        project.setActive(true);
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        return project;
    }

    public static Contact createTestContact() {
        Contact contact = new Contact();
        contact.setId(1L);
        contact.setName("Test Contact");
        contact.setEmail("contact@example.com");
        contact.setMessage("Test message content.");
        contact.setRead(false);
        contact.setResponse("Test response content.");
        contact.setIpAddress("127.0.0.1");
        contact.setAdditionalInfo("Test additional info");
        contact.setActive(true);
        contact.setCreatedAt(LocalDateTime.now());
        contact.setUpdatedAt(LocalDateTime.now());
        return contact;
    }
} 