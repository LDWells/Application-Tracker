DROP DATABASE IF EXISTS job_application_tracker_test;
CREATE DATABASE job_application_tracker_test;
USE job_application_tracker_test;

CREATE TABLE `User` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    google_id VARCHAR(255) UNIQUE,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role ENUM('ADMIN', 'USER') NOT NULL
);

CREATE TABLE Company (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255)
);

CREATE TABLE Job (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    company_id INT,
    CONSTRAINT fk_job_company FOREIGN KEY (company_id) REFERENCES Company(id)
);

CREATE TABLE Application (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    job_id INT,
    application_date DATE,
    applied_on VARCHAR(250),
    status ENUM('APPLIED', 'INTERVIEW', 'OFFER', 'REJECTED'),
    CONSTRAINT fk_application_user FOREIGN KEY (user_id) REFERENCES `User`(id),
    CONSTRAINT fk_application_job FOREIGN KEY (job_id) REFERENCES Job(id)
);

CREATE TABLE Task (
    id INT AUTO_INCREMENT PRIMARY KEY,
    application_id INT,
    description VARCHAR(255),
    due_date DATE,
    reminder_date DATE,
    status ENUM('PENDING', 'COMPLETED'),
    CONSTRAINT fk_task_application FOREIGN KEY (application_id) REFERENCES Application(id)
);

CREATE TABLE Post (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    post_date DATE,
    CONSTRAINT fk_post_user FOREIGN KEY (user_id) REFERENCES `User`(id)
);

CREATE TABLE `Comment` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    post_id INT,
    user_id INT,
    content TEXT NOT NULL,
    comment_date DATE,
    CONSTRAINT fk_comment_post FOREIGN KEY (post_id) REFERENCES Post(id),
    CONSTRAINT fk_comment_user FOREIGN KEY (user_id) REFERENCES `User`(id)
);

CREATE TABLE app_user (
    app_user_id INT AUTO_INCREMENT PRIMARY KEY,
    google_id VARCHAR(255) UNIQUE,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    disabled BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE app_user_role (
    app_user_id INT,
    app_role_id INT,
    PRIMARY KEY (app_user_id, app_role_id),
    FOREIGN KEY (app_user_id) REFERENCES app_user(app_user_id),
    FOREIGN KEY (app_role_id) REFERENCES app_role(app_role_id)
);

CREATE TABLE app_role (
    app_role_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

DELIMITER //

CREATE PROCEDURE set_known_good_state()
BEGIN

	-- Disable foreign key checks
    SET FOREIGN_KEY_CHECKS = 0;
    
    -- Error Code: 1175. You are using safe update mode and you tried to update a table without a WHERE that uses a KEY column.
    -- To disable safe mode, toggle the option in Preferences -> SQL Editor and reconnect.   
    
    -- Delete from dependent tables first
    DELETE FROM `Comment`;
    DELETE FROM Post;
    DELETE FROM Task;
    DELETE FROM Application;
    DELETE FROM Job;
    DELETE FROM Company;
    DELETE FROM `User`;

    -- Reset auto-increment values
    ALTER TABLE `User` AUTO_INCREMENT = 1;
    ALTER TABLE Company AUTO_INCREMENT = 1;
    ALTER TABLE Job AUTO_INCREMENT = 1;
    ALTER TABLE Application AUTO_INCREMENT = 1;
    ALTER TABLE Task AUTO_INCREMENT = 1;
    ALTER TABLE Post AUTO_INCREMENT = 1;
    ALTER TABLE `Comment` AUTO_INCREMENT = 1;

    -- Populate User table with dummy data
    INSERT INTO `User` (google_id, username, email, password, role) VALUES
    ('google-123', 'admin_user', 'admin@example.com', 'password123', 'ADMIN'),
    ('google-124', 'john_doe', 'john@example.com', 'password123', 'USER'),
    ('google-125', 'jane_smith', 'jane@example.com', 'password123', 'USER'),
    ('google-126', 'mike_brown', 'mike@example.com', 'password123', 'USER'),
    ('google-127', 'lisa_white', 'lisa@example.com', 'password123', 'USER'),
    ('google-128', 'chris_green', 'chris@example.com', 'password123', 'USER'),
    ('google-129', 'patricia_black', 'patricia@example.com', 'password123', 'USER'),
    ('google-130', 'matt_gray', 'matt@example.com', 'password123', 'USER'),
    ('google-131', 'linda_yellow', 'linda@example.com', 'password123', 'USER'),
    ('google-132', 'dave_blue', 'dave@example.com', 'password123', 'USER'),
    ('google-133', 'susan_purple', 'susan@example.com', 'password123', 'USER'),
    ('google-134', 'tom_red', 'tom@example.com', 'password123', 'USER'),
    ('google-135', 'nancy_orange', 'nancy@example.com', 'password123', 'USER'),
    ('google-136', 'paul_violet', 'paul@example.com', 'password123', 'USER'),
    ('google-137', 'carol_turquoise', 'carol@example.com', 'password123', 'USER');

    -- Populate Company table with dummy data
    INSERT INTO Company (name, address) VALUES
    ('Tech Corp', '123 Tech Lane, Silicon Valley, CA'),
    ('Biz Solutions', '456 Business St, New York, NY'),
    ('Health Inc', '789 Health Blvd, Boston, MA'),
    ('Finance Group', '101 Finance Way, Chicago, IL'),
    ('Education Co', '202 Education Ave, San Francisco, CA'),
    ('Retail King', '303 Retail Road, Dallas, TX'),
    ('Energy Solutions', '404 Energy St, Houston, TX'),
    ('Auto Motors', '505 Auto Drive, Detroit, MI'),
    ('Food Industries', '606 Food Lane, Seattle, WA'),
    ('Media House', '707 Media Blvd, Los Angeles, CA'),
    ('Entertainment LLC', '808 Entertainment Ave, Las Vegas, NV'),
    ('Fashion Inc', '909 Fashion Road, Miami, FL'),
    ('Travel Corp', '1010 Travel St, Orlando, FL'),
    ('Construction Ltd', '1111 Construction Blvd, Atlanta, GA'),
    ('Logistics LLC', '1212 Logistics Way, Denver, CO');

    -- Populate Job table with dummy data
    INSERT INTO Job (title, description, company_id) VALUES
    ('Software Engineer', 'Develop and maintain web applications.', 1),
    ('Business Analyst', 'Analyze business processes and requirements.', 2),
    ('Data Scientist', 'Work on data analysis and machine learning models.', 3),
    ('Financial Analyst', 'Perform financial analysis and modeling.', 4),
    ('Teacher', 'Teach various subjects to students.', 5),
    ('Store Manager', 'Manage retail store operations.', 6),
    ('Energy Analyst', 'Analyze energy consumption and savings.', 7),
    ('Mechanical Engineer', 'Design and develop mechanical systems.', 8),
    ('Food Scientist', 'Research and develop food products.', 9),
    ('Journalist', 'Write and report news articles.', 10),
    ('Event Planner', 'Plan and organize events.', 11),
    ('Fashion Designer', 'Design and create fashion items.', 12),
    ('Travel Agent', 'Assist clients with travel arrangements.', 13),
    ('Construction Manager', 'Manage construction projects.', 14),
    ('Logistics Coordinator', 'Coordinate logistics and supply chain operations.', 15);

    -- Populate Application table with dummy data
    INSERT INTO Application (user_id, job_id, application_date, applied_on, status) VALUES
    (2, 1, '2023-01-15', 'LinkedIn', 'APPLIED'),
    (3, 2, '2023-02-20', 'Indeed', 'INTERVIEW'),
    (2, 3, '2023-03-10', 'Company Website', 'OFFER'),
    (4, 4, '2023-01-22', 'Monster', 'REJECTED'),
    (5, 5, '2023-02-28', 'LinkedIn', 'APPLIED'),
    (6, 6, '2023-03-05', 'Indeed', 'INTERVIEW'),
    (7, 7, '2023-01-30', 'Company Website', 'OFFER'),
    (8, 8, '2023-02-10', 'Monster', 'REJECTED'),
    (9, 9, '2023-03-15', 'LinkedIn', 'APPLIED'),
    (10, 10, '2023-01-18', 'Indeed', 'INTERVIEW'),
    (11, 11, '2023-02-25', 'Company Website', 'OFFER'),
    (12, 12, '2023-03-20', 'Monster', 'REJECTED'),
    (13, 13, '2023-01-27', 'LinkedIn', 'APPLIED'),
    (14, 14, '2023-02-05', 'Indeed', 'INTERVIEW'),
    (15, 15, '2023-03-12', 'Company Website', 'OFFER');

    -- Populate Task table with dummy data
    INSERT INTO Task (application_id, description, due_date, reminder_date, status) VALUES
    (1, 'Follow up email', '2023-01-20', '2023-01-19', 'PENDING'),
    (2, 'Prepare for interview', '2023-02-25', '2023-02-24', 'PENDING'),
    (3, 'Send thank you note', '2023-03-15', '2023-03-14', 'COMPLETED'),
    (4, 'Research company', '2023-01-25', '2023-01-24', 'PENDING'),
    (5, 'Update resume', '2023-03-01', '2023-02-28', 'PENDING'),
    (6, 'Submit portfolio', '2023-03-10', '2023-03-09', 'PENDING'),
    (7, 'Follow up call', '2023-02-15', '2023-02-14', 'COMPLETED'),
    (8, 'Attend networking event', '2023-03-05', '2023-03-04', 'PENDING'),
    (9, 'Prepare presentation', '2023-03-20', '2023-03-19', 'PENDING'),
    (10, 'Send follow-up email', '2023-02-25', '2023-02-24', 'PENDING'),
    (11, 'Schedule mock interview', '2023-03-10', '2023-03-09', 'PENDING'),
    (12, 'Review job description', '2023-01-20', '2023-01-19', 'COMPLETED'),
    (13, 'Create cover letter', '2023-02-15', '2023-02-14', 'PENDING'),
    (14, 'Complete assessment', '2023-03-20', '2023-03-19', 'PENDING'),
    (15, 'Prepare for second interview', '2023-04-01', '2023-03-31', 'PENDING');
    
    -- Populate Post table with dummy data
    INSERT INTO Post (user_id, title, content, post_date) VALUES
    (2, 'My Interview Experience at Tech Corp', 'I had a great experience with the interview process at Tech Corp...', '2023-01-25'),
    (3, 'Tips for Job Applications', 'Here are some useful tips for applying to jobs...', '2023-02-28'),
    (4, 'Resume Writing Tips', 'Make sure your resume is concise and highlights your achievements...', '2023-03-15'),
    (5, 'Networking Strategies', 'Networking can significantly boost your job search...', '2023-04-10'),
    (6, 'How to Ace Your Interviews', 'Preparation is key to acing your interviews...', '2023-05-05'),
    (7, 'Balancing Multiple Job Offers', 'Deciding between multiple job offers can be challenging...', '2023-06-20'),
    (8, 'Importance of Follow-Up Emails', 'Sending a follow-up email shows your interest in the position...', '2023-07-15'),
    (9, 'What to Do After Rejection', 'Handling job rejection gracefully is important...', '2023-08-10'),
    (10, 'Using LinkedIn for Job Search', 'LinkedIn is a powerful tool for job seekers...', '2023-09-05'),
    (11, 'Benefits of Job Fairs', 'Job fairs provide a great opportunity to meet potential employers...', '2023-10-01'),
    (12, 'Preparing for Remote Interviews', 'Remote interviews require different preparation strategies...', '2023-11-20'),
    (13, 'Creating a Portfolio', 'A portfolio can showcase your skills and projects...', '2023-12-15'),
    (14, 'Understanding Job Descriptions', 'Reading job descriptions carefully is crucial...', '2024-01-10'),
    (15, 'Salary Negotiation Tips', 'Negotiating your salary can be intimidating but necessary...', '2024-02-05');

    -- Populate Comment table with dummy data
    INSERT INTO `Comment` (post_id, user_id, content, comment_date) VALUES
    (1, 3, 'Thanks for sharing your experience!', '2023-01-26'),
    (2, 2, 'These tips are really helpful!', '2023-03-01'),
    (3, 4, 'Great advice on resume writing!', '2023-03-16'),
    (4, 5, 'Networking has really helped me in my job search.', '2023-04-11'),
    (5, 6, 'I followed these interview tips and it worked!', '2023-05-06'),
    (6, 7, 'I had to choose between two offers last month.', '2023-06-21'),
    (7, 8, 'Follow-up emails have definitely made a difference.', '2023-07-16'),
    (8, 9, 'Rejection is tough but part of the process.', '2023-08-11'),
    (9, 10, 'LinkedIn helped me land my last job.', '2023-09-06'),
    (10, 11, 'I found my current job at a job fair.', '2023-10-02'),
    (11, 12, 'Remote interviews are tricky but manageable.', '2023-11-21'),
    (12, 13, 'Creating a portfolio showcased my skills.', '2023-12-16'),
    (13, 14, 'I always read job descriptions carefully.', '2024-01-11'),
    (14, 15, 'Negotiated my salary successfully last week.', '2024-02-06'),
    (15, 2, 'This post was very informative.', '2024-02-07');


    -- Step 1: Populate the app_role table
    INSERT INTO app_role (name) VALUES
    ('USER'),
    ('ADMIN');

    -- Step 2: Populate the app_user table
    INSERT INTO app_user (google_id, username, email, password_hash, disabled) VALUES
    ('google-12345', 'john_doe', 'john@example.com', '$2a$10$1234567890123456789012', FALSE), -- bcrypt hashed password
    ('google-67890', 'jane_doe', 'jane@example.com', '$2a$10$9876543210987654321098', FALSE), -- bcrypt hashed password
    (NULL, 'admin_user', 'admin@example.com', '$2a$10$1122334455667788990011', FALSE);

    -- Step 3: Populate the app_user_role table
    -- Assuming app_user_id 1 -> john_doe, 2 -> jane_doe, 3 -> admin_user
    INSERT INTO app_user_role (app_user_id, app_role_id) VALUES
    (1, 1), -- john_doe as USER
    (2, 1), -- jane_doe as USER
    (3, 1), -- admin_user as USER
    (3, 2); -- admin_user as ADMIN

	-- Re-enable foreign key checks
    SET FOREIGN_KEY_CHECKS = 1;
END //

DELIMITER ;
