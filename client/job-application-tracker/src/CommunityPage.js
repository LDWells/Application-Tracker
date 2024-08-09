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

function CommunityPage()
{

	const [communityPosts, setCommunityPosts] = useState([DEFAULT_POST]);

	const init = {
		method: 'GET'
		};//Authentication header for get

	useEffect( () => {
		fetch(`http://localhost:8080/api/post`, init)
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
				setCommunityPosts(data);
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
			<section className='postContainer'>
			<h1 className='center'>Community Posts</h1>
			<hr></hr>
					{communityPosts.map(cp => 
						<div key={cp.id} className='postListBox mb-5'>
							<h1 className='postListBoxText'>{cp.title}</h1>
							<h6 className='postListBoxText'>{cp.summary}</h6>
							<h6 className='postListBoxText'>
							{cp.postDate}
							<Link className="btn btn-outline-primary postListButton" to={`/community/${cp.id}`}>View Post</Link>
							</h6>
						</div>
					)}
			</section>
		</>
	)
}
export default CommunityPage;