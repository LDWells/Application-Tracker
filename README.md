### Capstone Project Proposal: Job Application Tracker

#### 1. Problem Statement
Job seekers often apply to multiple positions simultaneously, making it difficult to track application statuses, deadlines, interview schedules, and follow-up actions. The lack of a centralized system to manage these applications can lead to missed opportunities, unorganized follow-ups, and overall inefficiency in the job search process.

#### 2. Technical Solution
Develop a web application that helps job seekers manage their job applications in one place. The application will allow users to log details of each application, set reminders for important dates, track the status of each application, and manage associated tasks such as interviews and follow-ups.

**Scenario 1:**  
Emma is actively job hunting and has applied to several positions. She uses the Job Application Tracker to log each application with details like the company name, job title, application date, and status. She sets reminders for follow-up actions and interview dates. The application helps her stay organized and ensures she doesn’t miss any important deadlines.

**Scenario 2:**  
John has an upcoming interview but can’t remember the specific requirements of the job. He uses the Job Application Tracker to review the job description and prepare accordingly. After the interview, he updates the application status and logs notes for future reference.

#### 3. Glossary
- **Application:** An instance of a job application submitted to a company.
- **Job Seeker:** A user who is actively looking for a job and uses the application to track their job applications.
- **Task:** A specific action related to a job application, such as preparing for an interview or sending a follow-up email.
- **Reminder:** A notification set by the user to remember important dates and actions.

#### 4. High-Level Requirement
- **User Role (Job Seeker):**
  - Log in using a username and password.
  - Add a job application.
  - Edit a job application.
  - Delete a job application.
  - View a list of job applications.
  - Add tasks and set reminders for each application.
  - Update application status.
- **Admin Role:**
  - Manage user accounts.
  - View all job applications.
  - Perform CRUD operations on all entities.

#### 5. User Stories/Scenarios
**Create a Job Application**  
Create an application that job seekers can use to track their job applications.

**Suggested Data:**
- Company name
- Job title
- Application date
- Job description
- Job link
- Status (e.g., Applied, Interview, Offer, Rejected)
- Notes

**Precondition:** User must be logged in.  
**Post-condition:** Application is saved and visible in the user’s application list.

**Edit a Job Application**  
Can only edit existing applications.

**Precondition:** User must be logged in and have existing job applications.  
**Post-condition:** Application details are updated.

**Delete a Job Application**  
Can only delete existing applications.

**Precondition:** User must be logged in and have existing job applications.  
**Post-condition:** Application is removed from the list.

**Browse Job Applications**  
View a list of all job applications.

**Precondition:** User must be logged in.  
**Post-condition:** User can view a list of all their job applications with details.

**Add Tasks and Set Reminders**  
Create tasks and set reminders for each application.

**Suggested Data:**
- Task description
- Due date
- Reminder notification

**Precondition:** User must be logged in and have existing job applications.  
**Post-condition:** Task is saved and reminders are set.

**Update Application Status**  
Update the status of job applications.

**Precondition:** User must be logged in and have existing job applications.  
**Post-condition:** Application status is updated.

### Stretch Goals
**Google Sign-In Integration:** Implement Google OAuth2 for user authentication to allow users to log in and sign up using their Google accounts.

**Google Calendar API Integration:** Use the Google Calendar API to sync application-related events (e.g., interviews, deadlines) with users' Google Calendars for better event management and reminders.

**Additional Stretch Goals (Optional):**
- **WebSocket Communication:** Implement real-time notifications using WebSocket to notify users about upcoming deadlines, new tasks, or status updates.
