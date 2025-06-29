-- Drop old notifications table if exists (if you want to preserve data, use ALTER TABLE instead)
DROP TABLE IF EXISTS notifications;

-- Create new notifications table matching the Notification entity
CREATE TABLE notifications (
    id UUID PRIMARY KEY,
    title VARCHAR(255),
    message TEXT,
    sender_id UUID NOT NULL REFERENCES base_user(id),
    recipient_id UUID NOT NULL REFERENCES base_user(id),
    job_id UUID REFERENCES jobs(id),
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL
);

-- Insert 5 notifications for each user
INSERT INTO notifications (id, title, message, sender_id, recipient_id, job_id, is_read, created_at)
VALUES
  -- Notifications for User 1
  (gen_random_uuid(), 'Welcome!', 'Welcome to SkillSpace, User One!', '11111111-2222-4333-8444-555555555555', '11111111-2222-4333-8444-555555555555', NULL, FALSE, NOW()),
  (gen_random_uuid(), 'Profile Complete', 'Your profile is now complete.', '11111111-2222-4333-8444-555555555555', '11111111-2222-4333-8444-555555555555', NULL, FALSE, NOW()),
  (gen_random_uuid(), 'New Job Posted', 'Check out new jobs in your area.', '22222222-3333-4444-8555-666666666666', '11111111-2222-4333-8444-555555555555', NULL, FALSE, NOW()),
  (gen_random_uuid(), 'Application Update', 'Your application has been received.', '33333333-4444-4555-8666-777777777777', '11111111-2222-4333-8444-555555555555', NULL, FALSE, NOW()),
  (gen_random_uuid(), 'Message from Company', 'A company has sent you a message.', 'cb10104c-9de0-4ffe-a17f-603be508e318', '11111111-2222-4333-8444-555555555555', NULL, FALSE, NOW()),

  -- Notifications for User 2
  (gen_random_uuid(), 'Welcome!', 'Welcome to SkillSpace, User Two!', '22222222-3333-4444-8555-666666666666', '22222222-3333-4444-8555-666666666666', NULL, FALSE, NOW()),
  (gen_random_uuid(), 'Profile Complete', 'Your profile is now complete.', '22222222-3333-4444-8555-666666666666', '22222222-3333-4444-8555-666666666666', NULL, FALSE, NOW()),
  (gen_random_uuid(), 'New Job Posted', 'Check out new jobs in your area.', '11111111-2222-4333-8444-555555555555', '22222222-3333-4444-8555-666666666666', NULL, FALSE, NOW()),
  (gen_random_uuid(), 'Application Update', 'Your application has been received.', '33333333-4444-4555-8666-777777777777', '22222222-3333-4444-8555-666666666666', NULL, FALSE, NOW()),
  (gen_random_uuid(), 'Message from Company', 'A company has sent you a message.', '6dfcb5ea-b611-4900-9623-4e149cd89850', '22222222-3333-4444-8555-666666666666', NULL, FALSE, NOW()),

  -- Notifications for User 3
  (gen_random_uuid(), 'Welcome!', 'Welcome to SkillSpace, User Three!', '33333333-4444-4555-8666-777777777777', '33333333-4444-4555-8666-777777777777', NULL, FALSE, NOW()),
  (gen_random_uuid(), 'Profile Complete', 'Your profile is now complete.', '33333333-4444-4555-8666-777777777777', '33333333-4444-4555-8666-777777777777', NULL, FALSE, NOW()),
  (gen_random_uuid(), 'New Job Posted', 'Check out new jobs in your area.', '11111111-2222-4333-8444-555555555555', '33333333-4444-4555-8666-777777777777', NULL, FALSE, NOW()),
  (gen_random_uuid(), 'Application Update', 'Your application has been received.', '22222222-3333-4444-8555-666666666666', '33333333-4444-4555-8666-777777777777', NULL, FALSE, NOW()),
  (gen_random_uuid(), 'Message from Company', 'A company has sent you a message.', 'dd200be9-b108-4ca4-96b1-4ca43049c9f6', '33333333-4444-4555-8666-777777777777', NULL, FALSE, NOW());