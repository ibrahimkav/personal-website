package com.personal.portfolio.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.personal.portfolio.entity.*;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
    }

    public static String convertObjectToJsonString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseResponse(MvcResult result, Class<T> responseClass) {
        try {
            return mapper.readValue(result.getResponse().getContentAsString(), responseClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static User createTestUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("test@example.com");
        user.setFullName("Test User");
        user.setRole(User.Role.USER);
        user.setEmailVerified(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setActive(true);
        return user;
    }

    public static About createTestAbout() {
        About about = new About();
        about.setFullName("John Doe");
        about.setTitle("Software Engineer");
        about.setDescription("Experienced software engineer with a passion for building scalable applications");
        about.setLocation("Istanbul, Turkey");
        about.setEmail("john@example.com");
        about.setPhone("+90 555 123 4567");
        about.setLinkedinUrl("https://linkedin.com/in/johndoe");
        about.setGithubUrl("https://github.com/johndoe");
        about.setSummary("Full-stack developer with expertise in Java and React");
        about.setProfileImageUrl("https://example.com/profile.jpg");
        about.setCreatedAt(LocalDateTime.now());
        about.setUpdatedAt(LocalDateTime.now());
        about.setActive(true);
        return about;
    }

    public static Experience createTestExperience() {
        Experience experience = new Experience();
        experience.setCompany("Tech Corp");
        experience.setPosition("Senior Software Engineer");
        experience.setDescription("Led development of microservices architecture");
        experience.setStartDate(LocalDate.now().minusYears(2));
        experience.setEndDate(LocalDate.now());
        experience.setLocation("Istanbul, Turkey");
        experience.setIsCurrent(false);
        experience.setCompanyUrl("https://example.com");
        experience.setCompanyLogoUrl("https://example.com/logo.png");
        experience.setOrder(1);
        experience.setCreatedAt(LocalDateTime.now());
        experience.setUpdatedAt(LocalDateTime.now());
        experience.setActive(true);
        return experience;
    }

    public static Project createTestProject() {
        Project project = new Project();
        project.setName("Portfolio Website");
        project.setDescription("Personal portfolio website built with Spring Boot and React");
        project.setTechnologies("Java, Spring Boot, React");
        project.setGithubUrl("https://github.com/johndoe/portfolio");
        project.setDemoUrl("https://portfolio.johndoe.com");
        project.setImageUrl("https://example.com/portfolio.jpg");
        project.setFeatured(true);
        project.setOrder(1);
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        project.setActive(true);
        return project;
    }

    public static Contact createTestContact() {
        Contact contact = new Contact();
        contact.setName("Jane Smith");
        contact.setEmail("jane@example.com");
        contact.setSubject("Project Opportunity");
        contact.setMessage("I would like to discuss a project opportunity");
        contact.setIsRead(false);
        contact.setIsReplied(false);
        contact.setReplyMessage("Thank you for your message. I will get back to you soon.");
        contact.setReplyDate(LocalDateTime.now());
        contact.setCreatedAt(LocalDateTime.now());
        contact.setUpdatedAt(LocalDateTime.now());
        contact.setActive(true);
        return contact;
    }
} 