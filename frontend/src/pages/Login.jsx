import { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { AuthContext } from "../contexts/AuthContext";
import "./Login.css";
import SSBLogo from "../assets/SSBLogo.png";

function Login() {
  const navigate = useNavigate();
  const { login } = useContext(AuthContext);
  const [email, setEmail] = useState("");
  const [nome, setNome] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault();
    if (email.trim() !== "" && nome.trim() !== "") {
      try {
        const response = axios.post(
          "http://localhost:8080/api/usuarios/acesso",
          {
            email: email,
            nome: nome,
          },
        );
        login(response.data);
        navigate("/home");
      } catch (error) {
        console.error("Erro ao fazer login:", error);
        alert("Falha ao conectar com o servidor.");
      }
    } else {
      alert("Por favor, preencha todos os campos!");
    }
  };

  return (
    <div className="login-container">
      <form className="login-box" onSubmit={handleLogin}>
        <img className="logo" src={SSBLogo} alt="Logo SSB" />

        <h4 className="subtitle">
          Acesse sua conta para gerenciar seus espaços.
        </h4>
        <div className="input-group">
          <label htmlFor="nome">Nome Completo</label>
          <input
            id="nome"
            type="text"
            value={nome}
            onChange={(e) => setNome(e.target.value)}
            placeholder="Digite seu nome"
            required
          />
        </div>

        <div className="input-group">
          <label htmlFor="email">Email Corporativo</label>
          <input
            id="email"
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="Digite seu email"
            required
          />
        </div>

        <button type="submit" className="login-button">
          Entrar
        </button>
      </form>
    </div>
  );
}

export default Login;
