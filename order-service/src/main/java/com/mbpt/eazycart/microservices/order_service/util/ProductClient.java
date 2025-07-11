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
        final  List<ProductDTO> response = interServiceCommunicationHandler
                .interServiceCallByWebClient(productServiceUrl + "/all", new ParameterizedTypeReference<List<ProductDTO>>() {
                });
        return response;
    }

    public ProductDTO findProductById(Integer product_id) {
       final ProductDTO response = interServiceCommunicationHandler
                .interServiceCallByWebClient(productServiceUrl + "/" + product_id, new ParameterizedTypeReference<ProductDTO>() {
                });
        return response;
    }

}