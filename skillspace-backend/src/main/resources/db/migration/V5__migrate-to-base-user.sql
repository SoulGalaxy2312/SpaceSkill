CREATE TABLE base_user (
    id UUID PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    profile_name VARCHAR(255) NOT NULL,
    location VARCHAR(255),
    about TEXT,
    role VARCHAR(50) NOT NULL
);

-- Migrate existing users
INSERT INTO base_user (id, email, password, profile_name, location, about, role)
SELECT id, email, password, profile_name, location, about, role FROM users;

-- Migrate existing companies
INSERT INTO base_user (id, email, password, profile_name, location, about, role)
SELECT id, email, password, profile_name, location, about, role FROM companies;

-- Remove duplicated columns from users and companies, add FK
ALTER TABLE users DROP COLUMN email, DROP COLUMN password, DROP COLUMN profile_name, DROP COLUMN location, DROP COLUMN about, DROP COLUMN role;
ALTER TABLE users ADD CONSTRAINT fk_users_baseuser FOREIGN KEY (id) REFERENCES base_user(id);

ALTER TABLE companies DROP COLUMN email, DROP COLUMN password, DROP COLUMN profile_name, DROP COLUMN location, DROP COLUMN about, DROP COLUMN role;
ALTER TABLE companies ADD CONSTRAINT fk_companies_baseuser FOREIGN KEY (id) REFERENCES base_user(id);