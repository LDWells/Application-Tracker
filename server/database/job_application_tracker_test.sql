DROP DATABASE IF EXISTS job_application_tracker_test;
CREATE DATABASE job_application_tracker_test;
USE job_application_tracker_test;

create table app_user (
    app_user_id int primary key auto_increment,
    username varchar(50) not null unique,
    password_hash varchar(2048) not null,
    disabled boolean not null default(0),
    email varchar(250)
);

create table app_role (
    app_role_id int primary key auto_increment,
    `name` varchar(50) not null unique
);

create table app_user_role (
    app_user_id int not null,
    app_role_id int not null,
    constraint pk_app_user_role
        primary key (app_user_id, app_role_id),
    constraint fk_app_user_role_user_id
        foreign key (app_user_id)
        references app_user(app_user_id),
    constraint fk_app_user_role_role_id
        foreign key (app_role_id)
        references app_role(app_role_id)
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
    CONSTRAINT fk_application_user FOREIGN KEY (user_id) REFERENCES app_user(app_user_id),
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
    summary varchar(255) NOT NULL,
    content TEXT NOT NULL,
    post_date DATE,
    CONSTRAINT fk_post_user FOREIGN KEY (user_id) REFERENCES app_user(app_user_id)
);

CREATE TABLE `Comment` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    post_id INT,
    user_id INT,
    content TEXT NOT NULL,
    comment_date DATE,
    CONSTRAINT fk_comment_post FOREIGN KEY (post_id) REFERENCES Post(id),
    CONSTRAINT fk_comment_user FOREIGN KEY (user_id) REFERENCES app_user(app_user_id)
);


-- Testing Security
insert into app_role (`name`) values
    ('USER'),
    ('ADMIN');

-- passwords are set to "P@ssw0rd!"
insert into app_user (username, password_hash, disabled)
    values
    ('admin', '$2a$10$yzRgjbTQH41nMY5OhHmN8eInejTWmP.6tjNekRoaL7D2/Or1eVxhe', '0'),
    ('john@smith.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 0),
    ('sally@jones.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 0);

insert into app_user_role
    values
    (1, 1),
    (1, 2),
    (2, 1),
    (3, 1);
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
    DELETE FROM app_role;
    DELETE FROM app_user;
    DELETE FROM app_user_role;

    -- Reset auto-increment values
    ALTER TABLE app_user AUTO_INCREMENT = 1;
	ALTER TABLE app_role AUTO_INCREMENT = 1;
	ALTER TABLE app_user_role AUTO_INCREMENT = 1;
    ALTER TABLE Company AUTO_INCREMENT = 1;
    ALTER TABLE Job AUTO_INCREMENT = 1;
    ALTER TABLE Application AUTO_INCREMENT = 1;
    ALTER TABLE Task AUTO_INCREMENT = 1;
    ALTER TABLE Post AUTO_INCREMENT = 1;
    ALTER TABLE `Comment` AUTO_INCREMENT = 1;

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
    (3, 4, '2023-01-22', 'Monster', 'REJECTED');

    -- Populate Task table with dummy data
    INSERT INTO Task (application_id, description, due_date, reminder_date, status) VALUES
    (1, 'Follow up email', '2023-01-20', '2023-01-19', 'PENDING'),
    (2, 'Prepare for interview', '2023-02-25', '2023-02-24', 'PENDING'),
    (3, 'Send thank you note', '2023-03-15', '2023-03-14', 'COMPLETED');
    
    -- Populate Post table with dummy data
    INSERT INTO Post (user_id, title, summary, content, post_date) VALUES
    (2, 'My Interview Experience at Tech Corp', 'I had a great experience with the interview process at Tech Corp...', 'The interview was good.', '2023-01-25'),
    (3, 'Tips for Job Applications', 'Here are some useful tips for applying to jobs...', 'Have a job already', '2023-02-28'),
    (3, '10 MOre Tips for Job Applications', 'different Here are some useful tips for applying to jobs...', 'Have multiple jobs', '2023-02-28');

    -- Populate Comment table with dummy data
    INSERT INTO `Comment` (post_id, user_id, content, comment_date) VALUES
    (1, 3, 'Thanks for sharing your experience!', '2023-01-26'),
    (2, 2, 'These tips are really helpful!', '2023-03-01');


-- Testing Security
insert into app_role (`name`) values
    ('USER'),
    ('ADMIN');

-- passwords are set to "P@ssw0rd!"
insert into app_user (username, password_hash, disabled)
    values
    ('admin', '$2a$10$yzRgjbTQH41nMY5OhHmN8eInejTWmP.6tjNekRoaL7D2/Or1eVxhe', '0'),
    ('john@smith.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 0),
    ('sally@jones.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 0);

insert into app_user_role
    values
    (1, 1),
    (1, 2),
    (2, 1),
    (3, 1);

	-- Re-enable foreign key checks
    SET FOREIGN_KEY_CHECKS = 1;
END //

DELIMITER ;
