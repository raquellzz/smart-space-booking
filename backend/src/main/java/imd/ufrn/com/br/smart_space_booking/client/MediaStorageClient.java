package imd.ufrn.com.br.smart_space_booking.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MediaStorageClient {

    private final RestTemplate restTemplate;

    @Value("${media.server.url}")
    private String mediaServerUrl;

    public MediaStorageClient() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Busca os bytes de uma imagem no MS de mídia pelo ID.
     * O endpoint do MS é: GET /v1/files/{id}
     * O ID é armazenado como String na entidade mas o MS usa Long.
     */
    public byte[] buscarImagem(String imageId) {
        String url = mediaServerUrl + "/" + Long.parseLong(imageId);
        return restTemplate.getForObject(url, byte[].class);
    }
}