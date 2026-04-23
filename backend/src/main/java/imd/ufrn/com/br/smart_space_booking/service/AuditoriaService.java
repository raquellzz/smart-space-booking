package imd.ufrn.com.br.smart_space_booking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import imd.ufrn.com.br.smart_space_booking.client.GeminiClient;
import imd.ufrn.com.br.smart_space_booking.dto.AuditoriaResultadoDTO;
import imd.ufrn.com.br.smart_space_booking.enums.AuditoriaTipo;
import imd.ufrn.com.br.smart_space_booking.model.Auditoria;
import imd.ufrn.com.br.smart_space_booking.model.Reserva;
import imd.ufrn.com.br.smart_space_booking.prompts.AuditoriaPrompts;
import imd.ufrn.com.br.smart_space_booking.repository.AuditoriaRepository;
import imd.ufrn.com.br.smart_space_booking.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AuditoriaService {

    private final GeminiClient geminiClient;
    private final AuditoriaRepository auditoriaRepository;
    private final ReservaRepository reservaRepository;
    private final ObjectMapper objectMapper;

    public AuditoriaService(GeminiClient geminiClient,
                            AuditoriaRepository auditoriaRepository,
                            ReservaRepository reservaRepository,
                            ObjectMapper objectMapper) {
        this.geminiClient = geminiClient;
        this.auditoriaRepository = auditoriaRepository;
        this.reservaRepository = reservaRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Auditoria de check-in: valida o estado inicial da sala.
     * Resultado sempre registrado, independente de aprovação.
     */
    public Auditoria realizarCheckIn(Long reservaId, List<MultipartFile> imagens, List<String> imageIds) {
        Reserva reserva = buscarReserva(reservaId);
        validarAuditoriaInexistente(reservaId, AuditoriaTipo.CHECK_IN);

        String respostaTexto = geminiClient.analisar(AuditoriaPrompts.promptCheckIn(), imagens);
        AuditoriaResultadoDTO resultado = parsearResposta(respostaTexto);

        return persistir(reserva, AuditoriaTipo.CHECK_IN, resultado, imageIds, respostaTexto);
    }

    /**
     * Auditoria de check-out: avalia o ambiente e classifica em uma AuditoriaCategoria.
     * Requer que o check-in já tenha sido realizado para a mesma reserva.
     */
    public Auditoria realizarCheckOut(Long reservaId, List<MultipartFile> imagens, List<String> imageIds) {
        Reserva reserva = buscarReserva(reservaId);
        validarCheckInRealizado(reservaId);
        validarAuditoriaInexistente(reservaId, AuditoriaTipo.CHECK_OUT);

        String respostaTexto = geminiClient.analisar(AuditoriaPrompts.promptCheckOut(), imagens);
        AuditoriaResultadoDTO resultado = parsearResposta(respostaTexto);

        return persistir(reserva, AuditoriaTipo.CHECK_OUT, resultado, imageIds, respostaTexto);
    }

    // -------------------------
    // Métodos privados
    // -------------------------

    private AuditoriaResultadoDTO parsearResposta(String textoResposta) {
        try {
            String limpo = textoResposta
                    .replaceAll("(?s)```json\\s*", "")
                    .replaceAll("(?s)```\\s*", "")
                    .trim();
            return objectMapper.readValue(limpo, AuditoriaResultadoDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao interpretar resposta do Gemini: " + e.getMessage(), e);
        }
    }

    private Auditoria persistir(Reserva reserva,
                                AuditoriaTipo tipo,
                                AuditoriaResultadoDTO resultado,
                                List<String> imageIds,
                                String respostaTexto) {
        Auditoria auditoria = new Auditoria();
        auditoria.setReserva(reserva);
        auditoria.setTipo(tipo);
        auditoria.setAprovado(resultado.isAprovado());
        auditoria.setObservacoes(resultado.getObservacoes());
        auditoria.setCategoria(resultado.getCategoria()); // null no check-in
        auditoria.setImageIds(imageIds);
        auditoria.setResultadoIa(respostaTexto);

        return auditoriaRepository.save(auditoria);
    }

    private Reserva buscarReserva(Long reservaId) {
        return reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada: " + reservaId));
    }

    private void validarCheckInRealizado(Long reservaId) {
        boolean existe = auditoriaRepository
                .findByReservaIdAndTipo(reservaId, AuditoriaTipo.CHECK_IN)
                .isPresent();

        if (!existe) {
            throw new RuntimeException("Check-in não realizado para a reserva: " + reservaId);
        }
    }

    private void validarAuditoriaInexistente(Long reservaId, AuditoriaTipo tipo) {
        boolean jaExiste = auditoriaRepository
                .findByReservaIdAndTipo(reservaId, tipo)
                .isPresent();

        if (jaExiste) {
            throw new RuntimeException(tipo + " já realizado para a reserva: " + reservaId);
        }
    }
}