CREATE TABLE admin (
    id UUID PRIMARY KEY REFERENCES base_user(id)
);

-- Insert initial admin into base_user
INSERT INTO base_user (id, email, password, profile_name, location, about, role)
VALUES (
    'b1a2c3d4-e5f6-4a7b-8c9d-0e1f2a3b4c5d', -- new valid UUID
    'admin@skillspace.com',
    '$2a$10$A/Knays7Stm1EFVDRBf4Pe93ka00/NZaxAhvOc/Y3AUQn17uQocbq', -- bcrypt for 'password123'
    'Admin',
    'Headquarters',
    'Super admin account',
    'ADMIN'
);

-- Insert into admin table
INSERT INTO admin (id)
VALUES ('b1a2c3d4-e5f6-4a7b-8c9d-0e1f2a3b4c5d');