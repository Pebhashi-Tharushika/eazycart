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
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.List;

@Component
public class ProductClient {

    @Value("${product.service.url}")
    private String productServiceUrl;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(ProductClient.class);

    @Autowired
    private InterServiceCommunicationHandler interServiceCommunicationHandler;

    public List<ProductDTO> getAllProducts() {
        final HttpResponse<List<ProductDTO>> response = interServiceCommunicationHandler
                .interServiceCallByWebClient(productServiceUrl + "/all", new ParameterizedTypeReference<HttpResponse<List<ProductDTO>>>() {
                });
        if (response.statusCode() == 200) {
            return response.body();
        } else {
            String errorMessage = String.format("Product service returned status %d for /all. Response: %s", response.statusCode(), response.body());
            logger.error(errorMessage);
            throw new ProductServiceException(errorMessage);
        }
    }

    public ProductDTO findProductById(Integer product_id) {
        HttpResponse<ProductDTO> response = interServiceCommunicationHandler
                .interServiceCallByWebClient(productServiceUrl + "/" + product_id, new ParameterizedTypeReference<HttpResponse<ProductDTO>>() {
                });
        if (response.statusCode() == 200) {
            return response.body();
        } else if (response.statusCode() == 404) {
            String errorMessage = String.format("Product not found (status %d) for ID %d. Response: %s", response.statusCode(), product_id, response.body());
            logger.error(errorMessage);
            throw new InvalidOrderItemsException(errorMessage);
        } else {
            String errorMessage = String.format("Product service returned status %d for ID %d. Response: %s", response.statusCode(), product_id, response.body());
            logger.error(errorMessage);
            throw new ProductServiceException(errorMessage);
        }
    }

}