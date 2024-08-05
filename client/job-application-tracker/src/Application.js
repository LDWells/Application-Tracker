import {useState, useEffect} from 'react';

const APPLICATION_DEFAULT = {
	userId: 1,
	jobId: 1,
	applicationDate: '',
	status: 'Applied'
};

const JOB_DEFAULT = {
	companyId: 1,
	title: 'title',
	description: 'description'
};

const COMPANY_DEFAULT = {
	name: 'company',
	address: 'address'
};



function Application()
{

	const [application, setApplication] = useState(APPLICATION_DEFAULT);
	const [job, setJob] = useState(JOB_DEFAULT);
	const [company, setCompany] = useState(COMPANY_DEFAULT);

	return (
		<>
			<h1>Application</h1>
			<p>Component to display a singular application in detail</p>
			<section>
				<table className="table table-dark table-striped table-bordered">
					<thead>
						<th>Company Name</th>
						<th>Company Address</th>
						<th>Job Title</th>
						<th>Job Description</th>
						<th>Submission Date</th>
						<th>Status</th>
					</thead>
					<tbody>
						<td>{company.name}</td>
						<td>{company.address}</td>
						<td>{job.title}</td>
						<td>{job.description}</td>
						<td>{application.applicationDate}</td>
						<td>{application.status}</td>
					</tbody>
				</table>
			</section>
		</>
	)
}
export default Application;