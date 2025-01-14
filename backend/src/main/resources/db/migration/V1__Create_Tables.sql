-- Users table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    full_name VARCHAR(100),
    role VARCHAR(20) NOT NULL,
    email_verified BOOLEAN DEFAULT FALSE,
    reset_password_token VARCHAR(100),
    verification_token VARCHAR(100),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    active BOOLEAN DEFAULT TRUE
);

-- About table
CREATE TABLE about (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(1000),
    location VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    linkedin_url VARCHAR(200),
    github_url VARCHAR(200),
    summary VARCHAR(2000),
    profile_image_url VARCHAR(500),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    active BOOLEAN DEFAULT TRUE
);

-- Experiences table
CREATE TABLE experiences (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    organization VARCHAR(100) NOT NULL,
    description VARCHAR(1000),
    start_date DATE NOT NULL,
    end_date DATE,
    location VARCHAR(100),
    type VARCHAR(20) NOT NULL,
    technologies VARCHAR(500),
    certificate_url VARCHAR(500),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    active BOOLEAN DEFAULT TRUE
);

-- Projects table
CREATE TABLE projects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(1000),
    technologies VARCHAR(500),
    github_url VARCHAR(500),
    demo_url VARCHAR(500),
    image_url VARCHAR(500),
    featured BOOLEAN DEFAULT FALSE,
    project_order INT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    active BOOLEAN DEFAULT TRUE
);

-- Contacts table
CREATE TABLE contacts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    message VARCHAR(1000) NOT NULL,
    read BOOLEAN DEFAULT FALSE,
    response VARCHAR(1000),
    ip_address VARCHAR(50),
    additional_info VARCHAR(500),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    active BOOLEAN DEFAULT TRUE
); 