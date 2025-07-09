package com.mbpt.eazycart.microservices.order_service.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbpt.eazycart.microservices.order_service.dto.ProductDTO;
import com.mbpt.eazycart.microservices.order_service.exception.InvalidOrderItemsException;
import com.mbpt.eazycart.microservices.order_service.exception.ProductServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

@Component
public class ProductClient {

    @Value("${product.service.url}")
    private String productServiceURL;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(ProductClient.class);

    public List<ProductDTO> getAllProducts() {
        try {
            final HttpResponse<String> response = InterServiceCommunicationHandler.interServiceCall(productServiceURL + "/all");
            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<>() {
                });
            } else {
                String errorMessage = String.format("Product service returned status %d for /all. Response: %s", response.statusCode(), response.body());
                logger.error(errorMessage);
                throw new ProductServiceException(errorMessage);
            }
        } catch (IOException | InterruptedException e) {
            logger.error("Failed to fetch all products from {}: {}", productServiceURL + "/all", e.getMessage(), e);
            throw new ProductServiceException("Failed to communicate with product service.", e);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while fetching all products: {}", e.getMessage(), e);
            throw new ProductServiceException("An unexpected error occurred while fetching products.", e);
        }
    }

    public ProductDTO findProductById(Integer product_id) {
        try {
            final HttpResponse<String> response = InterServiceCommunicationHandler.interServiceCall(productServiceURL + "/" + product_id);
            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), ProductDTO.class);
            } else if (response.statusCode() == 404) {
                String errorMessage = String.format("Product not found (status %d) for ID %d. Response: %s", response.statusCode(), product_id, response.body());
                logger.error(errorMessage);
                throw new InvalidOrderItemsException(errorMessage);
            } else {
                String errorMessage = String.format("Product service returned status %d for ID %d. Response: %s", response.statusCode(), product_id, response.body());
                logger.error(errorMessage);
                throw new ProductServiceException(errorMessage);
            }
        } catch (IOException | InterruptedException e) {
            logger.error("Failed to fetch product with id {} from {}: {}", product_id, productServiceURL + "/" + product_id, e.getMessage(), e);
            throw new ProductServiceException("Failed to communicate with product service for ID: " + product_id, e);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while fetching product with id {}: {}", product_id, e.getMessage(), e);
            throw new ProductServiceException("An unexpected error occurred while fetching product.", e);
        }
    }

}