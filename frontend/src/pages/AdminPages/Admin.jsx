import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { deletarSala, getSalas } from '../../services/api';
import './Admin.css';
import '../../App.css';
import SSBLogo from '../../assets/SSBLogo.png';
import imagemMockada from '../../assets/mockImagemSala.jpg';

function Admin() {
  const [salas, setSalas] = useState([]);
  const [loading, setLoading] = useState(true);
  // const [termoBusca, setTermoBusca] = useState("");
  const [searchParams] = useSearchParams();
  const termoBusca = searchParams.get('busca') || "";
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

  
  const salasFiltradas = salas.filter((sala) => {
    const busca = termoBusca.toLowerCase();
    return (
      sala.nome.toLowerCase().includes(busca) || 
      sala.local.toLowerCase().includes(busca)
    );
  });

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

      <main className="admin-main">
        <div className="page-header">
          <h1 className="page-title">Gerenciar Salas</h1>
          <button className="btn-primary btn-addsala" onClick={() => navigate('/cadastrar-sala')}>
            <span className="material-icons">add</span>
            Nova Sala
          </button>
        </div>

        <section className="rooms-grid">
          {salasFiltradas.length === 0 ? (
            <p>Nenhuma sala encontrada.</p>
          ) : (
            salasFiltradas.map(sala => (
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