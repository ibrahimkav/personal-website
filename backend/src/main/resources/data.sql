-- Initial admin user (password: admin123)
MERGE INTO users (id, username, password, email, full_name, role, email_verified, active, created_at, updated_at)
KEY(username)
VALUES (1, 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'admin@example.com', 'Admin User', 'ADMIN', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- About data
MERGE INTO about (id, full_name, title, description, location, email, phone, linkedin_url, github_url, summary, profile_image_url, created_at, updated_at, active)
KEY(id)
VALUES (1, 'Ahmet Yılmaz', 'Senior Full Stack Developer', 
'Tutkulu ve deneyimli bir yazılım geliştirici', 
'İstanbul, Türkiye', 
'ahmet@example.com', 
'+90 532 123 4567', 
'https://linkedin.com/in/ahmetyilmaz', 
'https://github.com/ahmetyilmaz', 
'8 yıllık deneyime sahip, modern web teknolojilerinde uzmanlaşmış full stack developer. Mikroservis mimarileri, cloud computing ve DevOps pratiklerinde güçlü bilgi birikimine sahibim.',
'https://example.com/profile.jpg',
CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);

-- Experiences data
MERGE INTO experiences (id, position, company, description, start_date, end_date, location, is_current, company_url, company_logo_url, experience_order, created_at, updated_at, active)
KEY(id)
VALUES 
(1, 'Senior Software Engineer', 'Teknoloji A.Ş.', 'Mikroservis tabanlı e-ticaret platformunun geliştirilmesi ve yönetimi', '2021-01-01', NULL, 'İstanbul', true, 'https://teknoloji.com.tr', 'https://teknoloji.com.tr/logo.png', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
(2, 'Full Stack Developer', 'Yazılım Ltd.', 'Fintech uygulamalarının geliştirilmesi ve bakımı', '2018-06-01', '2020-12-31', 'Ankara', false, 'https://yazilim.com.tr', 'https://yazilim.com.tr/logo.png', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
(3, 'Backend Developer', 'Digital Corp', 'REST API geliştirme ve veritabanı optimizasyonu', '2016-03-01', '2018-05-31', 'İzmir', false, 'https://digital.com.tr', 'https://digital.com.tr/logo.png', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);

-- Projects data
MERGE INTO projects (id, name, description, technologies, github_url, demo_url, image_url, featured, project_order, created_at, updated_at, active)
KEY(id)
VALUES 
(1, 'E-Ticaret Platformu', 'Modern teknolojiler kullanılarak geliştirilmiş kapsamlı bir e-ticaret platformu', 'Spring Boot, React, PostgreSQL, Redis, Docker', 'https://github.com/ahmetyilmaz/ecommerce', 'https://ecommerce-demo.com', 'https://example.com/ecommerce.jpg', true, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
(2, 'Blog Sistemi', 'SEO dostu, yüksek performanslı blog platformu', 'Node.js, Vue.js, MongoDB, Elasticsearch', 'https://github.com/ahmetyilmaz/blog', 'https://blog-demo.com', 'https://example.com/blog.jpg', true, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
(3, 'Task Manager', 'Gerçek zamanlı görev yönetim uygulaması', 'Spring Boot, Angular, MySQL, WebSocket', 'https://github.com/ahmetyilmaz/taskmanager', 'https://taskmanager-demo.com', 'https://example.com/taskmanager.jpg', false, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);

-- Contacts data
MERGE INTO contacts (id, name, email, phone, subject, message, is_read, is_replied, reply_message, reply_date, created_at, updated_at, active)
KEY(id)
VALUES 
(1, 'Mehmet Demir', 'mehmet@example.com', '+90 555 111 2233', 'Proje Detayları Hakkında', 'Projeniz hakkında detaylı bilgi almak istiyorum.', true, true, 'Teşekkürler, detayları mail olarak ilettim.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
(2, 'Ayşe Yıldız', 'ayse@example.com', '+90 555 222 3344', 'İş Birliği Teklifi', 'İş birliği teklifim var, görüşebilir miyiz?', false, false, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
(3, 'Can Özkan', 'can@example.com', '+90 555 333 4455', 'Blog Projesi Teknolojileri', 'Blog projenizde kullandığınız teknolojiler hakkında bilgi alabilir miyim?', false, false, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true); 