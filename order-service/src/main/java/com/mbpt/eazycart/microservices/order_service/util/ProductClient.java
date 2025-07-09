package com.mbpt.eazycart.microservices.order_service.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbpt.eazycart.microservices.order_service.dto.ProductDTO;
import com.mbpt.eazycart.microservices.order_service.entity.ProductEntity;
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

    public List<ProductDTO> getAllProducts() {
        try {
            final HttpResponse<String> response = InterServiceCommunicationHandler.interServiceCall(productServiceURL + "/all");
            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<>() {
                });
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to fetch all products ", e);
        }
        return null;
    }

    public ProductEntity findProductById(Integer product_id) {
        try {
            final HttpResponse<String> response = InterServiceCommunicationHandler.interServiceCall(productServiceURL + "/" + product_id);
            if (response.statusCode() == 200) {
                ProductDTO productDTO = objectMapper.readValue(response.body(), ProductDTO.class);
                return objectMapper.convertValue(productDTO, ProductEntity.class);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to fetch product with id " + product_id, e);
        }
        return null;
    }

}
