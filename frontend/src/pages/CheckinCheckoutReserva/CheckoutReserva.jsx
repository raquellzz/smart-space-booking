import { useState, useEffect, useContext } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
//import { realizarCheckoutReserva } from '../../services/api';
import { uploadArquivo } from '../../services/apiFiles';
import { AuthContext } from '../../contexts/AuthContext';

import '../AdminPages/CadastroSala.css';

function CheckoutReserva({ onClose }) {
  const navigate = useNavigate();
  const { id } = useParams();

  const [fotoSelecionada, setFotoSelecionada] = useState(null);
  const [previewUrl, setPreviewUrl] = useState(null);
  const [carregando, setCarregando] = useState(false);
  const [solicitarLimpeza, setSolicitarLimpeza] = useState(false);
  const [solicitarManutencao, setSolicitarManutencao] = useState(false);

  console.log("ID da reserva para check-out:", id);

  const { user } = useContext(AuthContext);

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setFotoSelecionada(file);
      setPreviewUrl(URL.createObjectURL(file));
    }
  };

  const limparFoto = () => {
    setFotoSelecionada(null);
    setPreviewUrl(null);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!fotoSelecionada) {
      alert("Por favor, tire uma foto da sala para confirmar o check-out.");
      return;
    }

    setCarregando(true);

    try {
      const fotoId = await uploadArquivo(fotoSelecionada);

      /*const response = await realizarCheckoutReserva(id, user.id, fotoId, {
        solicitarLimpeza,
        solicitarManutencao,
      });*/

      alert("Check-out realizado com sucesso!");

      if (onClose) {
        onClose();
      } else {
        // navigate('/perfil');
      }
    } catch (error) {
      console.error("Erro no check-out:", error);
      const mensagemErro =
        error.response?.data || "Erro ao processar o check-out. Tente novamente.";
      alert(mensagemErro);
    } finally {
      setCarregando(false);
    }
  };

  useEffect(() => {
    return () => {
      if (previewUrl) URL.revokeObjectURL(previewUrl);
    };
  }, [previewUrl]);

  return (
    <div className="admin-container" style={{ maxWidth: '500px', margin: '0 auto' }}>
      <main className="admin-main">
        <div className="page-header">
          <h2 className="page-title">Check-out da Sala</h2>
          <p>Tire uma foto do ambiente para confirmar sua saída.</p>
        </div>

        <form onSubmit={handleSubmit} className="cadastro-form">

          {/* Foto */}
          <div className="input-group" style={{ textAlign: 'center' }}>
            {previewUrl ? (
              <div className="preview-container">
                <img
                  src={previewUrl}
                  alt="Preview do Check-out"
                  style={{ width: '100%', maxHeight: '300px', objectFit: 'cover', borderRadius: '8px' }}
                />
                <button
                  type="button"
                  className="btn-cancel"
                  onClick={limparFoto}
                  style={{ marginTop: '10px' }}
                >
                  Tirar outra foto
                </button>
              </div>
            ) : (
              <div
                className="upload-box"
                style={{ padding: '20px', border: '2px dashed #ccc', borderRadius: '8px' }}
              >
                <label style={{ display: 'block', cursor: 'pointer' }}>
                  <span className="material-icons" style={{ fontSize: '48px', color: '#666' }}>
                    camera_alt
                  </span>
                  <p>Clique aqui para abrir a câmera</p>
                  <input
                    type="file"
                    accept="image/*"
                    capture="environment"
                    onChange={handleFileChange}
                    style={{ display: 'none' }}
                  />
                </label>
              </div>
            )}
          </div>

          {/* Solicitações */}
          <div className="input-group" style={{ marginTop: '20px' }}>
            <label className="input-label">Solicitações (opcional)</label>

            <div
              style={{
                display: 'flex',
                flexDirection: 'column',
                gap: '12px',
                marginTop: '10px',
              }}
            >
              <label
                style={{
                  display: 'flex',
                  alignItems: 'center',
                  gap: '10px',
                  cursor: 'pointer',
                  fontSize: '14px',
                }}
              >
                <input
                  type="checkbox"
                  checked={solicitarLimpeza}
                  onChange={(e) => setSolicitarLimpeza(e.target.checked)}
                  style={{ width: '18px', height: '18px', cursor: 'pointer' }}
                />
                <span className="material-icons" style={{ fontSize: '20px', color: '#666' }}>
                  cleaning_services
                </span>
                Solicitar Limpeza
              </label>

              <label
                style={{
                  display: 'flex',
                  alignItems: 'center',
                  gap: '10px',
                  cursor: 'pointer',
                  fontSize: '14px',
                }}
              >
                <input
                  type="checkbox"
                  checked={solicitarManutencao}
                  onChange={(e) => setSolicitarManutencao(e.target.checked)}
                  style={{ width: '18px', height: '18px', cursor: 'pointer' }}
                />
                <span className="material-icons" style={{ fontSize: '20px', color: '#666' }}>
                  build
                </span>
                Solicitar Manutenção
              </label>
            </div>
          </div>

          {/* Ações */}
          <div className="cadastro-actions" style={{ marginTop: '20px' }}>
            <button
              type="button"
              className="btn-cancel"
              onClick={onClose || (() => navigate(-1))}
              disabled={carregando}
            >
              Cancelar
            </button>
            <button
              //type="submit"
              className="btn-primary btn-save"
              disabled={carregando || !fotoSelecionada}
            >
              {carregando ? "Validando..." : "Confirmar Check-out"}
            </button>
          </div>
        </form>
      </main>
    </div>
  );
}

export default CheckoutReserva;
