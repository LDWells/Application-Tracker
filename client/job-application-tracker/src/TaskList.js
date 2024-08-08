
import {useState, useEffect} from 'react';
import StatusColor from './StatusColor';


function TaskList({applicationId})
{
	const [tasks, setTasks] = useState([]);
	const token = localStorage.getItem('token');
	const init = {
		method: 'GET',
		headers: {
			'Authorization': `Bearer ${token}`,
			},
		};

	useEffect ( () => {
		fetch(`http://localhost:8080/api/tasks/application/${applicationId}`, init)
		.then(response => {
			if (response.status === 200)
			{
				return response.json();
			}
			else
			{
				return Promise.reject(`Unexpected status code: ${response.status}`);
			}
		})
		.then(data => {
			if (data)
			{
				setTasks(data);
			}
			else
			{
				console.log("NO DATA");
			}
		})
		.catch(console.log)
	},[]);

	return (
		<>
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