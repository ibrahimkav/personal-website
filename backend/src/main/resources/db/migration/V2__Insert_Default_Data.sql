-- Default admin user (password: admin123)
INSERT INTO users (username, password, email, full_name, role, email_verified, created_at, updated_at, active)
VALUES ('admin', '$2a$10$PH0p6pE5sOBwABXqXHXyGOq9aXlAL5kGa.nUY6tVvMgL1pR1OY0Aq', 'admin@example.com', 'Admin User', 'ADMIN', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);

-- Sample about data
INSERT INTO about (full_name, title, description, location, email, phone, linkedin_url, github_url, summary, created_at, updated_at, active)
VALUES ('John Doe', 'Full Stack Developer', 'Passionate software developer with expertise in web technologies', 'Istanbul, Turkey', 'john@example.com', '+90 555 123 4567', 'https://linkedin.com/in/johndoe', 'https://github.com/johndoe', 'Experienced software developer with a strong background in full-stack development...', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);

-- Sample experiences
INSERT INTO experiences (position, company, description, start_date, end_date, location, is_current, company_url, company_logo_url, experience_order, created_at, updated_at, active)
VALUES 
('Senior Developer', 'Tech Corp', 'Led development of various web applications', '2020-01-01', NULL, 'Istanbul', true, 'https://techcorp.com', 'https://techcorp.com/logo.png', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('Software Engineer', 'Innovation Inc', 'Developed and maintained enterprise applications', '2018-01-01', '2019-12-31', 'Ankara', false, 'https://innovation.com', 'https://innovation.com/logo.png', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('Computer Engineering', 'Tech University', 'Bachelor''s degree in Computer Engineering', '2014-09-01', '2018-06-30', 'Istanbul', false, 'https://techuniversity.edu', 'https://techuniversity.edu/logo.png', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);

-- Sample projects
INSERT INTO projects (name, description, technologies, github_url, demo_url, featured, project_order, created_at, updated_at, active)
VALUES 
('E-Commerce Platform', 'A full-featured e-commerce platform with modern technologies', 'Spring Boot, Angular, PostgreSQL', 'https://github.com/johndoe/ecommerce', 'https://demo-ecommerce.example.com', true, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
('Task Management System', 'Project management tool with real-time updates', 'Spring Boot, React, MongoDB', 'https://github.com/johndoe/task-manager', 'https://demo-tasks.example.com', true, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true); 