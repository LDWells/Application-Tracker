
import {Link} from 'react-router-dom';

function HomePage()
{
	return (
		<>
			<h1 className="center">Welcome</h1>
			<hr></hr>
			<div className="center">
				<Link className=" btn btn-outline-primary center homeButton" to={"/login"}>Log In</Link>
			</div>
		</>
	)
}
export default HomePage;