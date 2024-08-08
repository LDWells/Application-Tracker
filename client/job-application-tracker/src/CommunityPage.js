import {useState, useEffect} from 'react';
import {Link, useParams} from 'react-router-dom';


function CommunityPage()
{

	const [communityPosts, setCommunityPosts] = useState([]);

	const token = localStorage.getItem('token');
	const init = {
		method: 'GET',
		headers: {
			'Authorization': `Bearer ${token}`,
			},
		};//Authentication header for get

	useEffect( () => {
		fetch(`http://localhost:8080/api/community`, init)
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
			<section>
			<h1 className='center'>Applications</h1>
					{communityPosts.map(cp => 
						<div key={cp.id} className='applicationListBox mb-5'>
							<h1 className='applicationListBoxText'>{cp.title}</h1>
							<h6 className='applicationListBoxText'>{cp.content}</h6>
							<h5 className='applicationListBoxText'>
							{cp.postDate}
							<Link className="btn btn-outline-light applicationListButton" to={`/community/${cp.id}`}>View Post</Link>
							</h5>
						</div>
					)}
			</section>
		</>
	)
}
export default CommunityPage;