drop database if exists job_application_tracker;
CREATE DATABASE job_application_tracker;
use job_application_tracker;

-- CREATE TABLE `User` (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     google_id VARCHAR(255) UNIQUE,
--     username VARCHAR(50) NOT NULL UNIQUE,
--     email VARCHAR(100) NOT NULL UNIQUE,
--     password VARCHAR(100) NOT NULL,
--     role ENUM('ADMIN', 'USER') NOT NULL
-- );

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
    summary VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    post_date DATE,
    CONSTRAINT fk_post_user FOREIGN KEY (user_id) REFERENCES app_user(app_user_id)
);

CREATE TABLE `Comment` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    post_id INT,
    user_id INT,
    username varchar(255) NOT NULL,
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
    ('john', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 0),
    ('sally', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 0);

insert into app_user_role
    values
    (1, 1),
    (1, 2),
    (2, 1),
    (3, 1);
    
    SELECT c.id AS company_id, c.name AS company_name, c.address AS company_address, 
    j.id AS job_id, j.title AS job_title, j.description AS job_description,
    a.id AS application_id, a.user_id, a.application_date, a.applied_on, a.status 
    FROM Company c LEFT JOIN Job j ON c.id = j.company_id 
    LEFT JOIN Application a ON j.id = a.job_id;