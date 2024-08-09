
import {useState, useEffect} from 'react';
import {Link, useParams} from 'react-router-dom';
import Post from './Post';
import CommentList from './CommentList';

const DEFAULT_POST = {
	id: 0,
	userId: 0,
	title: '',
	summary: '',
	content: '',
	postDate: ''
}

function PostDetailsPage()
{
	const {postId} = useParams();
	
	return (
		<>
			<section>
				<Post postId={parseInt(postId)}/>
				<hr></hr>
				<Link className='commentOnButton btn btn-outline-primary' to={`/comment/add/${postId}`}>Comment On</Link>
				<hr></hr>
				<CommentList postId={parseInt(postId)}/>
			</section>
		</>
	)
}
export default PostDetailsPage;