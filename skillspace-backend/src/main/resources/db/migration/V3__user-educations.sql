-- Rename column school to university
ALTER TABLE user_educations RENAME COLUMN school TO university;

-- Add column title
ALTER TABLE user_educations ADD COLUMN title VARCHAR(255);

-- Optional: drop unused field
ALTER TABLE user_educations DROP COLUMN field_of_study;