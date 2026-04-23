package imd.ufrn.com.br.smart_space_booking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import imd.ufrn.com.br.smart_space_booking.client.GeminiClient;
import imd.ufrn.com.br.smart_space_booking.client.MediaStorageClient;
import imd.ufrn.com.br.smart_space_booking.dto.AuditoriaResponseDTO;
import imd.ufrn.com.br.smart_space_booking.dto.AuditoriaResultadoDTO;
import imd.ufrn.com.br.smart_space_booking.enums.AuditoriaTipo;
import imd.ufrn.com.br.smart_space_booking.enums.ReservaStatus;
import imd.ufrn.com.br.smart_space_booking.model.Auditoria;
import imd.ufrn.com.br.smart_space_booking.model.Reserva;
import imd.ufrn.com.br.smart_space_booking.prompts.AuditoriaPrompts;
import imd.ufrn.com.br.smart_space_booking.repository.AuditoriaRepository;
import imd.ufrn.com.br.smart_space_booking.repository.ReservaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AuditoriaService {

    private final GeminiClient geminiClient;
    private final MediaStorageClient mediaStorageClient;
    private final AuditoriaRepository auditoriaRepository;
    private final ReservaRepository reservaRepository;
    private final ObjectMapper objectMapper;

    public AuditoriaService(GeminiClient geminiClient,
                            MediaStorageClient mediaStorageClient,
                            AuditoriaRepository auditoriaRepository,
                            ReservaRepository reservaRepository,
                            ObjectMapper objectMapper) {
        this.geminiClient = geminiClient;
        this.mediaStorageClient = mediaStorageClient;
        this.auditoriaRepository = auditoriaRepository;
        this.reservaRepository = reservaRepository;
        this.objectMapper = objectMapper;
    }

    public List<AuditoriaResponseDTO> findAll() {
        return auditoriaRepository.findAll()
                .stream()
                .map(AuditoriaResponseDTO::fromEntity)
                .toList();
    }

    public List<AuditoriaResponseDTO> findByReservaId(Long reservaId) {
        return auditoriaRepository.findByReservaId(reservaId)
                .stream()
                .map(AuditoriaResponseDTO::fromEntity)
                .toList();
    }

    @Transactional
    public Auditoria realizarCheckIn(Long reservaId, List<MultipartFile> imagens, List<String> imageIds) {
        Reserva reserva = buscarReserva(reservaId);
        validarStatus(reserva, ReservaStatus.CONFIRMADA, "Check-in só pode ser feito em reservas CONFIRMADAS.");
        validarAuditoriaInexistente(reservaId, AuditoriaTipo.CHECK_IN);

        List<byte[]> imagensReferencia = buscarImagensReferencia(reserva);
        String respostaTexto = geminiClient.analisar(AuditoriaPrompts.promptCheckIn(), imagensReferencia, imagens);
        AuditoriaResultadoDTO resultado = parsearResposta(respostaTexto);

        reserva.setStatus(ReservaStatus.EM_ANDAMENTO);
        reservaRepository.save(reserva);

        return persistir(reserva, AuditoriaTipo.CHECK_IN, resultado, imageIds, respostaTexto);
    }

//    @Transactional
//    public Auditoria realizarCheckOut(Long reservaId, List<MultipartFile> imagens, List<String> imageIds) {
//        Reserva reserva = buscarReserva(reservaId);
//        validarStatus(reserva, ReservaStatus.EM_ANDAMENTO, "Check-out só pode ser feito em reservas EM_ANDAMENTO.");
//        validarAuditoriaInexistente(reservaId, AuditoriaTipo.CHECK_OUT);
//
//        List<byte[]> imagensReferencia = buscarImagensReferencia(reserva);
//        String respostaTexto = geminiClient.analisar(AuditoriaPrompts.promptCheckOut(), imagensReferencia, imagens);
//        AuditoriaResultadoDTO resultado = parsearResposta(respostaTexto);
//
//        reserva.setStatus(ReservaStatus.ENCERRADA);
//        reservaRepository.save(reserva);
//
//        return persistir(reserva, AuditoriaTipo.CHECK_OUT, resultado, imageIds, respostaTexto);
//    }

    private List<byte[]> buscarImagensReferencia(Reserva reserva) {
        return reserva.getSala().getImagens().stream()
                .map(mediaStorageClient::buscarImagem)
                .toList();
    }

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
        auditoria.setCategoria(resultado.getCategoria());
        auditoria.setImageIds(imageIds);
        auditoria.setResultadoIa(respostaTexto);

        return auditoriaRepository.save(auditoria);
    }

    private Reserva buscarReserva(Long reservaId) {
        return reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada: " + reservaId));
    }

    private void validarStatus(Reserva reserva, ReservaStatus statusEsperado, String mensagem) {
        if (reserva.getStatus() != statusEsperado) {
            throw new RuntimeException(mensagem);
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