
import {Link} from 'react-router-dom';

function HomePage()
{
	return (
		<>
			<h1 className="center">Home Page</h1>
			<div className="center">
				<Link className=" btn btn-outline-light center homeButton" to={"/login"}>Log In</Link>
			</div>
		</>
	)
}
export default HomePage;