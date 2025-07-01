ALTER TABLE applications
    RENAME COLUMN resume_url TO personal_statement;

ALTER TABLE applications
    ALTER COLUMN personal_statement TYPE TEXT,
    ALTER COLUMN personal_statement SET NOT NULL;