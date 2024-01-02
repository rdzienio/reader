package pl.gienius.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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

    public List<Book> getAvailableBooks(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String availableUrl = baseUrl + "available";

        HttpEntity<?> request = new HttpEntity<>(headers);

        ResponseEntity<List<Book>> response = restTemplate.exchange(
                availableUrl,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<Book>>() {});
        logger.info("Got available books");
        return response.getBody();
    }

    public boolean rentBook(Long bookId, Long readerId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Reader-ID", readerId.toString());

        String rentUrl = readerUrl + "rent/" + bookId;

        HttpEntity<?> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<Void> response = restTemplate.postForEntity(rentUrl, request, Void.class);
            logger.info("Book rented: " + bookId);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.CONFLICT){
                logger.info("Book: " + bookId + "is blocked!");
                return false;
            }
            else throw e;
        }
    }

    public boolean returnBook(Long bookId, Long readerId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Reader-ID", readerId.toString());

        String rentUrl = readerUrl + "rent/" + bookId;

        HttpEntity<?> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<Void> response = restTemplate.exchange(rentUrl, HttpMethod.DELETE, request, Void.class);
            logger.info("Book rented: " + bookId);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.CONFLICT){
                logger.info("Something went wrong when returning the book: " + bookId + "...");
                return false;
            }
            else throw e;
        }
    }

}
