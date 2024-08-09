
import { useState, useEffect } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";

const DEFAULT_POST = {
	userId: 0,
	title: '',
	summary: '',
	content: '',
	postDate: ''
}

function PostFormPage()
{
	const [post, setPost] = useState(DEFAULT_POST);
	const [errors, setErrors] = useState([]);
	const navigate = useNavigate();
	
	const handleChange = (event) =>
	{
		const newPost = {...post};
		newPost[event.target.name] = event.target.value;
		setPost(newPost);
	};

	const handlePostSubmit = (event) =>
	{
		event.preventDefault();
		post.userId = sessionStorage.getItem('appUserId');
		addPost();
	};

	const formatDate = (date) => {
		var d = new Date(date),
			month = '' + (d.getMonth() + 1),
			day = '' + d.getDate(),
			year = d.getFullYear();
	
		if (month.length < 2) 
			month = '0' + month;
		if (day.length < 2) 
			day = '0' + day;
	
		return [year, month, day].join('-');
	}

	const addPost = () =>
	{
		post.postDate = formatDate(Date.now());
		const url = `http://localhost:8080/api/post`;
		const token = sessionStorage.getItem('token');
		const init = {
			method: 'POST',
			headers: {
				'Authorization': `Bearer ${token}`,
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(post)
		};
		fetch(url, init)
		.then(response => {
			if (response.status === 201 || response.status === 400)
			{
				return response.json();
			}
			else
			{
				return Promise.reject(`Unexcpected status code: ${response.status}`);
			}
		})
		.then(data => {
			if (data.id)
			{
				navigate(`/community`);
			}
			else
			{
				setErrors(data);
			}
		})
		.catch(console.log)
  	};

	return (
		<>
			<section id="formContainer" className="container">
				<h2 id="formHeading" className="center">Add a Post</h2>
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
				<form onSubmit={handlePostSubmit} id="form" className="formContainer">
					<fieldset className="form-group">
						<label htmlFor="title" className="bs-dark-text-emphasis">Post Title:</label>
						<input type="text" id="title" className="form-control" name="title"
							value={post.title}
							onChange={handleChange}/>
					</fieldset>
					<fieldset className="form-group">
						<label htmlFor="summary">Post Summary:</label>
						<input type="text" id="summary" className="form-control" name="summary"
							value={post.summary}
							onChange={handleChange}/>
					</fieldset>
					<fieldset className="form-group">
						<label htmlFor="content">What you want to say:</label>
						<input type="text" id="content" className="form-control" name="content"
							value={post.content}
							onChange={handleChange}/>
					</fieldset>
					<div className="mt-4 mb-5 center">
						<button className="btn btn-outline-primary" type="submit" id="formSubmitButton">Add Post</button>
						<Link className="btn btn-outline-danger linkButton ml-5" to={`/community`}>Cancel</Link>
					</div>
				</form>
			</section>
		</>
	)
}
export default PostFormPage;