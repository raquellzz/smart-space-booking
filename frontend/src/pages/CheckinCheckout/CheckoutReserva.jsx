import { useState, useEffect, useContext } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { fazerCheckOut } from '../../services/api';
import { AuthContext } from '../../contexts/AuthContext';
import './CheckinReserva.css';

function CheckoutReserva({ onClose }) {
  const navigate = useNavigate();
  const { id } = useParams();
  const { user } = useContext(AuthContext);

  const [arquivos, setArquivos] = useState([]);
  const [previews, setPreviews] = useState([]);
  const [carregando, setCarregando] = useState(false);

  function handleFileChange(e) {
    const selecionados = Array.from(e.target.files);
    if (!selecionados.length) return;

    const novosPreviews = selecionados.map((arquivo) => ({
      url: URL.createObjectURL(arquivo),
      nome: arquivo.name,
    }));

    setArquivos((prev) => [...prev, ...selecionados]);
    setPreviews((prev) => [...prev, ...novosPreviews]);
    e.target.value = '';
  }

  function removerImagem(index) {
    URL.revokeObjectURL(previews[index].url);
    setArquivos((prev) => prev.filter((_, i) => i !== index));
    setPreviews((prev) => prev.filter((_, i) => i !== index));
  }

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!arquivos.length) {
      alert('Por favor, adicione pelo menos uma foto da sala para confirmar o check-out.');
      return;
    }

    setCarregando(true);
    try {
      await fazerCheckOut(id, user.id, arquivos);
      alert('Check-out realizado com sucesso!');
      if (onClose) onClose();
      else navigate(-1);
    } catch (error) {
      console.error('Erro no check-out:', error);
      const mensagemErro = error.response?.data || 'Erro ao processar o check-out. Tente novamente.';
      alert(mensagemErro);
    } finally {
      setCarregando(false);
    }
  };

  useEffect(() => {
    return () => previews.forEach((p) => URL.revokeObjectURL(p.url));
  }, [previews]);

  return (
    <div className="admin-container">
      <main className="admin-main">
        <div className="page-header">
            <h2 className="page-title">Check-out da Sala</h2>
            <p>Fotografe o ambiente após o uso para registrar sua saída.</p>
        </div>

        <form onSubmit={handleSubmit} className="checkin-form">

          {/* Zona de upload */}
          <div
            className="checkin-upload-zona"
            onClick={() => document.getElementById('input-fotos-checkout').click()}
          >
            <span className="material-icons checkin-upload-icone">add_a_photo</span>
            <p className="checkin-upload-texto">Clique para adicionar fotos</p>
            <p className="checkin-upload-subtexto">JPG, PNG ou WEBP · Múltiplas imagens permitidas</p>
            <input
              id="input-fotos-checkout"
              type="file"
              accept="image/*"
              multiple
              capture="environment"
              onChange={handleFileChange}
              style={{ display: 'none' }}
            />
          </div>

          {/* Grid de previews */}
          {previews.length > 0 && (
            <div className="checkin-previews">
              {previews.map((preview, index) => (
                <div key={index} className="checkin-preview-item">
                  <img src={preview.url} alt={preview.nome} className="checkin-preview-img" />
                  <button
                    type="button"
                    className="checkin-preview-remover"
                    onClick={() => removerImagem(index)}
                    disabled={carregando}
                    title="Remover imagem"
                  >
                    ✕
                  </button>
                </div>
              ))}

              <div
                className="checkin-preview-adicionar"
                onClick={() => document.getElementById('input-fotos-checkout').click()}
                title="Adicionar mais imagens"
              >
                <span>+</span>
              </div>
            </div>
          )}

          <div className="checkin-actions">
            <button
              type="button"
              className="btn-cancel"
              onClick={onClose || (() => navigate(-1))}
              disabled={carregando}
            >
              Cancelar
            </button>
            <button
              type="submit"
              className="btn-primary btn-save"
              disabled={carregando || !arquivos.length}
            >
              {carregando
                ? 'Validando...'
                : `Confirmar Check-out${arquivos.length > 0 ? ` (${arquivos.length})` : ''}`}
            </button>
          </div>
        </form>
      </main>
    </div>
  );
}

export default CheckoutReserva;
