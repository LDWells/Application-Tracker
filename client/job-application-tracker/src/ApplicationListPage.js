
import {useState, useEffect} from 'react';
import {Link} from 'react-router-dom';
//Temp code




function ApplicationListPage()
{

	const [applications, setApplications] = useState(APPLICATIONS_DEFAULT);
	const [jobs, setJobs] = useState(JOBS_DEFAULT);
	const [companies, setCompanies] = useState(COMPANIES_DEFAULT);

	const findJobById = (jobId) => {
		return jobs.find(j => j.id === jobId)
	}

	const findCompanyById = (companyId) => {
		return companies.find(c => c.id === companyId)
	}

	return (
		<>
			<h1>Application List Page</h1>
			<p>Component to display list of applications in non detailed view</p>
			<p>this will have for each application, the company name, the job title, the submission date, and the status</p>
			<section className='container'>
				{applications.map(a => 
					<div key={a.id}>
						{/* <h3>{findCompanyById(findJobById(a.jobId)).name}</h3>
						<h3>{findJobById(a.jobId).title}</h3> */}
						<h3>{a.applicationDate}</h3>
						<h3>{a.status}</h3>
					</div>
				)}
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
			</section>
		</>
	)
}
export default ApplicationListPage;