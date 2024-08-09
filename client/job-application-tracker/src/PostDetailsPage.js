
import {Link, useNavigate, useParams} from 'react-router-dom';
import Post from './Post';
import CommentList from './CommentList';

function PostDetailsPage()
{
	const {postId} = useParams();
	const navigate = useNavigate();

	const handleDeletePost = () => {
		if (window.confirm(`Are you sure you want delete this post?`))
		{
			const token = sessionStorage.getItem('token');
			const init2 = {
				method: 'DELETE',
				headers: {
					'Authorization': `Bearer ${token}`,
					},
			};
			fetch(`http://localhost:8080/api/post/${postId}`, init2)
			.then(response => {
				if (response.status === 204)
				{
					navigate('/community');
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
			<section>
				<Post postId={parseInt(postId)}/>
				<hr></hr>
				<Link className='commentOnButton btn btn-outline-primary' to={`/comment/add/${postId}`}>Comment</Link>
				{JSON.stringify(sessionStorage.getItem('authorities')).includes('ROLE_ADMIN') &&
					<button className='deletePostButton btn btn-outline-danger' onClick={() => handleDeletePost()}>Delete Post</button>
				}
				<hr></hr>
				<CommentList postId={parseInt(postId)}/>
			</section>
		</>
	)
}
export default PostDetailsPage;