import {useState, useEffect} from 'react';
import {Link} from 'react-router-dom';
import StatusColor from './StatusColor';

function Application({applicationId})
{
	const [application, setApplication] = useState([]);

	const token = localStorage.getItem('token');
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
	

	return (
		<>
			<section className='applicationContainer'>
				<div key={application.id} className='applicationBox mb-5'>	
					<h1 className='applicationBoxText center'>Company</h1>
					<h4 className='applicationBoxText center text-dark-50 text-dark'>{application.companyName}</h4>
					<h6 className='applicationBoxText center text-dark-50 text-dark'>{application.campanyAddress}</h6>
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
			<section className='center'><Link className="btn btn-outline-light linkButton" to={"/task/add"}>Add a Task</Link></section>
			
		</>
	)
}
export default Application;