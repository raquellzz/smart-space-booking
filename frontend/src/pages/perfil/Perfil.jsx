import { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../../contexts/AuthContext';
import SSBLogo from '../../assets/SSBLogo.png';
import './Perfil.css';

function Perfil() {
  const { user, logout } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  // Mock Data: Simulando o histórico de reservas do usuário por enquanto
  const historicoReservas = [
    {
      id: 1,
      sala: "Sala de Conferência Orion",
      data: "15 Out, 2024",
      horario: "09:00 - 11:00",
      status: "FAZER CHECK-IN",
    },
    {
      id: 2,
      sala: "Estação Individual #42",
      data: "12 Out, 2024",
      horario: "14:00 - 18:00",
      status: "CONCLUÍDA",
    },
    {
      id: 3,
      sala: "Auditório Principal",
      data: "10 Out, 2024",
      horario: "08:00 - 12:00",
      status: "CANCELADA",
    },
    {
      id: 4,
      sala: "Sala de Reunião Alpha",
      data: "08 Out, 2024",
      horario: "10:00 - 11:30",
      status: "CONCLUÍDA",
    }
  ];

  const getStatusClass = (status) => {
    switch (status) {
      case 'FAZER CHECK-IN': return 'status-checkin';
      case 'CONCLUÍDA': return 'status-concluida';
      case 'CANCELADA': return 'status-cancelada';
      default: return '';
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
                
                {reserva.status === 'FAZER CHECK-IN' ? (
                  <button className="badge checkin-btn">
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
        </section>
      </main>
    </div>
  );
}

export default Perfil;