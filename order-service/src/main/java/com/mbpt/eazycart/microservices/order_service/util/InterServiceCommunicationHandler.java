package com.mbpt.eazycart.microservices.order_service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class InterServiceCommunicationHandler {

    private final WebClient webClient;

    @Autowired
    public InterServiceCommunicationHandler(WebClient webClient) {
        this.webClient = webClient;
    }

    public <T> ResponseEntity<T> interServiceCallByWebClient(String url, ParameterizedTypeReference<T> typeRef) {
        return webClient.get()
                .uri(url)
                .exchangeToMono(response -> response.toEntity(typeRef))
                .block();
    }

}