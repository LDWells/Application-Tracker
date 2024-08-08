
import {useState, useEffect} from 'react';
import {Link, useParams} from 'react-router-dom';
import StatusColor from './StatusColor';

function TaskListPage()
{

	const [applications, setApplications] = useState([]);
	const [tasks, setTasks] = useState([]);

	const token = localStorage.getItem('token');
	const init = {
		method: 'GET',
		headers: {
			'Authorization': `Bearer ${token}`,
			},
		};//Authentication header for get

	useEffect( () => {
		fetch(`http://localhost:8080/api/application/details`, init)
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
				const newApplications = data.filter(a=> a.userId === parseInt(localStorage.getItem('appUserId')));
				setApplications(newApplications);
				getTasks(newApplications);
			}
			else
			{
				console.log("NO DATA");
			}
		})
		.catch(console.log)
	},[]); 

	const getTasks = (tempApplications) => {
		fetch('http://localhost:8080/api/tasks', init)
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
				var newTasks = [];
				for(let i = 0;i<tempApplications.length;i++)
				{
					for(let j=0;j<data.length;j++)
					{
						if (data[j].applicationId === tempApplications[i].applicationId)
						{
							newTasks.push(data[j])
						}
					}
				}
				setTasks(newTasks);
			}
			else
			{
				console.log("NO DATA");
			}
		})
		.catch(console.log)
	};

	const getApplication = (applicationId) => {
		return applications.find(a => a.applicationId === applicationId);
	}

	return (
		<>
			<section>
			<h1 className='center'>Tasks</h1>
					{tasks.map(t => 
						<div key={t.id} className='applicationListBox mb-5'>
							<h5 className='taskListBoxText'>{getApplication(t.applicationId).jobTitle} at {getApplication(t.applicationId).companyName}</h5>
							<h6 className='taskListBoxText'>{t.description}</h6>
							<h6 className='taskListBoxText'>Status: <StatusColor status={t.status}/></h6>
							<h6 className='taskListBoxText'>Due Date: {t.dueDate}</h6>
							<h6 className='taskListBoxText'>Reminder Date: {t.reminderDate}</h6>
						</div>
					)}
			</section>
		</>
	)
};
export default TaskListPage;