import { useState, useEffect } from "react";
import {Link} from "react-router-dom";

function NavBar()
{
	const [userId, setUserId] = useState();
	useEffect ( () => {
		window.addEventListener('userIdUpdate', () => {
			setUserId(parseInt(sessionStorage.getItem('appUserId')));
		})
	},[]);

	return (
		<nav className="nav bg-dark">
			<header className="NavBarTitle">Job Application Tracker</header>
			<Link className="btn btn-outline-light NavBarText" to={"/"}>Home</Link>
			<div className="dropdown">
				<button className="btn btn-outline-light dropdown-toggle NavBarText" type="button" id="dropdownMenu" data-toggle="dropdown">Applications</button>
				<div className="dropdown-menu bg-dark">
					<Link className="dropdown-item dropdownText NavBarDropdownItems" to={`/applications/${userId}`}>View Applicaitons</Link>
					<li><hr className="dropdown-divider"/></li>
					<Link className="dropdown-item dropdownText NavBarDropdownItems" to={"/application/add"}>Add an Application</Link>
					<li><hr className="dropdown-divider"/></li>
					<Link className="dropdown-item dropdownText NavBarDropdownItems" to={"/tasks"}>View Tasks</Link>
				</div>
			</div>
			<div className="dropdown">
				<button className="btn btn-outline-light dropdown-toggle NavBarText" type="button" id="dropdownMenu" data-toggle="dropdown">Community</button>
				<div className="dropdown-menu bg-dark">
					<Link className="dropdown-item dropdownText NavBarDropdownItems" to={"/community"}>Community Posts</Link>
					<li><hr className="dropdown-divider"/></li>
					<Link className="dropdown-item dropdownText NavBarDropdownItems" to={"/post/add"}>Add a Community Post</Link>
				</div>
			</div>
			<Link className="btn btn-outline-light NavBarText" to={"/login"}>Log In</Link>
		</nav>
	)
}
export default NavBar;