import { useState, useEffect, useContext } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { realizarCheckinReserva } from '../../services/api';
import { uploadArquivo } from '../../services/apiFiles';
import { AuthContext } from '../../contexts/AuthContext';

import '../AdminPages/CadastroSala.css';

function CheckinReserva({ onClose }) {
  const navigate = useNavigate();
  const { id } = useParams();
  
  const [fotoSelecionada, setFotoSelecionada] = useState(null);
  const [previewUrl, setPreviewUrl] = useState(null);
  const [carregando, setCarregando] = useState(false);
  console.log("ID da reserva para check-in:", id);

  const { user } = useContext(AuthContext);

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setFotoSelecionada(file);
      // Cria uma URL temporária para mostrar o preview da foto na tela
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
      alert("Por favor, tire uma foto da sala para confirmar o check-in.");
      return; 
    }

    setCarregando(true);

    try {
      const fotoId = await uploadArquivo(fotoSelecionada);

      const response = await realizarCheckinReserva(id, user.id, fotoId);
      
      alert("Check-in realizado com sucesso!");
      
      if (onClose) {
        onClose();
      } else {
        // navigate('/perfil');
      }

    } catch (error) {
      console.error("Erro no check-in:", error);
      const mensagemErro = error.response?.data || "Erro ao processar o check-in. Tente novamente.";
      
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
          <h2 className="page-title">Check-in da Sala</h2>
          <p>Tire uma foto do ambiente para confirmar sua presença.</p>
        </div>

        <form onSubmit={handleSubmit} className="cadastro-form">
          
          <div className="input-group" style={{ textAlign: 'center' }}>
            {previewUrl ? (
              <div className="preview-container">
                <img 
                  src={previewUrl} 
                  alt="Preview do Check-in" 
                  style={{ width: '100%', maxHeight: '300px', objectFit: 'cover', borderRadius: '8px' }} 
                />
                <button type="button" className="btn-cancel" onClick={limparFoto} style={{ marginTop: '10px' }}>
                  Tirar outra foto
                </button>
              </div>
            ) : (
              <div className="upload-box" style={{ padding: '20px', border: '2px dashed #ccc', borderRadius: '8px' }}>
                <label style={{ display: 'block', cursor: 'pointer' }}>
                  <span className="material-icons" style={{ fontSize: '48px', color: '#666' }}>camera_alt</span>
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

          <div className="cadastro-actions" style={{ marginTop: '20px' }}>
            <button type="button" className="btn-cancel" onClick={onClose || (() => navigate(-1))} disabled={carregando}>
              Cancelar
            </button>
            <button type="submit" className="btn-primary btn-save" disabled={carregando || !fotoSelecionada}>
              {carregando ? "Validando..." : "Confirmar Check-in"}
            </button>
          </div>
        </form>
      </main>
    </div>
  );
}

export default CheckinReserva;