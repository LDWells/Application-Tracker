
import {useState, useEffect} from 'react';
import {Link} from 'react-router-dom';
//Temp code
const APPLICATION_DEFAULT = {
	id: 1,
	userId: 1,
	jobId: 1,
	applicationDate: '01/01/2024',
	status: 'Applied'
};

const APPLICATION_DEFAULT2 = {
	id: 2,
	userId: 1,
	jobId: 2,
	applicationDate: '02/02/2024',
	status: 'Rejected'
};

const APPLICATIONS_DEFAULT = [
	APPLICATION_DEFAULT,
	APPLICATION_DEFAULT2
];

function ApplicationListPage()
{

	const [applications, setApplications] = useState(APPLICATIONS_DEFAULT);

	return (
		<>
			<h1>Application List Page</h1>
			<p>Component to display list of applications in non detailed view</p>
			<p>this will have for each application, the company name, the job title, the submission date, and the status</p>
			<section className='container'>
				<table className="table table-dark table-striped table-bordered">
					<thead>
						{/* <th>Company Name</th>
						<th>Job Title</th>
						<th>Submission Date</th>
						<th>Status</th> */}
						<th>Job Id</th>
						<th>Submission Date</th>
						<th>Status</th>
						<th>&nbsp;</th>
					</thead>
					<tbody>
					{applications.map( a => 
						<tr key={a.id}>
							<td>{a.jobId}</td>
							{/* <td>{job.title}</td> */}
							<td>{a.applicationDate}</td>
							<td>{a.status}</td>
							<td className='center'>
								<Link className="btn btn-outline-light linkButton" to={`/application/${a.id}`}>View Application Details</Link>
							</td>
						</tr>
					)}
					</tbody>
				</table>
			</section>
		</>
	)
}
export default ApplicationListPage;