
import { useState, useEffect } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";

const DEFAULT_COMMENT = {
	postId: 0,
	userId: 0,
	username: '',
	content: '',
	commentDate: ''
}

function CommentFormPage()
{
	const [comment, setComment] = useState(DEFAULT_COMMENT);
	const [errors, setErrors] = useState([]);
	const navigate = useNavigate();
	const {postId} = useParams();

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

	const handleChange = (event) =>
		{
			const newComment = {...comment};
			newComment[event.target.name] = event.target.value;
			setComment(newComment);
		};
	
		const handleCommentSubmit = (event) =>
		{
			event.preventDefault();
			comment.userId = parseInt(sessionStorage.getItem('appUserId'));
			comment.commentDate = formatDate(Date.now());
			comment.postId = postId;
			addComment();
		};
	
		const addComment = () =>
		{
			const token = sessionStorage.getItem('token');
			const url = 'http://localhost:8080/api/comment'
			const init = {
				method: 'POST',
				headers: {
					'Authorization': `Bearer ${token}`,
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(comment)
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
					navigate(`/community/${postId}`);
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
				<h2 id="formHeading" className="center">Comment</h2>
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
				<form onSubmit={handleCommentSubmit} id="form" className="formContainer">
					<fieldset className="form-group">
						<label htmlFor="content" className="bs-dark-text-emphasis">Comment</label>
						<input type="text" id="content" className="form-control" name="content"
							value={comment.content}
							onChange={handleChange}/>
					</fieldset>
					<fieldset className="form-group">
						<label htmlFor="username" className="bs-dark-text-emphasis">Username</label>
						<input type="text" id="username" className="form-control" name="username"
							value={comment.username}
							onChange={handleChange}/>
					</fieldset>
					<div className="mt-4 center">
						<button className="btn btn-outline-primary" type="submit" id="formSubmitButton">Post Comment</button>
						<Link className="btn btn-outline-danger linkButton ml-5" to={`/community/${postId}`}>Cancel</Link>
					</div>
				</form>
			</section>
		</>
	)
}
export default CommentFormPage;