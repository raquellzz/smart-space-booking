import { Navigate, Route, Routes } from "react-router-dom";
import { AuthProvider } from "./contexts/AuthContext";
import Admin from "./pages/adminPages/Admin";
import CadastroSala from "./pages/adminPages/CadastroSala";
import EditarSala from "./pages/adminPages/EditarSala";
import Login from "./pages/login/Login";
import Home from "./pages/Home";
import Reserva from "./pages/reserva/Reserva";
import ProtectedRoute from "./components/ProtectedRoute";
import AcessoNegado from "./pages/acessoNegado/acessoNegado";
import Perfil from "./pages/perfil/Perfil";
import Layout from "./components/Layout";

function App() {
  return (
    <AuthProvider>
      <div className="app-container">
        <Routes>
          {/* Rotas Públicas */}
          <Route path="/" element={<Navigate to="/login" replace />} />
          <Route path="/login" element={<Login />} />
          <Route path="/acesso-negado" element={<AcessoNegado />} />

          <Route element={<Layout />}>
            {/* Rotas exclusivas de USER e ADMIN */}
            <Route 
            path="/home" 
            element={
              <ProtectedRoute allowedRoles={['USER', 'ADMIN']}>
                <Home />
              </ProtectedRoute>
            } 
          />
            <Route
              path="/criar-reserva/:id"
              element={
                <ProtectedRoute allowedRoles={["USER", "ADMIN"]}>
                  <Reserva />
                </ProtectedRoute>
              }
            />

            <Route
              path="/perfil"
              element={
                <ProtectedRoute allowedRoles={["USER", "ADMIN"]}>
                  <Perfil />
                </ProtectedRoute>
              }
            />
            {/* Rotas Exclusivas de ADMIN */}
            <Route
              path="/admin"
              element={
                <ProtectedRoute allowedRoles={["ADMIN"]}>
                  <Admin />
                </ProtectedRoute>
              }
            />
            <Route
              path="/cadastrar-sala"
              element={
                <ProtectedRoute allowedRoles={["ADMIN"]}>
                  <CadastroSala />
                </ProtectedRoute>
              }
            />
            <Route
              path="/editar-sala/:id"
              element={
                <ProtectedRoute allowedRoles={["ADMIN"]}>
                  <EditarSala />
                </ProtectedRoute>
              }
            />
          </Route>
        </Routes>
      </div>
    </AuthProvider>
  );
}

export default App;
