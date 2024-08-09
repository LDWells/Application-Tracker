
import {Link, useNavigate} from 'react-router-dom';
import {useState} from 'react';

//Template used from: https://mdbootstrap.com/docs/standard/extended/login/#section-6
//Well need to research if its ok to use the template, Curtis did modify it by changing
// a -> Link, and changed certain styling, removed certain aspects.

const DEFAULT_USER = {
	appUserId: 0,
	username: '',
	password: '',
	disabled: false,
	roles: [""]
};

function LogInPage()
{

	const [user, setUser] = useState(DEFAULT_USER);
	const [errors, setErrors] = useState([]);

	const url = 'http://localhost:8080/api/user'
	const navigate = useNavigate();


	const handleSubmit = (event) => {
		event.preventDefault();
		//attempt to log in
		handleLogIn(user.username, user.password);
	}

	const handleLogIn = (username, password) => {
		const init = {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({username, password})
		};
		fetch(`${url}/authenticate`, init)
		.then(response => {
			if (response.status === 200)
			{
				return response.json();
			}
			else
			{
				return Promise.reject(`Unexcpected status code: ${response.status}`);
			}
		})
		.then(data => {
			if (data)
			{
				sessionStorage.setItem('token', data.jwt_token);
				sessionStorage.setItem('authorities', JSON.parse(atob(data.jwt_token.split('.')[1])).authorities);
				console.log(sessionStorage.getItem('authorities'));
				getUserData(username);
			}
			else
			{
				setErrors(data);
			}
		})
		.catch(console.log)
	};

	const getUserData = (username) => {
		const token = sessionStorage.getItem('token');
		const init = {
			method: 'POST',
			headers: {
				'Authorization': `Bearer ${token}`,
				'Content-Type': 'application/json'
			},
			body: username
		};//Authentication header for get
		fetch(`${url}`, init)
		.then(response => {
			if (response.status === 200)
			{
				return response.json();
			}
			else
			{
				return Promise.reject(`Unexcpected status code: ${response.status}`);
			}
		})
		.then(data => {
			if (data.appUserId)
			{
				sessionStorage.setItem('appUserId', data.appUserId);
				window.dispatchEvent(new Event('userIdUpdate'));
				navigate(`/applications/${data.appUserId}`);
			}
			else
			{
				setErrors(data);
			}
		})
		.catch(console.log)
		let test = localStorage.getItem('token');
	};

	const handleChange = (event) => {
		const newUser = {...user};
		newUser[event.target.name] = event.target.value;
		setUser(newUser);
	}

	return (
		<section>
			<div className="container py-5 h-100">
				<div className="row d-flex justify-content-center align-items-center h-100">
					<div className="col-12 col-md-8 col-lg-6 col-xl-5">
						<div className="card bg-dark text-white LogInBoxStyle">
							<div className="card-body p-5 text-center">
								<div className="mb-md-1 mt-md-4 pb-5">
									<h2 className="fw-bold mb-2 text-uppercase">Login</h2>
									<p className="text-white-50 mb-5">Please enter your login and password!</p>
									<form onSubmit={handleSubmit}>
										<fieldset data-mdb-input-init className="form-outline form-white mb-4">
											<label className="form-label" htmlFor="username">Username</label>
											<input type="text" id="username" name='username' value={user.username} onChange={handleChange} className="form-control form-control-lg"/>
										</fieldset>
										<fieldset data-mdb-input-init className="form-outline form-white mb-4">
											<label className="form-label" htmlFor="password">Password</label>
											<input type="password" id="password" name='password' value={user.password} onChange={handleChange} className="form-control form-control-lg" />
										</fieldset>
										{/* Option for forgot password, probably won't use */}
										{/* <p className="small mb-5 pb-lg-2"><a className="text-white-50" href="#!">Forgot password?</a></p> */}
										<button className="btn btn-outline-light btn-lg px-5" type="submit">Login</button>
										<div className="d-flex justify-content-center text-center">
											<Link className="btn btn-outline-light linkButton mt-5" to={"/"}>Log In With Google</Link>
										</div>
									</form>
								</div>
								<div>
									<Link to={"/register"} className="btn btn-outline-light linkButton">Register</Link>
								</div>
							</div>
							{errors.length > 0 && (
							<div id="error" className='alert alert-danger'>
								<p>The following errors occured: </p>
								<ul>
								{errors.map(error => 
								<li key={error}>{error}</li>
								)}
								</ul>
							</div>
							)}
						</div>
					</div>
				</div>
			</div>
		</section>
	)
}
export default LogInPage;