import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { cadastrarSala } from '../../services/api'
import './CadastroSala.css';
import './Admin.css';
import '../../App.css';
import SSBLogo from '../../assets/SSBLogo.png';

function CadastroSala() {
  const navigate = useNavigate();
  
  const [formData, setFormData] = useState({
    nome: '',
    capacidade: '',
    local: '',
    status: 'ATIVA',
    tipoSala: 'REUNIAO', 
    caracteristicasTexto: '',
    imagem: ''
  });

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const listaCaracteristicas = formData.caracteristicasTexto
        .split(',')
        .map(item => item.trim())
        .filter(item => item !== "");

      const dadosParaEnviar = {
        nome: formData.nome,
        capacidade: parseInt(formData.capacidade),
        local: formData.local,
        status: formData.status,
        tipoSala: formData.tipoSala,
        caracteristicas: listaCaracteristicas,
        //imagem: formData.imagem
      };

      await cadastrarSala(dadosParaEnviar);
      
      alert("Sala cadastrada com sucesso!");
      navigate('/admin');
    } catch (error) {
      console.error("Erro ao cadastrar sala:", error);
      alert("Falha ao cadastrar a sala. Verifique se o Back-end está rodando.");
    }
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
              value={formData.nome}
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
                value={formData.capacidade}
                onChange={(e) => setFormData({...formData, capacidade: e.target.value})}
              />
            </div>
            <div className="input-group">
              <label>Status Inicial</label>
              <select 
                value={formData.status}
                onChange={(e) => setFormData({...formData, status: e.target.value})}
              >
                <option value="ATIVA">Ativa</option>
                <option value="MANUTENCAO">Manutenção</option>
              </select>
            </div>
          </div>

          <div className="input-group">
            <label>Tipo de Sala</label>
            <select 
              value={formData.tipoSala}
              onChange={(e) => setFormData({...formData, tipoSala: e.target.value})}
            >
              <option value="REUNIAO">Sala de Reunião</option>
              <option value="CONFERENCIA">Sala de Conferência</option>
              <option value="LABORATORIO">Laboratório</option>
              <option value="ESTUDO_INDIVIDUAL">Sala de Estudo Individual</option>

            </select>
          </div>

          <div className="input-group">
            <label>Localização (Prédio/Andar)</label>
            <input 
              type="text" 
              required 
              placeholder="Ex: Terceiro andar, B402"
              value={formData.local}
              onChange={(e) => setFormData({...formData, local: e.target.value})}
            />
          </div>

          <div className="input-group">
            <label>Características (Separe por vírgula)</label>
            <input 
              type="text" 
              placeholder="Ex: TV, Ar-condicionado, Wi-Fi"
              value={formData.caracteristicasTexto}
              onChange={(e) => setFormData({...formData, caracteristicasTexto: e.target.value})}
            />
            <small style={{color: '#666', fontSize: '12px'}}>Os itens serão listados individualmente na visualização.</small>
          </div>

          <div className="input-group">
            <label>Imagem da Sala</label>
            <input 
              type="text" 
              placeholder="Cole o link da foto"
              value={formData.imagem}
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