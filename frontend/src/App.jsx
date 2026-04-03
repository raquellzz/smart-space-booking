import { Routes, Route } from 'react-router-dom';
import './App.css';
import Login from './pages/Login';
import Admin from './pages/adminPages/Admin';
import CadastroSala from './pages/adminPages/CadastroSala';  
//import Home from './Home';

function App() {
  return (
    <div className="app-container">
      <Routes>
        <Route path="/" element={<Login />} />
        {/*<Route path="/home" element={<Home />} />*/}
        <Route path="/admin" element={<Admin />} />
        <Route path="/cadastrar-sala" element={<CadastroSala />} />
      </Routes>
    </div>
  );
}

export default App;