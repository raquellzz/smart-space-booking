import { Routes, Route } from 'react-router-dom';
import './App.css';
import Login from './pages/Login'; 
//import Home from './Home';

function App() {
  return (
    <div className="app-container">
      <Routes>
        <Route path="/" element={<Login />} />
        {/*<Route path="/home" element={<Home />} />*/}
      </Routes>
    </div>
  );
}

export default App;