import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080/api",
});

export const loginUsuario = (dados) => api.post("/usuarios/acesso", dados);

export const getSalas = () => api.get("/salas");
export const getSalaById = (id) => api.get(`/salas/${id}`);
export const deletarSala = (id) => api.delete(`/salas/${id}`);
export const cadastrarSala = (salaData) => api.post("/salas", salaData);
export const atualizarSala = (id, salaData) =>
  api.put(`/salas/${id}`, salaData);

export const criarReserva = (reservaData) => api.post("/reservas", reservaData);
export const getReservasUsuario = (usuarioId) => api.get(`/reservas/usuario/${usuarioId}`);
export const getReservaById = (id) => api.get(`/reservas/${id}`);

export const getHorariosOcupados = (salaId, data) =>
  api.get("/reservas/ocupados", {
    params: { salaId, data },
  });

export default api;
