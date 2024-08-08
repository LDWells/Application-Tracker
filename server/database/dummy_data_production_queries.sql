use job_application_tracker;

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
(1, 1, '2023-01-15', 'LinkedIn', 'APPLIED'),
(2, 2, '2023-02-20', 'Indeed', 'INTERVIEW'),
(3, 3, '2023-03-10', 'Company Website', 'OFFER'),
(1, 4, '2023-01-22', 'Monster', 'REJECTED'),
(2, 5, '2023-02-28', 'LinkedIn', 'APPLIED'),
(3, 6, '2023-03-05', 'Indeed', 'INTERVIEW'),
(1, 7, '2023-01-30', 'Company Website', 'OFFER'),
(2, 8, '2023-02-10', 'Monster', 'REJECTED'),
(3, 9, '2023-03-15', 'LinkedIn', 'APPLIED'),
(1, 10, '2023-01-18', 'Indeed', 'INTERVIEW'),
(2, 11, '2023-02-25', 'Company Website', 'OFFER'),
(3, 12, '2023-03-20', 'Monster', 'REJECTED'),
(1, 13, '2023-01-27', 'LinkedIn', 'APPLIED'),
(2, 14, '2023-02-05', 'Indeed', 'INTERVIEW'),
(3, 15, '2023-03-12', 'Company Website', 'OFFER');

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
(3, 'Prepare for second interview', '2023-04-01', '2023-03-31', 'PENDING');

-- Populate Post table with dummy data
INSERT INTO Post (user_id, title, content, post_date) VALUES
(1, 'My Interview Experience at Tech Corp', 'I had a great experience with the interview process at Tech Corp...', '2023-01-25'),
(2, 'Tips for Job Applications', 'Here are some useful tips for applying to jobs...', '2023-02-28'),
(3, 'Resume Writing Tips', 'Make sure your resume is concise and highlights your achievements...', '2023-03-15');

-- Populate Comment table with dummy data
INSERT INTO `Comment` (post_id, user_id, content, comment_date) VALUES
(1, 1, 'Thanks for sharing your experience!', '2023-01-26'),
(2, 2, 'These tips are really helpful!', '2023-03-01'),
(3, 3, 'Great advice on resume writing!', '2023-03-16');