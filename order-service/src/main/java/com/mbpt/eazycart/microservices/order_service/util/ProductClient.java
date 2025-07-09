package com.mbpt.eazycart.microservices.order_service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbpt.eazycart.microservices.order_service.dto.ProductDTO;
import com.mbpt.eazycart.microservices.order_service.exception.InvalidOrderItemsException;
import com.mbpt.eazycart.microservices.order_service.exception.ProductServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductClient {

    @Value("${product.service.url}")
    private String productServiceURL;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InterServiceCommunicationHandler interServiceCommunicationHandler;

    private static final Logger logger = LoggerFactory.getLogger(ProductClient.class);

    public List<ProductDTO> getAllProducts() {
        final ResponseEntity<List<ProductDTO>> response = interServiceCommunicationHandler.interServiceCallByRestTemplate(
                productServiceURL + "/all", new ParameterizedTypeReference<>() {
                });
        if (response.getStatusCode().value() == 200) {
            return response.getBody();
        } else {
            String errorMessage = String.format("Product service returned status %d for /all. Response: %s", response.getStatusCode().value(), response.getBody());
            logger.error(errorMessage);
            throw new ProductServiceException(errorMessage);
        }
    }

    public ProductDTO findProductById(Integer product_id) {
        ResponseEntity<ProductDTO> response = interServiceCommunicationHandler.interServiceCallByRestTemplate(
                productServiceURL + "/" + product_id, new ParameterizedTypeReference<>() {
                });
        if (response.getStatusCode().value() == 200) {
            return response.getBody();
        } else if (response.getStatusCode().value() == 404) {
            String errorMessage = String.format("Product not found (status %d) for ID %d. Response: %s", response.getStatusCode().value(), product_id, response.getBody());
            logger.error(errorMessage);
            throw new InvalidOrderItemsException(errorMessage);
        } else {
            String errorMessage = String.format("Product service returned status %d for ID %d. Response: %s", response.getStatusCode().value(), product_id, response.getBody());
            logger.error(errorMessage);
            throw new ProductServiceException(errorMessage);
        }
    }

}
