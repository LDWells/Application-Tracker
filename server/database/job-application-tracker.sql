drop database if exists job_application_tracker;
CREATE DATABASE job_application_tracker;
use job_application_tracker;

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

-- Populate the app_role table
INSERT INTO app_role (name) VALUES
('USER'),
('ADMIN');