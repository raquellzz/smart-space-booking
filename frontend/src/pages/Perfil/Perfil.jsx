import { useContext, useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { getReservasUsuario } from '../../services/api';
import { AuthContext } from '../../contexts/AuthContext';
import './Perfil.css';

function Perfil() {
  const { user, logout } = useContext(AuthContext);
  const navigate = useNavigate();
  
  const [historicoReservas, setHistoricoReservas] = useState([]);
  const [loading, setLoading] = useState(true);

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  useEffect(() => {
    async function carregarMinhasReservas() {
      if (!user?.id) {
        console.log("ID do usuário não disponível. Verifique o estado de autenticação.");
        setLoading(false);
        return;
      }
      try {
        console.log("Buscando reservas para o usuário ID:", user.id);
        const response = await getReservasUsuario(user?.id);
        console.log("Reservas que vieram do Banco:", response.data);
        
        const reservasFormatadas = response.data.map((r) => {
          
          const campoInicio = r.inicioDateTime; 
          const campoFim = r.fimDateTime;

          const dataInicio = new Date(campoInicio);
          const dataFim = new Date(campoFim);

          let dataFormatada = dataInicio.toLocaleDateString('pt-BR', {
            day: '2-digit', month: 'short', year: 'numeric'
          }).replace(' de ', ' ').replace('.', '');
          
          const horaInicio = dataInicio.toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' });
          const horaFim = dataFim.toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' });

          let statusVisual = r.status;
          let precisaDeCheckin = false;
          if (r.status === 'CONFIRMADA') {
            const agora = new Date();

            if (agora < dataInicio) {
              // Antes do horário → pode cancelar
              statusVisual = 'CANCELAR RESERVA';

            } else if (agora >= dataInicio && agora <= dataFim) {
              // Durante o horário → checar se já fez check-in
              if (r.fotoCheckinId || r.dataHoraCheckin) {
                statusVisual = 'FAZER CHECKOUT';
              } /*else if (r.fotoCheckout || r.dataHoraCheckout) {
                statusVisual = 'CONCLUÍDA';
              }*/ else {
                statusVisual = 'FAZER CHECK-IN';
              } 

            } else {
              statusVisual = 'EXPIRADA';
            }

          } else if (r.status === 'ENCERRADA') {
            statusVisual = 'CONCLUÍDA';
          }

          return {
            id: r.id,
            sala: `Sala ${r.salaNome}` || `Sala #${r.salaId}`, 
            data: dataFormatada,
            horario: `${horaInicio} - ${horaFim}`,
            status: statusVisual,
            dataInicio,
            dataFim,
          };
        });

        setHistoricoReservas(reservasFormatadas);

      } catch (error) {
        console.error("Erro ao buscar histórico:", error);
      } finally {
        setLoading(false);
      }
    }

    carregarMinhasReservas();
  }, [user]);

  const getStatusClass = (status) => {
  switch (status) {
    case 'FAZER CHECK-IN':   return 'status-checkin';
    case 'FAZER CHECKOUT':   return 'status-checkout';  
    case 'CANCELAR RESERVA': return 'status-cancelar'; 
    case 'CONCLUÍDA':        return 'status-concluida';
    case 'CANCELADA':        return 'status-cancelada';
    case 'EXPIRADA':         return 'status-cancelada';
    default: return '';
  }
};

  const irParaCheckin = (id) => {
    navigate(`/checkin/${id}`);
  };

  const irParaCheckout = (id) => {
    navigate(`/checkout/${id}`);
  };

  const cancelarReserva = async (id) => {
    if (!window.confirm('Tem certeza que deseja cancelar esta reserva?')) return;
    try {
      await cancelarReservaApi(id); // add
      setHistoricoReservas(prev => prev.filter(r => r.id !== id));
    } catch (error) {
      alert('Erro ao cancelar a reserva. Tente novamente.');
    }
  };

  return (
    <div className="perfil-container">
      <main className="perfil-content">
        <h1 className="page-title">Perfil</h1>

        {/* Card principal do usuário */}
        <section className="user-card">
          <div className="user-details">
            <h2 className="user-name">{user?.nome || 'Usuário Desconhecido'}</h2>
            <p className="user-email">{user?.email || 'email@ssb-corp.com'}</p>
            
            <div className="trust-score-badge">
              <span className="score-label">TRUST SCORE:</span>
              <span className="score-value">★ {user?.trustScore || 100}/100</span>
            </div>
          </div>
          
          <button className="logout-button" onClick={handleLogout}>
            🚪 Sair da Conta
          </button>
        </section>

        {/* Histórico de reservas */}
        <section className="history-section">
          <div className="history-header">
            <h2>Histórico de Reservas</h2>
            <p>Gerencie suas utilizações de espaços de trabalho.</p>
          </div>

          {loading ? (
            <p style={{ color: '#666' }}>Carregando seu histórico de reservas...</p>
          ) : historicoReservas.length === 0 ? (
            <p style={{ color: '#666' }}>Você ainda não possui reservas registradas.</p>
          ) : (
            <div className="reservations-list">
              {historicoReservas.map((reserva) => (
                <div key={reserva.id} className={`reservation-card ${getStatusClass(reserva.status)}`}>
                  <div className="reservation-info">
                    <h3>{reserva.sala}</h3>
                    <div className="reservation-datetime">
                      <span>📅 {reserva.data}</span>
                      <span>🕒 {reserva.horario}</span>
                    </div>
                  </div>
                  
                  {['FAZER CHECK-IN', 'FAZER CHECKOUT', 'CANCELAR RESERVA'].includes(reserva.status) ? (
                    <button
                      className={`badge ${getStatusClass(reserva.status)}`}
                      onClick={() => {
                        if (reserva.status === 'FAZER CHECK-IN')   irParaCheckin(reserva.id);
                        if (reserva.status === 'FAZER CHECKOUT')   irParaCheckout(reserva.id);
                        if (reserva.status === 'CANCELAR RESERVA') /*cancelarReserva(reserva.id)*/; // add dps
                      }}
                    >
                      {reserva.status} &gt;
                    </button>
                  ) : (
                    <span className={`badge ${getStatusClass(reserva.status)}`}>
                      {reserva.status}
                    </span>
                  )}
                </div>
              ))}
            </div>
          )}
        </section>
      </main>
    </div>
  );
}

export default Perfil;