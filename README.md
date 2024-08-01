### Capstone Project Proposal: Job Application Tracker with Community Blog and Comments

#### 1. Problem Statement
Job seekers often apply to multiple positions simultaneously, making it difficult to track application statuses, deadlines, interview schedules, and follow-up actions. The lack of a centralized system to manage these applications can lead to missed opportunities, unorganized follow-ups, and overall inefficiency in the job search process. Additionally, job seekers may benefit from a community platform to share their hiring experiences, tips, and support each other, including a commenting feature for posts.

#### 2. Technical Solution
Develop a web application that helps job seekers manage their job applications in one place. The application will allow users to log details of each application, set reminders for important dates, track the status of each application, and manage associated tasks such as interviews and follow-ups. Additionally, it will feature a community section where users can write posts about their hiring experiences and add comments to these posts, creating a supportive and interactive environment.

**Scenario 1:**  
Emma is actively job hunting and has applied to several positions. She uses the Job Application Tracker to log each application with details like the company name, job title, application date, and status. She sets reminders for follow-up actions and interview dates. The application helps her stay organized and ensures she doesn’t miss any important deadlines.

**Scenario 2:**  
John has an upcoming interview but can’t remember the specific requirements of the job. He uses the Job Application Tracker to review the job description and prepare accordingly. After the interview, he updates the application status and logs notes for future reference.

**Scenario 3:**  
Jane wants to share her successful job application strategy with others. She writes a detailed post in the community section about her experiences and tips, helping other job seekers improve their own applications. Other users can comment on her post to ask questions or add their own insights.

#### 3. Glossary
- **Application:** An instance of a job application already submitted on another job portal, where the user will manage their job search journey.
- **Job Seeker:** A user who is actively looking for a job and uses the application to track their job applications.
- **Task:** A specific action related to a job application, such as preparing for an interview or sending a follow-up email.
- **Reminder:** A notification set by the user to remember important dates and actions.
- **Post:** An article written by a user in the community section about their hiring experiences, tips, or other relevant topics.
- **Comment:** A remark or query added by users to a post in the community section.

#### 4. High-Level Requirement
- **User Role (Job Seeker):**
  - Log in using a username and password.
  - Add a job application.
  - Edit a job application.
  - Delete a job application.
  - View a list of job applications.
  - Add tasks and set reminders for each application.
  - Update application status.
  - Write and read posts in the community section.
  - Add and read comments on posts in the community section.
- **Admin Role:**
  - Manage user accounts.
  - Perform CRUD operations on all community related stuff.
  - Moderate posts and comments in the community section.

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
- Reminder date

**Precondition:** User must be logged in and have existing job applications.  
**Post-condition:** Task is saved and reminders are set.

**Update Application Status**  
Update the status of job applications.

**Precondition:** User must be logged in and have existing job applications.  
**Post-condition:** Application status is updated.

**Write a Post in the Community Section**  
Create a post to share hiring experiences, tips, or other relevant information.

**Suggested Data:**
- Post title
- Post content
- Date of posting

**Precondition:** User must be logged in.  
**Post-condition:** Post is saved and visible in the community section.

**Add a Comment to a Post**  
Create a comment on a community post to engage in discussions or ask questions.

**Suggested Data:**
- Comment content
- Date of comment
- Post ID

**Precondition:** User must be logged in.  
**Post-condition:** Comment is saved and visible under the post.

### Stretch Goals
**Google Sign-In Integration:** Implement Google OAuth2 for user authentication to allow users to log in and sign up using their Google accounts.

**Google Calendar API Integration:** Use the Google Calendar API to sync application-related events (e.g., interviews, deadlines) with users' Google Calendars for better event management and reminders.

**TinyMCE Integration**: Use TinyMCE as a rich text editor for writing posts and comments in the community section to enhance the user experience.

**Additional Stretch Goals (Optional):**
- **WebSocket Communication:** Implement real-time notifications using WebSocket to notify users about upcoming deadlines, new tasks, or status updates.