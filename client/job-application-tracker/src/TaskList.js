
import {useState, useEffect} from 'react';

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

	return (
		<>
			{/* <h1>Task List</h1>
			<p>This will display a list of tasks</p> */}
			<section>
				<ul>
					{tasks.map(t => 
						<div key={t.id}>
							<h6>{t.description}</h6>
							<h6>{t.status}</h6>
							<h6>{t.dueDate}</h6>
							<h6>{t.reminderDate}</h6>
						</div>
					)}
				</ul>
			</section>
		</>
	)
}
export default TaskList;