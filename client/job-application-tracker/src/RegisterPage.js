
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

function RegisterPage()
{

	const [user, setUser] = useState(DEFAULT_USER);
	const [errors, setErrors] = useState([]);

	const url = 'http://localhost:8080/api/user'
	const navigate = useNavigate();


	const handleSubmit = (event) => {
		event.preventDefault();
		//attempt to log in
		handleRegister(user.username, user.password);
	}

	const handleRegister = (username, password) => {
		const init = {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({username, password})
		};
		fetch(`${url}/register`, init)
		.then(response => {
			if (response.status === 201)
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
				navigate('/login')
			}
			else
			{
				setErrors(data);
			}
		})
		.catch(console.log)
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
									<h2 className="fw-bold mb-2 text-uppercase">REGISTER</h2>
									<p className="text-white-50 mb-5">Please enter a username and password!</p>
									<form onSubmit={handleSubmit}>
										<fieldset data-mdb-input-init className="form-outline form-white mb-4">
											<label className="form-label" htmlFor="username">Username</label>
											<input type="text" id="username" name='username' value={user.username} onChange={handleChange} className="form-control form-control-lg"/>
										</fieldset>
										<fieldset data-mdb-input-init className="form-outline form-white mb-4">
											<label className="form-label" htmlFor="password">Password</label>
											<input type="password" id="password" name='password' value={user.password} onChange={handleChange} className="form-control form-control-lg" />
										</fieldset>
										<button className="btn btn-outline-light btn-lg px-5" type="submit">Register</button>
									</form>
								</div>
								<div>
									<Link to={"/login"} className="btn btn-outline-light linkButton">Have an Account?</Link>
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
export default RegisterPage;