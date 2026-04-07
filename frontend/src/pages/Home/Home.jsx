import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getSalas } from '../../services/api';
import './adminPages/Admin.css';
import '../App.css';
import './Home.css';
import SSBLogo from '../assets/SSBLogo.png';
import imagemMockada from '../assets/mockImagemSala.jpg';

function Home() {
  const [salas, setSalas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [termoBusca, setTermoBusca] = useState("");
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

  if (loading) return <div className="p-10 text-center text-xl font-bold">Carregando salas...</div>;

  const salasFiltradas = salas.filter((sala) => {
    const busca = termoBusca.toLowerCase();
    return (
      sala.nome.toLowerCase().includes(busca) || 
      sala.local.toLowerCase().includes(busca)
    );
  });

  return (
    <div className="admin-container">
      <header className="admin-header">
        <div className="header-left">
          <img className="logo-admin" src={SSBLogo} alt="S.S.B. Logo" />
        </div>

        <div className="search-bar">
          <span className="material-icons search-icon">search</span>
          <input
            type="text"
            placeholder="Pesquise uma sala ou localização"
            value={termoBusca}
            onChange={(e) => setTermoBusca(e.target.value)}
          />
        </div>

        <div className="header-right">
          <span className="user-icon material-icons">account_circle</span>
          <span>User</span>
        </div>
      </header>

      <main className="admin-main">
        <div className="page-header">
          <h1 className="page-title">Explorar salas</h1>
        </div>

        <section className="rooms-grid">
          {salasFiltradas.length === 0 ? (
            <p>Nenhuma sala encontrada.</p>
          ) : (
            salasFiltradas.map(sala => (
              <div className="sala-card">
                <div className="sala-image-container">
                  <img src={sala.imagem || imagemMockada} alt={sala.nome} className="sala-image" />
                </div>

                <div className="sala-content">
                  <div className="sala-header">
                    <h3 className="sala-title">{sala.nome}</h3>
                    <div className="sala-capacity">
                      <span className="material-icons capacity-icon">groups</span>
                      <span>{String(sala.capacidade).padStart(2, '0')}</span>
                    </div>
                  </div>

                  <p className="sala-location">{sala.local}</p>

                  <div className="sala-features">
                    {sala.caracteristicas && sala.caracteristicas.map((feature, index) => (
                      <span key={index} className="feature-tag">{feature}</span>
                    ))}
                  </div>

                  <button className="btn-primary" onClick={() => navigate('/criar-reserva/' + sala.id)}>
                    Reservar Sala <span className="material-icons">arrow_forward</span>
                  </button>
                </div>
              </div>
            ))
          )}
        </section>
      </main>
    </div>
  );
}

export default Home;