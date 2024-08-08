import {useState, useEffect} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import StatusColor from './StatusColor';

const APPLICATION_DTO_DEFAULT = {
	applicationId: 0,
	companyName: '',
	companyAddress: '',
	jobTitle: '',
	jobDescription: '',
	userId: 0,
	applicationDate: '',
	appliedOn: '',
	status: 'APPLIED'
};

function Application({applicationId})
{
	const [application, setApplication] = useState(APPLICATION_DTO_DEFAULT);
	const navigate = useNavigate();
	const token = sessionStorage.getItem('token');
	const init = {
		method: 'GET',
		headers: {
			'Authorization': `Bearer ${token}`,
			},
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
					if (data)
					{
						setApplication(data);
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
				setApplication([]);
			}
	},[]);
	
	const handleDeleteApplication = () => {
		if (window.confirm(`Are you sure you want to your ${application.companyName} application?`))
		{
			const init2 = {
				method: 'DELETE',
				headers: {
					'Authorization': `Bearer ${token}`,
					},
			};
			fetch(`http://localhost:8080/api/application/${applicationId}`, init2)
			.then(response => {
				if (response.status === 204)
				{
					const tempUserId = sessionStorage.getItem('appUserId');
					navigate(`/applications/${tempUserId}`)
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
			<section className='applicationContainer'>
				<div key={application.id} className='applicationBox mb-5'>	
					<h1 className='applicationBoxText center'>Company</h1>
					<h4 className='applicationBoxText center text-dark-50 text-dark'>{application.companyName}</h4>
					<h6 className='applicationBoxText center text-dark-50 text-dark'>{application.companyAddress}</h6>
					<hr></hr>
					<h1 className='applicationBoxText center'>Job</h1>	
					<h4 className='applicationBoxText center text-dark-50 text-dark'>{application.jobTitle}</h4>
					<h6 className='applicationBoxText center text-dark-50 text-dark'>{application.jobDescription}</h6>
					<hr></hr>	
					<h1 className='applicationBoxText center'>Application</h1>
					<h6 className='applicationBoxText center text-dark-50 text-dark'>Date: {application.applicationDate}</h6>
					<h5 className='applicationBoxText center text-dark-50 text-dark'>Status: {<StatusColor status={application.status}/>}</h5>
				</div>
			</section>
			<hr></hr>
			<section className='center'>
				<Link className="btn btn-outline-primary linkButton" to={`/task/add/${application.applicationId}`}>Add a Task</Link>
				<Link className="btn btn-outline-secondary linkButton ml-3" to={`/application/edit/${application.applicationId}`}>Edit Application</Link>
				<button className="btn btn-outline-danger linkButton ml-3" onClick={handleDeleteApplication}>Delete Application</button>
			</section>
			
		</>
	)
}
export default Application;