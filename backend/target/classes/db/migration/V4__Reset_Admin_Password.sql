-- Reset admin password to 'admin123' with a known working hash
UPDATE users 
SET password = '$2a$10$PH0p6pE5sOBwABXqXHXyGOq9aXlAL5kGa.nUY6tVvMgL1pR1OY0Aq' 
WHERE username = 'admin'; 