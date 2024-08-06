
import {Link} from 'react-router-dom';

//Template used from: https://mdbootstrap.com/docs/standard/extended/login/#section-6
//Well need to research if its ok to use the template, Curtis did modify it by changing
// a -> Link, and changed certain styling, removed certain aspects.

function LogInPage()
{
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
									<div data-mdb-input-init className="form-outline form-white mb-4">
										<label className="form-label" for="email">Email</label>
										<input type="email" id="email" className="form-control form-control-lg"  placeholder='example@gmail.com' />
									</div>
									<div data-mdb-input-init className="form-outline form-white mb-4">
										<label className="form-label" for="password">Password</label>
										<input type="password" id="password" className="form-control form-control-lg" />
									</div>
									{/* Option for forgot password, probably won't use */}
									{/* <p className="small mb-5 pb-lg-2"><a className="text-white-50" href="#!">Forgot password?</a></p> */}
									<button className="btn btn-outline-light btn-lg px-5" type="submit">Login</button>
									<div className="d-flex justify-content-center text-center">
										<Link className="btn btn-outline-light linkButton mt-5" to={"/"}>Log In With Google</Link>
									</div>
								</div>
								<div>
									<Link to={"/"} className="btn btn-outline-light linkButton">Sign Up</Link>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	)
}
export default LogInPage;