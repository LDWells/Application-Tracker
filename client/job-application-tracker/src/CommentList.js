
import {useState, useEffect} from 'react';
import {Link, useNavigate, useParams} from 'react-router-dom';

const DEFAULT_COMMENT = {
	id: 0,
	postId: 0,
	userId: 0,
	username: '',
	content: '',
	commentDate: ''
}

function CommentList({postId})
{

	const [comments, setComments] = useState([DEFAULT_COMMENT]);
	const navigate = useNavigate();
	const init = {
		method: 'GET'
	};
	useEffect( () => {
		fetch(`http://localhost:8080/api/comment/${postId}`, init)
		.then(response => {
			if (response.status === 200)
			{
				return response.json();
			}
			else
			{
				return Promise.reject(`Unexpected status code: ${response.status}`);
			}
		})
		.then(data => {
			if (data)
			{
				setComments(data);
			}
			else
			{
				console.log("NO DATA");
			}
		})
		.catch(console.log)
	},[]); 

	const handleDeleteComment = (commentId) => {
		if (window.confirm(`Are you sure you want delete this comment?`))
			{
				const token = sessionStorage.getItem('token');
				const init2 = {
					method: 'DELETE',
					headers: {
						'Authorization': `Bearer ${token}`,
						},
				};
				fetch(`http://localhost:8080/api/comment/${commentId}`, init2)
				.then(response => {
					if (response.status === 204)
					{
						const newComments = comments.filter(c => c.id !== commentId);
						setComments(newComments);
						navigate(`/community/${postId}`)
					}
					else
					{
						return Promise.reject(`Unexpected status code: ${response.status}`);
					}
				})
				.catch(console.log);
			}
		};

	return (
		<>
			{comments.map( c =>
				<section key={c.id} className='commentContainer commentBox'>
					<h4 className='commentBoxText'>Details</h4>
					<h6 className='commentBoxText'>Commented on: {c.commentDate}</h6>
					<h6 className='commentBoxText'>Posted by: {c.username}</h6>
					<hr></hr>
					<h4 className='commentBoxText'>Comment</h4>
					<h6 className='commentBoxText'>{c.content}</h6>
					{JSON.stringify(sessionStorage.getItem('authorities')).includes('ROLE_ADMIN') &&
					<button className='btn btn-outline-danger commentBoxText' onClick={() => handleDeleteComment(c.id)}>Delete Comment</button>
					}
				</section>
			)}
		</>
	)
}
export default CommentList;