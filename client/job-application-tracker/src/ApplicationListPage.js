
import {useState, useEffect} from 'react';
import {Link} from 'react-router-dom';
//Temp code

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

const APPLICATION_DTO_DEFAULT2 = {
	companyId: 2,
	companyName: 'Biz Solutions',
	campanyAddress: '456 Business St, New York, NY',
	jobId: 2,
	jobTitle: 'Business Analyst',
	jobDescription: 'Analyze business processes and requirements.',
	applicationId: 2,
	userId: 3,
	applicationDate: '02/20/2023',
	appliedOn: 'Indeed',
	status: 'REJECTED'
};

const APPLICATION_DTOS_DEFAULT = [
	APPLICATION_DTO_DEFAULT,
	APPLICATION_DTO_DEFAULT2
];


function ApplicationListPage()
{

	const [applications, setApplications] = useState(APPLICATION_DTOS_DEFAULT);

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
	}

	return (
		<>
			{/* <h1>Application List Page</h1>
			<p>Component to display list of applications in non detailed view</p>
			<p>this will have for each application, the company name, the job title, the submission date, and the status</p> */}
			<section className='applicationListContainer'>
				{applications.map(a => 
					<div key={a.id} className='applicationListBox mb-5'>
						<h1 className='applicationListBoxText'>{a.jobTitle}</h1>
						
						<h6 className='applicationListBoxText'>{a.companyName}---{a.applicationDate}</h6>
						<h5 className='applicationListBoxText'>
						Status: {statusColor(a.status)}
						<Link className="btn btn-outline-light applicationListButton" to={`/application/${a.id}`}>View Application</Link>
						</h5>
					</div>
				)}
			</section>
		</>
	)
}
export default ApplicationListPage;