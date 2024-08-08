
import { useState, useEffect } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";

const APPLICATION_DTO_DEFAULT = {
	companyName: '',
	companyAddress: '',
	jobTitle: '',
	jobDescription: '',
	userId: 0,
	applicationDate: '',
	appliedOn: '',
	status: 'APPLIED'
};

function ApplicationFormPage()
{

	const [application, setApplication] = useState(APPLICATION_DTO_DEFAULT);
	const [errors, setErrors] = useState([]);
	const navigate = useNavigate();
	const userId = localStorage.getItem('appUserId');
	const {applicationId} = useParams();
	const token = localStorage.getItem('token');
	const init = {
		method: 'GET',
		headers: {
			'Authorization': `Bearer ${token}`,
		}
	};
	useEffect( () => {
		if (applicationId)
		{
			fetch(`http://localhost:8080/api/application/details/${applicationId}`, init)
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
				setApplication(data);
			})
			.catch(console.log)
		}
		else
		{
			setApplication(APPLICATION_DTO_DEFAULT);
		}
	},[applicationId]);
	
	const handleChange = (event) =>
	{
		const newApplication = {...application};
		newApplication[event.target.name] = event.target.value;
		setApplication(newApplication);
	};

	const handleApplicationSubmit = (event) =>
	{
		event.preventDefault();
		application.userId = userId;
		// we want to either add or update here
		addApplication();
	};

	const addApplication = () =>
	{
		const url = 'http://localhost:8080/api/application/dto'
		const init = {
			method: 'POST',
			headers: {
				'Authorization': `Bearer ${token}`,
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(application)
		};
		fetch(url, init)
		.then(response => {
			if (response.status === 201 || response.status === 400)
			{
				return response.json();
			}
			else
			{
				return Promise.reject(`Unexcpected status code: ${response.status}`);
			}
		})
		.then(data => {
			if (data.applicationId)
			{

				navigate(`/applications/${userId}`);
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
				<h2 id="formHeading">{localStorage.getItem('updating') === true ? 'Update an Application' : 'Add an Application'}</h2>
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
				<form onSubmit={handleApplicationSubmit} id="form">
					<fieldset className="form-group">
						<label htmlFor="companyName" className="bs-dark-text-emphasis">Company Name:</label>
						<input type="text" id="companyName" className="form-control" name="companyName"
							value={application.companyName}
							onChange={handleChange}/>
					</fieldset>
					<fieldset className="form-group">
						<label htmlFor="companyAddress">Company Address:</label>
						<input type="text" id="companyAddress" className="form-control" name="companyAddress"
							value={application.companyAddress}
							onChange={handleChange}/>
					</fieldset>
					<fieldset className="form-group">
						<label htmlFor="jobTitle">Job Title:</label>
						<input type="text" id="jobTitle" className="form-control" name="jobTitle"
							value={application.jobTitle}
							onChange={handleChange}/>
					</fieldset>
					<fieldset className="form-group">
						<label htmlFor="jobDescription">Job Description:</label>
						<input type="text" id="jobDescription" className="form-control" name="jobDescription"
							value={application.jobDescription}
							onChange={handleChange}/>
					</fieldset>
					<fieldset className="form-group">
						<label htmlFor="applicationDate">Application Date:</label>
						<input type="date" id="applicationDate" className="form-control" name="applicationDate"
						value={application.applicationDate}
						onChange={handleChange}/>
					</fieldset>
					<fieldset className="form-group">
						<label htmlFor="appliedOn">Applied On:</label>
						<input type="text" id="appliedOn" className="form-control" name="appliedOn"
							value={application.appliedOn}
							onChange={handleChange}/>
					</fieldset>
					<fieldset className="form-group">
						<label htmlFor="status">Status</label>
						<select 
						id="status" 
						name="status" 
						value={application.status} 
						onChange={handleChange}>
							<option>APPLIED</option>
							<option>INTERVIEW</option>
							<option>OFFER</option>
							<option>REJECTED</option>
						</select>
					</fieldset>
					<div className="mt-4 center">
						<button className="btn btn-outline-light" type="submit" id="formSubmitButton">{localStorage.getItem('updating') === true ? 'Update Agent' : 'Add Agent'}</button>
						<Link className="btn btn-outline-danger linkButton ml-5" to={`/applications/${userId}`}>Cancel</Link>
					</div>
				</form>
			</section>
		</>
	)
}
export default ApplicationFormPage;