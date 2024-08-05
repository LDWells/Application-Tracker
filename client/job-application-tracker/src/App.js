
import { BrowserRouter as Router } from "react-router-dom";
import {Routes, Route} from "react-router-dom";
import NavBar from "./NavBar"
import HomePage from "./HomePage"
import NotFoundPage from "./NotFoundPage";
import ApplicationListPage from "./ApplicationListPage";
import ApplicationDetailsPage from "./ApplicationDetailsPage";
import ApplicationFormPage from "./ApplicationFormPage";
import TaskFormPage from "./TaskFormPage";
import CommunityPage from "./CommunityPage";
import PostDetailsPage from "./PostDetailsPage"
import PostFormPage from "./PostFormPage";

function App() {
  return (
    <>
      <Router>
        <NavBar/>
        <Routes>
          <Route path="/" element={<HomePage/>}/>
          <Route path="/applications" element={<ApplicationListPage/>}/>
          <Route path="/application/:id" element={<ApplicationDetailsPage/>}/>
          <Route path="/application/add" element={<ApplicationFormPage/>}/>
          <Route path="/application/edit/:id" element={<ApplicationFormPage/>}/>
          <Route path="/task/add" element={<TaskFormPage/>}/>
          <Route path="/task/edit/:id" element={<TaskFormPage/>}/>
          <Route path="/community" element={<CommunityPage/>}/>
          <Route path="/post/:id" element={<PostDetailsPage/>}/>
          <Route path="/post/add" element={<PostFormPage/>}/>
          <Route path="/post/edit/:id" element={<PostFormPage/>}/>

          <Route path="*" element={<NotFoundPage/>}/>
        </Routes>
      </Router>
    </>
  );
}
export default App;
