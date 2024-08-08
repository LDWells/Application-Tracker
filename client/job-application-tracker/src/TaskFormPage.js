
import { useState, useEffect } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";

const TASK_DTO_DEFAULT = {
	applicationId: 0,
	description: '',
	dueDate: '',
	reminderDate: '',
	status: 'PENDING'
};

function TaskFormPage()
{

	const [task, setTask] = useState(TASK_DTO_DEFAULT);
	const [errors, setErrors] = useState([]);
	const {applicationId} = useParams();
	const navigate = useNavigate();
	const userId = localStorage.getItem('userId');
	function handleChange(event)
	{
		const newTask = {...task};
		newTask[event.target.name] = event.target.value;
		setTask(newTask);
	};

	function handleTaskSubmit(event)
	{
		event.preventDefault();
		// we want to either add or update here
		const tempTask = {...task};
		tempTask.applicationId = parseInt(applicationId);
		setTask(tempTask);
		addTask(tempTask);
	};

	const addTask = (tempTask) =>
	{
		const url = 'http://localhost:8080/api/tasks'
		const token = localStorage.getItem('token');
		const init = {
			method: 'POST',
			headers: {
				'Authorization': `Bearer ${token}`,
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(tempTask)
		};
		fetch(url, init)
		.then(response => {
			if (response.status === 201 || response.status === 500)
			{
				return response.json();
			}
			else
			{
				return Promise.reject(`Unexcpected status code: ${response.status}`);
			}
		})
		.then(data => {
			if (data.id)
			{
				navigate(`/home`);
			}
			else
			{
				setErrors(data);
			}
		})
		.catch(console.log)
  	};

	return (
		<>
			<section id="formContainer" className="container">
				<h2 id="formHeading">{localStorage.getItem('updating') === true ? 'Update a Task' : 'Add a Task'}</h2>
				{errors.length > 0 && (
				<div id="error" className='alert alert-danger'>
					<p>The following errors occured: </p>
					<ul>
					{errors.map(error => 
					<li key={error}>{error}</li>
					)}
					</ul>
				</div>
				)}
				<form onSubmit={handleTaskSubmit} id="form">
					<fieldset className="form-group">
						<label htmlFor="description" className="bs-dark-text-emphasis">Description:</label>
						<input type="text" id="description" className="form-control" name="description"
							value={task.description}
							onChange={handleChange}/>
					</fieldset>
					<fieldset className="form-group">
						<label htmlFor="dueDate">Due Date:</label>
						<input type="date" id="dueDate" className="form-control" name="dueDate"
						value={task.dueDate}
						onChange={handleChange}/>
					</fieldset>
					<fieldset className="form-group">
						<label htmlFor="reminderDate">Reminder Date:</label>
						<input type="date" id="reminderDate" className="form-control" name="reminderDate"
						value={task.reminderDate}
						onChange={handleChange}/>
					</fieldset>
					<fieldset className="form-group">
						<label htmlFor="status">Status</label>
						<select 
						id="status" 
						name="status" 
						value={task.status} 
						onChange={handleChange}>
							<option>PENDING</option>
							<option>COMPLETED</option>
						</select>
					</fieldset>
					<div className="mt-4 center">
						<button className="btn btn-outline-light" type="submit" id="formSubmitButton">{localStorage.getItem('updating') === true ? 'Update Task' : 'Add Task'}</button>
						<Link className="btn btn-outline-danger linkButton ml-5" to={`/applications/${userId}`}>Cancel</Link>
					</div>
				</form>
			</section>
		</>
	)
}
export default TaskFormPage;