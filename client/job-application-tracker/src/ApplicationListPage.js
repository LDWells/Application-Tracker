
import {useState, useEffect} from 'react';
import {Link} from 'react-router-dom';
import TaskList from './TaskList';
import StatusColor from './StatusColor';
//Temp code

const APPLICATION_DTO_DEFAULT = {
	companyId: 1,
	companyName: 'Tech Corp',
	campanyAddress: '123 Tech Lane, Silicon Valley, CA',
	jobId: 1,
	jobTitle: 'Software Engineer',
	jobDescription: 'Develop and maintain web applications.',
	applicationId: 1,
	userId: 2,
	applicationDate: '01/15/2023',
	appliedOn: 'LinkedIn',
	status: 'OFFER'
};

const APPLICATION_DTO_DEFAULT2 = {
	companyId: 2,
	companyName: 'Biz Solutions',
	campanyAddress: '456 Business St, New York, NY',
	jobId: 2,
	jobTitle: 'Business Analyst',
	jobDescription: 'Analyze business processes and requirements.',
	applicationId: 2,
	userId: 3,
	applicationDate: '02/20/2023',
	appliedOn: 'Indeed',
	status: 'REJECTED'
};

const APPLICATION_DTOS_DEFAULT = [
	APPLICATION_DTO_DEFAULT,
	APPLICATION_DTO_DEFAULT2
];

const DEFAULT_TASK = {
	id: 1,
	applicationId: 1,
	description: 'Follow up email',
	dueDate: '01/20/2023',
	reminderDate: '01/19/2023',
	status: 'PENDING'
};

const DEFAULT_TASK2 = {
	id: 2,
	applicationId: 1,
	description: 'Prepare for interview',
	dueDate: '01/20/2023',
	reminderDate: '01/19/2023',
	status: 'COMPLETED'
};

const DEFAULT_TASKS = [
	DEFAULT_TASK,
	DEFAULT_TASK,
	DEFAULT_TASK,
	DEFAULT_TASK,
	DEFAULT_TASK,
	DEFAULT_TASK2
];


function ApplicationListPage()
{

	const [applications, setApplications] = useState(APPLICATION_DTOS_DEFAULT);
	const [tasks, setTasks] = useState(DEFAULT_TASKS);

	const findApplication = (applicaitonId) =>{
		return applications.find(a => a.id = applicaitonId);
	}

	return (
		<>
			{/* <h1>Application List Page</h1>
			<p>Component to display list of applications in non detailed view</p>
			<p>this will have for each application, the company name, the job title, the submission date, and the status</p> */}
			<seciton className="modal-body row">
				<section className='col-md-6'>
					<h1 className='center'>Applications</h1>
					{applications.map(a => 
						<div key={a.id} className='applicationListBox mb-5'>
							<h1 className='applicationListBoxText'>{a.jobTitle}</h1>
							
							<h6 className='applicationListBoxText'>{a.companyName}---{a.applicationDate}</h6>
							<h5 className='applicationListBoxText'>
							Status: <StatusColor status={a.status}/>
							<Link className="btn btn-outline-light applicationListButton" to={`/application/${a.id}`}>View Application</Link>
							</h5>
						</div>
					)}
				</section>
				<section className='col-md-6'>
					<h1 className='center'>Tasks</h1>
					{tasks.map(t => 
						<div key={t.id} className='applicationListBox mb-5'>
							<h5 className='taskListBoxText'>{findApplication(t.applicationId).jobTitle} at {findApplication(t.applicationId).companyName}</h5>
							<h6 className='taskListBoxText'>{t.description}</h6>
							<h6 className='taskListBoxText'>Status: <StatusColor status={t.status}/></h6>
							<h6 className='taskListBoxText'>Due Date: {t.dueDate}</h6>
							{/* <h6 className='taskListBoxText'>Reminder Date: {t.reminderDate}</h6> */}
						</div>
					)}
				</section>
			</seciton>
		</>
	)
}
export default ApplicationListPage;