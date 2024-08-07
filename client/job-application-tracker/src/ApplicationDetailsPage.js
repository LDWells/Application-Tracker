import Application from "./Application";
import {useState, useEffect} from 'react';
import {Link} from 'react-router-dom';
import TaskList from "./TaskList";

function ApplicationDetailsPage()
{


	
	return (
		<>
			{/* <h1>Detailed Application page</h1>
			<p>This page will have an Applicaiton component to display, as well as a Tasks component, and possilby calendar eventually.
			The Application component is the application in detailed view. The Tasks component will be a list of Tasks related to the application</p> */}
			<section>
				<Application/>
			</section>
			<hr></hr>
			<section>
				<TaskList/>
			</section>
		</>
	)
}
export default ApplicationDetailsPage;