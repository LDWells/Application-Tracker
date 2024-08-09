
import {useState, useEffect} from 'react';
import {Link, useParams} from 'react-router-dom';

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
	const [users, setUsers] = useState([]);
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
				</section>
			)}
		</>
	)
}
export default CommentList;