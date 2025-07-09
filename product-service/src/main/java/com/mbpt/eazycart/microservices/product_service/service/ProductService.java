package com.mbpt.eazycart.microservices.product_service.service;

import com.mbpt.eazycart.microservices.product_service.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<ProductDTO> findAllProducts();

    ProductDTO findProductById(Integer id);

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(ProductDTO productDTO);

    ProductDTO deleteProduct(Integer id);
}