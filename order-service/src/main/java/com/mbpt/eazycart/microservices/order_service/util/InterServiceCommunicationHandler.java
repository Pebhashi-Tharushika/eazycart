package com.mbpt.eazycart.microservices.order_service.util;

import com.mbpt.eazycart.microservices.order_service.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class InterServiceCommunicationHandler {

    private final WebClient webClient;

    @Autowired
    public InterServiceCommunicationHandler(WebClient webClient) {
        this.webClient = webClient;
    }

   public <T> T interServiceCallByWebClient(String url, ParameterizedTypeReference<T> typeRef) {
    return webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(typeRef)
            .block();
}

}