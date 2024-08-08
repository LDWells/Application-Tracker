import Application from "./Application";
import {useState, useEffect} from 'react';
import {Link, useParams} from 'react-router-dom';
import TaskList from "./TaskList";

function ApplicationDetailsPage()
{
	const {applicationId} = useParams();
	return (
		<>
			<section>
				<Application applicationId={parseInt(applicationId)}/>
			</section>
			<hr></hr>
			<section>
				<TaskList applicationId={parseInt(applicationId)}/>
			</section>
		</>
	)
}
export default ApplicationDetailsPage;