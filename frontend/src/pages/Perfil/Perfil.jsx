import { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../contexts/AuthContext";
import { getReservasUsuario, uploadArquivo } from "../../services/api";
import ModalAuditoria from "../Perfil/components/ModalAuditoria";
import "./Perfil.css";

function Perfil() {
  const { user, logout } = useContext(AuthContext);
  const navigate = useNavigate();

  const [reservas, setReservas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [modalAberto, setModalAberto] = useState(null); // { reserva, tipo }
  const [processando, setProcessando] = useState(false);

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  useEffect(() => {
    carregarReservas();
  }, [user]);

  async function carregarReservas() {
    if (!user?.id) {
      setLoading(false);
      return;
    }
    try {
      const response = await getReservasUsuario(user.id);
      setReservas(response.data.map(formatar));
    } catch (error) {
      console.error("Erro ao buscar reservas:", error);
    } finally {
      setLoading(false);
    }
  }

  function formatar(r) {
    const dataInicio = new Date(r.inicioDateTime);
    const dataFim = new Date(r.fimDateTime);

    const dataFormatada = dataInicio
      .toLocaleDateString("pt-BR", {
        day: "2-digit",
        month: "short",
        year: "numeric",
      })
      .replace(" de ", " ")
      .replace(".", "");

    const horaInicio = dataInicio.toLocaleTimeString("pt-BR", {
      hour: "2-digit",
      minute: "2-digit",
    });
    const horaFim = dataFim.toLocaleTimeString("pt-BR", {
      hour: "2-digit",
      minute: "2-digit",
    });

    return {
      id: r.id,
      sala: r.salaNome ? `Sala ${r.salaNome}` : `Sala #${r.salaId}`,
      data: dataFormatada,
      horario: `${horaInicio} - ${horaFim}`,
      status: r.status,
    };
  }

  async function handleConfirmarAuditoria(arquivos) {
    setProcessando(true);
    try {
      const imageIds = await Promise.all(
        arquivos.map(async (arquivo) => {
          const resposta = await uploadArquivo(arquivo);
          return String(resposta.data.id);
        }),
      );

      const formData = new FormData();
      arquivos.forEach((arquivo) => formData.append("imagens", arquivo));
      imageIds.forEach((id) => formData.append("imageIds", id));

      setModalAberto(null);
      await carregarReservas();
    } catch (error) {
      console.error("Erro na auditoria:", error);
      setModalAberto(null);
    } finally {
      setProcessando(false);
    }
  }

  function getStatusClass(status) {
    switch (status) {
      case "CONFIRMADA":
        return "status-checkin";
      case "EM_ANDAMENTO":
        return "status-andamento";
      case "ENCERRADA":
        return "status-concluida";
      case "CANCELADA":
        return "status-cancelada";
      default:
        return "";
    }
  }

  function getLabelStatus(status) {
    switch (status) {
      case "CONFIRMADA":
        return "FAZER CHECK-IN";
      case "EM_ANDAMENTO":
        return "FAZER CHECK-OUT";
      case "ENCERRADA":
        return "CONCLUÍDA";
      case "CANCELADA":
        return "CANCELADA";
      default:
        return status;
    }
  }

  const isAcaoAtiva = (status) =>
    status === "CONFIRMADA" || status === "EM_ANDAMENTO";

  return (
    <div className="perfil-container">
      <main className="perfil-content">
        <h1 className="page-title">Perfil</h1>

        <section className="user-card">
          <div className="user-details">
            <h2 className="user-name">
              {user?.nome || "Usuário Desconhecido"}
            </h2>
            <p className="user-email">{user?.email || "email@ssb-corp.com"}</p>
            <div className="trust-score-badge">
              <span className="score-label">TRUST SCORE:</span>
              <span className="score-value">
                ★ {user?.trustScore || 100}/100
              </span>
            </div>
          </div>
          <button className="logout-button" onClick={handleLogout}>
            🚪 Sair da Conta
          </button>
        </section>

        <section className="history-section">
          <div className="history-header">
            <h2>Minhas Reservas</h2>
            <p>Gerencie suas utilizações de espaços de trabalho.</p>
          </div>

          {loading ? (
            <p style={{ color: "#666" }}>Carregando reservas...</p>
          ) : reservas.length === 0 ? (
            <p style={{ color: "#666" }}>
              Você ainda não possui reservas registradas.
            </p>
          ) : (
            <div className="reservations-list">
              {reservas.map((reserva) => (
                <div
                  key={reserva.id}
                  className={`reservation-card ${getStatusClass(reserva.status)}`}
                >
                  <div className="reservation-info">
                    <h3>{reserva.sala}</h3>
                    <div className="reservation-datetime">
                      <span>📅 {reserva.data}</span>
                      <span>🕒 {reserva.horario}</span>
                    </div>
                  </div>

                  {isAcaoAtiva(reserva.status) ? (
                    <button
                      className={`badge ${getStatusClass(reserva.status)}-btn`}
                      onClick={() =>
                        setModalAberto({
                          reserva,
                          tipo:
                            reserva.status === "CONFIRMADA"
                              ? "checkin"
                              : "checkout",
                        })
                      }
                    >
                      {getLabelStatus(reserva.status)} &gt;
                    </button>
                  ) : (
                    <span className={`badge ${getStatusClass(reserva.status)}`}>
                      {getLabelStatus(reserva.status)}
                    </span>
                  )}
                </div>
              ))}
            </div>
          )}
        </section>
      </main>

      {/* Modal de auditoria */}
      {modalAberto && (
        <ModalAuditoria
          tipo={modalAberto.tipo}
          reserva={modalAberto.reserva}
          onConfirmar={handleConfirmarAuditoria}
          onFechar={() => !processando && setModalAberto(null)}
          processando={processando}
        />
      )}
    </div>
  );
}

export default Perfil;
