
import {useState, useEffect} from 'react';
import {Link, useParams} from 'react-router-dom';
import StatusColor from './StatusColor';

const APPLICATION_DTO_DEFAULT = {
	companyId: 0,
	companyName: '',
	campanyAddress: '',
	jobId: 0,
	jobTitle: '',
	jobDescription: '',
	applicationId: 0,
	userId: 0,
	applicationDate: '',
	appliedOn: '',
	status: ''
};

function ApplicationListPage()
{

	const [applications, setApplications] = useState([]);
	const [tasks, setTasks] = useState([]);

	const {userId} = useParams();
	const token = sessionStorage.getItem('token');
	const init = {
		method: 'GET',
		headers: {
			'Authorization': `Bearer ${token}`,
			},
		};//Authentication header for get

	useEffect( () => {
		if (userId)
		{
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
					const newApplications = data.filter(a=> a.userId === parseInt(userId));
					setApplications(newApplications);
					getTasks(newApplications);
				}
				else
				{
					console.log("NO DATA");
				}
			})
			.catch(console.log)
		}
		else
		{
			console.log("SETTING APPLICATIONS HERE2");
			setApplications([]);
		}
	},[userId]); 

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
					// console.log(tempApplications[i]);
					for(let j=0;j<data.length;j++)
					{
						// console.log(data[j]);
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
		<h1 className='center'>Applications and Tasks</h1>
			<section className="modal-body row">
				<section className='col-md-6'>
					<h1 className='center'>Applications</h1>
					{applications.map(a => 
						<div key={a.applicationId} className='applicationListBox mb-5'>
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
							<h5 className='taskListBoxText'>{getApplication(t.applicationId).jobTitle} at {getApplication(t.applicationId).companyName}</h5>
							<h6 className='taskListBoxText'>{t.description}</h6>
							<h6 className='taskListBoxText'>Status: <StatusColor status={t.status}/></h6>
							<h6 className='taskListBoxText'>Due Date: {t.dueDate}</h6>
							<h6 className='taskListBoxText'>Reminder Date: {t.reminderDate}</h6>
						</div>
					)}
				</section>
			</section>
		</>
	)
}
export default ApplicationListPage;