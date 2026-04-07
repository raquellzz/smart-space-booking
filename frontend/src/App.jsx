import { Navigate, Route, Routes } from "react-router-dom";
import { AuthProvider } from "./contexts/AuthContext";
import Admin from "./pages/adminPages/Admin";
import CadastroSala from "./pages/adminPages/CadastroSala";
import EditarSala from "./pages/adminPages/EditarSala";
import Login from "./pages/Login";
import Reserva from "./pages/reserva/Reserva";

function App() {
  return (
    <AuthProvider>
      <div className="app-container">
        <Routes>
          <Route path="/" element={<Navigate to="/login" replace />} />
          <Route path="/login" element={<Login />} />
          {/* <Route path="/home" element={<Home />} /> */}
          <Route path="/criar-reserva/:id" element={<Reserva />} />
          <Route path="/admin" element={<Admin />} />
          <Route path="/cadastrar-sala" element={<CadastroSala />} />
          <Route path="/editar-sala/:id" element={<EditarSala />} />
        </Routes>
      </div>
    </AuthProvider>
  );
}

export default App;
