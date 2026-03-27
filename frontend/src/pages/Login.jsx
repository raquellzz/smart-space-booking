import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css';
import SSBLogo from '../assets/SSBLogo.png';

function Login() {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');

  const handleLogin = (e) => {
    e.preventDefault();
    if (email.trim() !== "") { 
      navigate('/home'); 
    } else {
      alert("Por favor, digite seu email!");
    }
  };

  return (
    <div className="login-container">
      <form className="login-box" onSubmit={handleLogin}>
        <img className="logo" src={SSBLogo} alt="Logo SSB" />

        <h4 className="subtitle">Acesse sua conta para gerenciar seus espaços.</h4>
        
        <div className="input-group">
          <label htmlFor="email">Email</label>
          <input 
            id="email"
            type="email" 
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="Digite seu email" 
            required
          />
        </div>

        <button type="submit" className="login-button">Entrar</button>
      </form>
    </div>
  );
}

export default Login;