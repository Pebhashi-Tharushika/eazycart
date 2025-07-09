package com.mbpt.eazycart.microservices.order_service.util;

import com.mbpt.eazycart.microservices.order_service.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
public class InterServiceCommunicationHandler {

    @Autowired
    private RestTemplate restTemplate;

    public HttpResponse<String> interServiceCallByHttpClient(String url) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    }

    public <T> ResponseEntity<T> interServiceCallByRestTemplate(String url, ParameterizedTypeReference<T> typeRef) {
        return restTemplate.exchange(url, HttpMethod.GET, null, typeRef);
    }

}
