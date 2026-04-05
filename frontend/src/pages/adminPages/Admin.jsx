import { useState, useEffect } from 'react'; // Adicionado useEffect
import { useNavigate } from 'react-router-dom';
import { deletarSala, getSalas } from '../../services/api'; // Adicione este import
import './Admin.css';
import '../../App.css';
import SSBLogo from '../../assets/SSBLogo.png';
import imagemMockada from '../../assets/mockImagemSala.jpg';

function Admin() {
  const [salas, setSalas] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    async function carregarSalas() {
      try {
        const response = await getSalas();
        setSalas(response.data);
      } catch (error) {
        console.error("Erro ao carregar salas:", error);
        alert("Não conseguimos carregar as salas no momento.");
      } finally {
        setLoading(false);
      }
    }
    carregarSalas();
  }, []);

  const handleDelete = async (id) => {
    try {
      console.log("ID que chegou no handleDelete:", id);
      await deletarSala(id); 

      alert("Sala removida com sucesso!");
      
      setSalas(prev => prev.filter(sala => sala.id !== id));

    } catch (error) {
      console.error("Erro ao deletar:", error);
      
      if (error.response?.status === 404) {
        setSalas(prev => prev.filter(sala => sala.id !== id));
      } else {
        alert("Erro ao excluir: verifique se a sala possui vínculos ativos.");
      }
    
  }
};

  if (loading) return <div className="p-10 text-center text-xl font-bold">Carregando salas...</div>;

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
          {salas.length === 0 ? (
            <p>Nenhuma sala encontrada.</p>
          ) : (
            salas.map(sala => (
              <div key={sala.id} className="room-card">
                <div className="room-card-main-content">
                  <div className="room-text-content">
                    <h3 className="room-title">{sala.nome}</h3>
                    <p className="room-info">
                      <span className="material-icons">place</span> {sala.local}
                    </p>
                    <p className="room-info">
                      <span className="material-icons">groups</span> {sala.capacidade} pessoas
                    </p>
                  </div>

                  <div className="room-image-container">
                    {/* Fallback para imagem mockada caso o banco não tenha URL */}
                    <img src={sala.imagem || imagemMockada} alt={sala.nome} className="room-card-img" />
                  </div>
                </div>

                <div className="room-card-footer">
                  <div className="room-actions">
                    <span className="material-icons action-icon" onClick={() => navigate('/editar-sala/' + sala.id)}>edit</span>
                    <span className="material-icons action-icon delete" onClick={() => handleDelete(sala.id)}>delete</span>
                  </div>
                  <span className={`status-label ${sala.status?.toLowerCase()}`}>
                    {sala.status}
                  </span>
                </div>
              </div>
            ))
          )}
        </section>
      </main>
    </div>
  );
}

export default Admin;