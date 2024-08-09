
import {useState, useEffect} from 'react';
import {Link, useParams} from 'react-router-dom';

const DEFAULT_POST = {
	id: 0,
	userId: 0,
	title: '',
	summary: '',
	content: '',
	postDate: ''
}

function Post({postId})
{
	const [post, setPost] = useState(DEFAULT_POST);

	const init = {
		method: 'GET'
	};
	useEffect( () => {
		fetch(`http://localhost:8080/api/post/${postId}`, init)
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
				setPost(data);
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
			<section className='postContainer postBox'>
				<h2 className='postBoxText'>{post.title}</h2>
				<hr></hr>
				<h3 className='postBoxText'>Summary</h3>
				<h6 className='postBoxText'>{post.summary}</h6>
				<hr></hr>
				<h3 className='postBoxText'>Details</h3>
				<h6 className='postBoxText'>{post.content}</h6>
				<hr></hr>
				<h6 className='postBoxText'>Posted on: {post.postDate}</h6>
			</section>
		</>
	)
}
export default Post;