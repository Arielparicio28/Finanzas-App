import "./App.css";

import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from "./components/NavBar.tsx";
import Home from "./pages/Home.tsx";
import UserList from "./pages/UserList.tsx";
import UserDetail from "./components/UserDetail.tsx";

function App() {
  return (
    <>
    <Router>
        <Navbar />
        <Routes>
        <Route path='/' element={<Home />} />
          <Route path='/users' element={<UserList />} />
          <Route path='/users/:id' element = {<UserDetail/>}/>
          {/* agregar más rutas aquí */}
        </Routes>
    </Router>
  
    </>
  );
}

export default App;