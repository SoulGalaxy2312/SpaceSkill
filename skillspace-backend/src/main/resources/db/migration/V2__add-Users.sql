-- Migration script to add initial users to the database
-- Password = password123
INSERT INTO users (id, email, password, profile_name, location, role) VALUES
    ('11111111-2222-4333-8444-555555555555'::uuid, 'user1@test.com', '$2a$10$A/Knays7Stm1EFVDRBf4Pe93ka00/NZaxAhvOc/Y3AUQn17uQocbq', 'User One', 'New York', 'USER'),
    ('22222222-3333-4444-8555-666666666666'::uuid, 'user2@test.com', '$2a$10$A/Knays7Stm1EFVDRBf4Pe93ka00/NZaxAhvOc/Y3AUQn17uQocbq', 'User Two', 'San Francisco', 'USER'),
    ('33333333-4444-4555-8666-777777777777'::uuid, 'user3@test.com', '$2a$10$A/Knays7Stm1EFVDRBf4Pe93ka00/NZaxAhvOc/Y3AUQn17uQocbq', 'User Three', 'Seattle', 'USER');