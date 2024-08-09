
import {Link} from 'react-router-dom';

function HomePage()
{

	let isLoggedIn = false;
	let test = localStorage.getItem('token');

	if (localStorage.getItem('token') === null){
		return (
			<>
				<h1 className="center">Home Page</h1>
				<div className="center" id="login-mode">
					<Link className="btn btn-outline-light center homeButton" to={"/login"}>Log Out</Link>
				</div>
			</>
		)
	}else{
		return (
			<>
				<h1 className="center">Home Page</h1>
				<div className="center" id="login-mode">
					<Link className="btn btn-outline-light center homeButton" to={"/login"}>Log In</Link>
				</div>
			</>
		)
	}


}
export default HomePage;