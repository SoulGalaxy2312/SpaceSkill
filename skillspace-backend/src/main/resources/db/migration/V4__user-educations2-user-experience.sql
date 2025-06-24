ALTER TABLE user_educations
ALTER COLUMN start_date TYPE VARCHAR(7) USING TO_CHAR(start_date, 'YYYY-MM');

ALTER TABLE user_educations
ALTER COLUMN end_date TYPE VARCHAR(7) USING TO_CHAR(end_date, 'YYYY-MM');

ALTER TABLE user_experiences
ALTER COLUMN start_date TYPE VARCHAR(7) USING TO_CHAR(start_date, 'YYYY-MM');

ALTER TABLE user_experiences
ALTER COLUMN end_date TYPE VARCHAR(7) USING TO_CHAR(end_date, 'YYYY-MM');
