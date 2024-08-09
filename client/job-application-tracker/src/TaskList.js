
import {useState, useEffect} from 'react';
import {useNavigate} from 'react-router-dom';
import StatusColor from './StatusColor';


function TaskList({applicationId})
{
	const [tasks, setTasks] = useState([]);
	const navigate = useNavigate();
	const token = sessionStorage.getItem('token');
	const init = {
		method: 'GET',
		headers: {
			'Authorization': `Bearer ${token}`,
			},
		};

	useEffect ( () => {
		fetch(`http://localhost:8080/api/tasks/application/${applicationId}`, init)
		.then(response => {
			if (response.status === 200 || response.status === 404)
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

	const handleDeleteTask = (taskId) => {
		if (window.confirm(`Are you sure you want delete this task?`))
		{
			const init2 = {
				method: 'DELETE',
				headers: {
					'Authorization': `Bearer ${token}`,
					},
			};
			fetch(`http://localhost:8080/api/tasks/${taskId}`, init2)
			.then(response => {
				if (response.status === 204)
				{
					const newTasks = tasks.filter(t => t.id !== taskId);
					setTasks(newTasks);
					navigate(`/application/${applicationId}`)
				}
				else
				{
					return Promise.reject(`Unexpected status code: ${response.status}`);
				}
			})
			.catch(console.log);
		}
	};

	return (
		<>
			<section className='taskContainer'>
				<ul>
					{tasks.map(t => 
						<div key={t.id} className='taskListBox mb-5'>
							<h2 className='taskListBoxText'>Task</h2>
							<h4 className='taskListBoxText'>{t.description}</h4>
							<h6 className='taskListBoxText'>Status: <StatusColor status={t.status}/></h6>
							<h6 className='taskListBoxText'>Due Date: {t.dueDate}</h6>
							<h6 className='taskListBoxText'>Reminder Date: {t.reminderDate}</h6>
							<button className='applicationBoxText center btn btn-outline-danger' onClick={() => handleDeleteTask(t.id)}>Delete Task</button>
						</div>
					)}
				</ul>
			</section>
		</>
	)
}
export default TaskList;