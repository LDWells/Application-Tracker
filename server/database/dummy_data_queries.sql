use job_application_tracker_test;

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