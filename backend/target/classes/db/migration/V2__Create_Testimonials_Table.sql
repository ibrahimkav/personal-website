CREATE TABLE testimonials (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    linkedin_profile_url VARCHAR(200),
    profile_image_url VARCHAR(500),
    company VARCHAR(100),
    position VARCHAR(100),
    is_approved BOOLEAN DEFAULT FALSE,
    display_order INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (user_id) REFERENCES users(id)
); 