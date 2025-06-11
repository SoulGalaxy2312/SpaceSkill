-- Create base tables (no foreign keys)
-- Create users table
CREATE TABLE users (
    id UUID PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    profile_name VARCHAR(255),
    location VARCHAR(255),
    about TEXT,
    role VARCHAR(50)
);

-- Create companies table
CREATE TABLE companies (
    id UUID PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    profile_name VARCHAR(255),
    location VARCHAR(255),
    about TEXT,
    role VARCHAR(50)
);

-- Create tables with foreign keys
-- Create user related tables
CREATE TABLE user_skills (
    user_id UUID REFERENCES users(id),
    skills VARCHAR(255),
    PRIMARY KEY (user_id, skills)
);

CREATE TABLE user_experiences (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES users(id),
    start_date DATE,
    end_date DATE,
    company VARCHAR(255),
    title VARCHAR(255)
);

CREATE TABLE user_educations (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES users(id),
    start_date DATE,
    end_date DATE,
    school VARCHAR(255),
    degree VARCHAR(255),
    field_of_study VARCHAR(255)
);

-- Create relationship tables
CREATE TABLE user_connections (
    user_id UUID REFERENCES users(id),
    connected_user_id UUID REFERENCES users(id),
    PRIMARY KEY (user_id, connected_user_id)
);

CREATE TABLE user_following_companies (
    user_id UUID REFERENCES users(id),
    company_id UUID REFERENCES companies(id),
    PRIMARY KEY (user_id, company_id)
);

CREATE TABLE company_recruiters (
    company_id UUID REFERENCES companies(id),
    user_id UUID REFERENCES users(id),
    PRIMARY KEY (company_id, user_id)
);

-- Create jobs table
CREATE TABLE jobs (
    id UUID PRIMARY KEY,
    company_id UUID NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    created_at DATE NOT NULL,
    location VARCHAR(255),
    required_skills TEXT[],
    CONSTRAINT fk_company FOREIGN KEY (company_id) REFERENCES companies(id)
);

-- Create applications table
CREATE TABLE applications (
    id UUID PRIMARY KEY,
    job_id UUID NOT NULL,
    user_id UUID NOT NULL,
    applied_at DATE NOT NULL,
    resume_url VARCHAR(255),
    status VARCHAR(50),
    CONSTRAINT fk_job FOREIGN KEY (job_id) REFERENCES jobs(id)
);

-- Create notifications table
CREATE TABLE notifications (
    id UUID PRIMARY KEY,
    job_id UUID,
    user_id UUID NOT NULL,
    company_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_job_notification FOREIGN KEY (job_id) REFERENCES jobs(id),
    CONSTRAINT fk_company_notification FOREIGN KEY (company_id) REFERENCES companies(id)
);

-- Insert companies first: password = password123 (bcrypt encoded)
INSERT INTO companies (id, email, password, profile_name, location, role) VALUES
    ('cb10104c-9de0-4ffe-a17f-603be508e318', 'company1@test.com', '$2a$10$A/Knays7Stm1EFVDRBf4Pe93ka00/NZaxAhvOc/Y3AUQn17uQocbq', 'Company One', 'New York', 'COMPANY'),
    ('6dfcb5ea-b611-4900-9623-4e149cd89850', 'company2@test.com', '$2a$10$A/Knays7Stm1EFVDRBf4Pe93ka00/NZaxAhvOc/Y3AUQn17uQocbq', 'Company Two', 'San Francisco', 'COMPANY'),
    ('dd200be9-b108-4ca4-96b1-4ca43049c9f6', 'company3@test.com', '$2a$10$A/Knays7Stm1EFVDRBf4Pe93ka00/NZaxAhvOc/Y3AUQn17uQocbq', 'Company Three', 'Seattle', 'COMPANY');

-- Jobs for Company 1 (cb10104c-9de0-4ffe-a17f-603be508e318)
INSERT INTO jobs (id, company_id, title, description, created_at, location, required_skills) VALUES
    (gen_random_uuid(), 'cb10104c-9de0-4ffe-a17f-603be508e318', 'Senior Software Engineer', 'Lead development of core platform features', CURRENT_DATE, 'New York', ARRAY['Java', 'Spring Boot', 'PostgreSQL']),
    (gen_random_uuid(), 'cb10104c-9de0-4ffe-a17f-603be508e318', 'Frontend Developer', 'Build responsive web applications', CURRENT_DATE, 'Remote', ARRAY['React', 'TypeScript', 'CSS']),
    (gen_random_uuid(), 'cb10104c-9de0-4ffe-a17f-603be508e318', 'DevOps Engineer', 'Manage cloud infrastructure', CURRENT_DATE, 'San Francisco', ARRAY['AWS', 'Docker', 'Kubernetes']),
    (gen_random_uuid(), 'cb10104c-9de0-4ffe-a17f-603be508e318', 'Product Manager', 'Drive product strategy and execution', CURRENT_DATE, 'Boston', ARRAY['Agile', 'JIRA', 'Product Strategy']),
    (gen_random_uuid(), 'cb10104c-9de0-4ffe-a17f-603be508e318', 'Data Scientist', 'Build ML models and analytics', CURRENT_DATE, 'Seattle', ARRAY['Python', 'TensorFlow', 'SQL']);

-- Jobs for Company 2 (6dfcb5ea-b611-4900-9623-4e149cd89850)
INSERT INTO jobs (id, company_id, title, description, created_at, location, required_skills) VALUES
    (gen_random_uuid(), '6dfcb5ea-b611-4900-9623-4e149cd89850', 'Backend Developer', 'Design and implement APIs', CURRENT_DATE, 'Austin', ARRAY['Java', 'MongoDB', 'Redis']),
    (gen_random_uuid(), '6dfcb5ea-b611-4900-9623-4e149cd89850', 'UI/UX Designer', 'Create beautiful user interfaces', CURRENT_DATE, 'Chicago', ARRAY['Figma', 'Adobe XD', 'Sketch']),
    (gen_random_uuid(), '6dfcb5ea-b611-4900-9623-4e149cd89850', 'System Architect', 'Design scalable systems', CURRENT_DATE, 'Remote', ARRAY['System Design', 'AWS', 'Microservices']),
    (gen_random_uuid(), '6dfcb5ea-b611-4900-9623-4e149cd89850', 'QA Engineer', 'Ensure software quality', CURRENT_DATE, 'Denver', ARRAY['Selenium', 'JUnit', 'TestNG']),
    (gen_random_uuid(), '6dfcb5ea-b611-4900-9623-4e149cd89850', 'Mobile Developer', 'Build iOS/Android apps', CURRENT_DATE, 'Miami', ARRAY['Swift', 'Kotlin', 'React Native']);

-- Jobs for Company 3 (dd200be9-b108-4ca4-96b1-4ca43049c9f6)
INSERT INTO jobs (id, company_id, title, description, created_at, location, required_skills) VALUES
    (gen_random_uuid(), 'dd200be9-b108-4ca4-96b1-4ca43049c9f6', 'Cloud Engineer', 'Manage cloud infrastructure', CURRENT_DATE, 'Portland', ARRAY['AWS', 'Azure', 'GCP']),
    (gen_random_uuid(), 'dd200be9-b108-4ca4-96b1-4ca43049c9f6', 'Security Engineer', 'Implement security measures', CURRENT_DATE, 'Washington DC', ARRAY['Security+', 'CISSP', 'Penetration Testing']),
    (gen_random_uuid(), 'dd200be9-b108-4ca4-96b1-4ca43049c9f6', 'Full Stack Developer', 'Build end-to-end applications', CURRENT_DATE, 'Los Angeles', ARRAY['React', 'Node.js', 'PostgreSQL']),
    (gen_random_uuid(), 'dd200be9-b108-4ca4-96b1-4ca43049c9f6', 'Machine Learning Engineer', 'Develop ML models', CURRENT_DATE, 'Seattle', ARRAY['Python', 'PyTorch', 'scikit-learn']),
    (gen_random_uuid(), 'dd200be9-b108-4ca4-96b1-4ca43049c9f6', 'Technical Lead', 'Lead development team', CURRENT_DATE, 'San Jose', ARRAY['Java', 'Leadership', 'Architecture']);