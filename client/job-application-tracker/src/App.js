
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
import LogInPage from "./LogInPage";
import RegisterPage from "./RegisterPage";
import TaskListPage from "./TaskListPage";

function App() {
  return (
    <>
      <Router>
        <NavBar/>
        <Routes>
          <Route path="/" element={<HomePage/>}/>
          <Route path="/applications/:userId" element={<ApplicationListPage/>}/>
          <Route path="/application/:applicationId" element={<ApplicationDetailsPage/>}/>
          <Route path="/application/add" element={<ApplicationFormPage/>}/>
          <Route path="/application/edit/:applicationId" element={<ApplicationFormPage/>}/>
          <Route path="/tasks" element={<TaskListPage/>}/>
          <Route path="/task/add" element={<TaskFormPage/>}/>
          <Route path="/task/edit/:id" element={<TaskFormPage/>}/>
          <Route path="/community" element={<CommunityPage/>}/>
          <Route path="/post/:id" element={<PostDetailsPage/>}/>
          <Route path="/post/add" element={<PostFormPage/>}/>
          <Route path="/post/edit/:id" element={<PostFormPage/>}/>
          <Route path="/login" element={<LogInPage/>}/>
          <Route path="/register" element={<RegisterPage/>}/>
          <Route path="*" element={<NotFoundPage/>}/>
        </Routes>
      </Router>
    </>
  );
}
export default App;
