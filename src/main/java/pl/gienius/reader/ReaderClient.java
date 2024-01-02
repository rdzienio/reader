package pl.gienius.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ReaderClient {

    Logger logger = LoggerFactory.getLogger(ReaderClient.class);

    private final String baseUrl = "http://localhost:3000/api/";

    private final String readerUrl = baseUrl + "/readers/";

    private final RestTemplate restTemplate;

    public ReaderClient() {
        this.restTemplate = new RestTemplate();
    }

    public Reader createReader(String name){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Reader> request = new HttpEntity<>(new Reader(name), headers);

        ResponseEntity<Reader> response = restTemplate.postForEntity(readerUrl + "addReader", request, Reader.class);
        logger.info("Sent request to addReader " + name);
        return response.getBody();
    }
}
