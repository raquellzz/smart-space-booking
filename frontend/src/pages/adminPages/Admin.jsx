import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Admin.css';
import SSBLogo from '../../assets/SSBLogo.png';
import imagemMockada from '../../assets/mockImagemSala.jpg';

const salasMock = [
  { id: 'B402', nome: 'Sala de Reunião', capacidade: 10, localizacao: 'Terceiro andar, B402', status: 'Ativa', imagem:imagemMockada },
  { id: 'C201', nome: 'Auditório Principal', capacidade: 50, localizacao: 'Prédio C', status: 'Ativa', imagem:imagemMockada },
  { id: 'A105', nome: 'Sala de Foco 1', capacidade: 2, localizacao: 'Prédio A', status: 'Manutenção', imagem:imagemMockada },
  { id: 'A106', nome: 'Sala de Foco 2', capacidade: 2, localizacao: 'Prédio A', status: 'Ativa', imagem:imagemMockada },
];

function Admin() {
  const [salas, setSalas] = useState(salasMock);
  const navigate = useNavigate();

  return (
    <div className="admin-container">
      <header className="admin-header">
        <div className="header-left">
          <img className="logo-admin" src={SSBLogo} alt="S.S.B. Logo" />
        </div>

        <div className="search-bar">
          <span className="material-icons search-icon">search</span>
          <input type="text" placeholder="Pesquise uma sala" />
        </div>

        <div className="header-right">
          <span className="user-icon material-icons">account_circle</span>
          <span>Admin</span>
        </div>
      </header>

      <main className="admin-main">
        <div className="page-header">
          <h1 className="page-title">Gerenciar Salas</h1>
          <button className="btn-primary btn-addsala" onClick={() => navigate('/cadastrar-sala')}>
            <span className="material-icons">add</span>
            Nova Sala
          </button>
        </div>

        <section className="rooms-grid">
          {salas.map(sala => (
            <div key={sala.id} className="room-card">
              <div className="room-card-main-content">
                <div className="room-text-content">
                  <h3 className="room-title">{sala.nome}</h3>
                  <p className="room-info">
                    <span className="material-icons">place</span> {sala.localizacao}
                  </p>
                  <p className="room-info">
                    <span className="material-icons">groups</span> {sala.capacidade} pessoas
                  </p>
                </div>

                <div className="room-image-container">
                  <img src={sala.imagem} alt={sala.nome} className="room-card-img" />
                </div>
              </div>

              <div className="room-card-footer">
                <div className="room-actions">
                  <span className="material-icons action-icon">edit</span>
                  <span className="material-icons action-icon delete">delete</span>
                </div>
                <span className={`status-label ${sala.status.toLowerCase()}`}>
                  {sala.status}
                </span>
              </div>
            </div>
          ))}
        </section>
      </main>
    </div>
  );
}

export default Admin;