import {useState, useEffect} from 'react';
import StatusColor from './StatusColor';

const APPLICATION_DTO_DEFAULT = {
	companyId: 1,
	companyName: 'Tech Corp',
	campanyAddress: '123 Tech Lane, Silicon Valley, CA',
	jobId: 1,
	jobTitle: 'Software Engineer',
	jobDescription: 'Develop and maintain web applications.',
	applicationId: 1,
	userId: 2,
	applicationDate: '01/15/2023',
	appliedOn: 'LinkedIn',
	status: 'OFFER'
};

function Application()
{

	const [application, setApplication] = useState(APPLICATION_DTO_DEFAULT);

	const statusColor = (status) => {
		if (status === "APPLIED")
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
		else if (status === "OFFER")
		{
			return <span className='text-success'>{status}</span>;
		}
	};

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
		</>
	)
}
export default Application;