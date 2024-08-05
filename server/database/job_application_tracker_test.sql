drop database if exists job_application_tracker_test;
CREATE DATABASE job_application_tracker_test;
use job_application_tracker_test;

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
    FOREIGN KEY (company_id) REFERENCES Company(id)
);


CREATE TABLE Application (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    job_id INT,
    application_date DATE,
    applied_on VARCHAR(250),
    status ENUM('APPLIED', 'INTERVIEW', 'OFFER', 'REJECTED'),
    FOREIGN KEY (user_id) REFERENCES User(id),
    FOREIGN KEY (job_id) REFERENCES Job(id)
);


CREATE TABLE Task (
    id INT AUTO_INCREMENT PRIMARY KEY,
    application_id INT,
    description VARCHAR(255),
    due_date DATE,
    reminder_date DATE,
    status ENUM('PENDING', 'COMPLETED'),
    FOREIGN KEY (application_id) REFERENCES Application(id)
);


CREATE TABLE Post (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    post_date DATE,
    FOREIGN KEY (user_id) REFERENCES User(id)
);

CREATE TABLE `Comment` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    post_id INT,
    user_id INT,
    content TEXT NOT NULL,
    comment_date DATE,
    FOREIGN KEY (post_id) REFERENCES Post(id),
    FOREIGN KEY (user_id) REFERENCES User(id)
);
