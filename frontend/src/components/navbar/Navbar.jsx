import { useContext } from "react";
import { AuthContext } from "../../contexts/AuthContext";
import SSBLogo from "../../assets/SSBLogo.png";
import "./Navbar.css";

function Navbar() {
  const { user } = useContext(AuthContext);

  // Ferramentas de navegação do React Router
  const [searchParams, setSearchParams] = useSearchParams();
  const location = useLocation();
  const navigate = useNavigate();

  // O valor atual do input é o que está na URL. Se não tiver nada, é uma string vazia.
  const termoBusca = searchParams.get("busca") || "";

  const handleBuscaChange = (e) => {
    const valorDigitado = e.target.value;

    // Se o usuário estiver na tela de Perfil e começar a digitar,
    // nós o mandamos para o Admin (ou Home) já com o termo de busca na URL
    if (location.pathname !== "/admin" && location.pathname !== "/home") {
      const rotaDestino = user?.perfil === "ADMIN" ? "/admin" : "/home";
      navigate(`${rotaDestino}?busca=${valorDigitado}`);
      return;
    }

    // Se ele já estiver na tela certa, apenas atualizamos a URL em tempo real
    if (valorDigitado) {
      setSearchParams({ busca: valorDigitado });
    } else {
      setSearchParams({}); // Limpa a URL se o input ficar vazio
    }
  };

  return (
    <header className="global-navbar">
      <div className="navbar-logo-container">
        <img className="navbar-logo" src={SSBLogo} alt="SSB Logo" />
      </div>

      <div className="navbar-search">
        <span className="material-icons search-icon">search</span>
        <input
          type="text"
          placeholder="Pesquise uma sala"
          value={termoBusca}
          onChange={handleBuscaChange}
        />
      </div>

      <div className="navbar-user">
        <span className="user-icon material-icons">account_circle</span>
        <span className="user-role">
          {user?.perfil === "ADMIN" ? "Admin" : "User"}
        </span>
      </div>
    </header>
  );
}

export default Navbar;
