import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './CadastroSala.css';
import './Admin.css';
import SSBLogo from '../../assets/SSBLogo.png';

function CadastroSala() {
  const navigate = useNavigate();
  
  const [formData, setFormData] = useState({
    nome: '',
    capacidade: '',
    localizacao: '',
    status: 'Ativa',
    imagem: null
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Sala Cadastrada:", formData);
    // Add back
    alert("Sala cadastrada com sucesso!");
    navigate('/admin');
  };
return (
    <div className="admin-container">
      <header className="admin-header">
        <div className="header-left">
          <img className="logo-admin" src={SSBLogo} alt="S.S.B. Logo" />
        </div>

        <div className="header-right">
          <span className="user-icon material-icons">account_circle</span>
          <span>Admin</span>
        </div>
      </header>

      <main className="admin-main">
        
        <div className="page-header">
          <h1 className="page-title">Cadastro de Sala</h1>
        </div>

        <form onSubmit={handleSubmit} className="cadastro-form">
          <div className="input-group">
            <label>Nome da Sala (ex: B402 - IMD)</label>
            <input 
              type="text" 
              required 
              placeholder="Digite o nome identificador"
              onChange={(e) => setFormData({...formData, nome: e.target.value})}
            />
          </div>

          <div className="form-row">
            <div className="input-group">
              <label>Capacidade (Pessoas)</label>
              <input 
                type="number" 
                required 
                placeholder="Ex: 10"
                onChange={(e) => setFormData({...formData, capacidade: e.target.value})}
              />
            </div>
            <div className="input-group">
              <label>Status Inicial</label>
              <select onChange={(e) => setFormData({...formData, status: e.target.value})}>
                <option value="Ativa">Ativa</option>
                <option value="Manutenção">Manutenção</option>
              </select>
            </div>
          </div>

          <div className="input-group">
            <label>Localização (Prédio/Andar)</label>
            <input 
              type="text" 
              required 
              placeholder="Ex: Terceiro andar, B402"
              onChange={(e) => setFormData({...formData, localizacao: e.target.value})}
            />
          </div>

          <div className="input-group">
            <label>Imagem da Sala</label>
            <input 
              type="text" 
              placeholder="Cole o link da foto"
              onChange={(e) => setFormData({...formData, imagem: e.target.value})}
            />
          </div>

          <div className="cadastro-actions">
            <button type="button" className="btn-cancel" onClick={() => navigate('/admin')}>
              Cancelar
            </button>
            <button type="submit" className="btn-primary btn-save">
              Salvar Sala
            </button>
          </div>
        </form>
      </main>
    </div>
  );
}

export default CadastroSala;