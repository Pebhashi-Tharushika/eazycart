package com.mbpt.eazycart.microservices.order_service.util;

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
    private String productServiceUrl;

    private static final Logger logger = LoggerFactory.getLogger(ProductClient.class);

    @Autowired
    private InterServiceCommunicationHandler interServiceCommunicationHandler;

    public List<ProductDTO> getAllProducts() {
        final ResponseEntity<List<ProductDTO>> response = interServiceCommunicationHandler
                .interServiceCallByWebClient(productServiceUrl + "/all", new ParameterizedTypeReference<List<ProductDTO>>() {
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
        ResponseEntity<ProductDTO> response = interServiceCommunicationHandler
                .interServiceCallByWebClient(productServiceUrl + "/" + product_id, new ParameterizedTypeReference<ProductDTO>() {
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