
import {useState, useEffect} from 'react';
import StatusColor from './StatusColor';

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
	DEFAULT_TASK2
];


function TaskList()
{

	const [tasks, setTasks] = useState(DEFAULT_TASKS);

	const statusColor = (status) => {
		if (status === "PENDING")
		{
			return <span className='text-warning'>{status}</span>;
		}
		else if (status === "REJECTED")
		{
			return <span className='text-danger'>{status}</span>;
		}
		else if (status === "INTERVIEW")
		{
			return <span className='text-info'>{status}</span>;
		}
		else if (status === "COMPLETED")
		{
			return <span className='text-success'>{status}</span>;
		}
	}

	return (
		<>
			{/* <h1>Task List</h1>
			<p>This will display a list of tasks</p> */}
			<section className='taskContainer'>
				<ul>
					{tasks.map(t => 
						<div key={t.id} className='taskListBox center mb-5'>
							<h2>Task</h2>
							<h4 className='taskListBoxText text-dark-50 text-dark'>{t.description}</h4>
							<h6 className='taskListBoxText text-dark-50 text-dark'>Status: <StatusColor status={t.status}/></h6>
							<h6 className='taskListBoxText text-dark-50 text-dark'>Due Date: {t.dueDate}</h6>
							<h6 className='taskListBoxText text-dark-50 text-dark'>Reminder Date: {t.reminderDate}</h6>
						</div>
					)}
				</ul>
			</section>
		</>
	)
}
export default TaskList;