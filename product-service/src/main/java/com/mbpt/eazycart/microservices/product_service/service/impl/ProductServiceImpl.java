package com.mbpt.eazycart.microservices.product_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbpt.eazycart.microservices.product_service.dto.ProductDTO;
import com.mbpt.eazycart.microservices.product_service.entity.ProductEntity;
import com.mbpt.eazycart.microservices.product_service.repository.ProductRepository;
import com.mbpt.eazycart.microservices.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ObjectMapper mapper;

    @Override
    public List<ProductDTO> findAllProducts() {
        List<ProductEntity> allProductsEntities = productRepository.findAll();
        return allProductsEntities.stream().map(entity -> mapper.convertValue(entity, ProductDTO.class)).toList();
    }

    @Override
    public ProductDTO findProductById(Integer id) {
        ProductEntity foundProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return mapper.convertValue(foundProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        ProductEntity createdProduct = productRepository.save(mapper.convertValue(productDTO, ProductEntity.class));
        return mapper.convertValue(createdProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        ProductEntity productEntity = mapper.convertValue(productDTO, ProductEntity.class);
        ProductEntity foundProduct = productRepository.findById(productDTO.getId()).orElseThrow(() -> new RuntimeException("Product not found"));
        foundProduct.setName(productEntity.getName());
        foundProduct.setDescription(productEntity.getDescription());
        foundProduct.setPrice(productEntity.getPrice());
        foundProduct.setStock(productEntity.getStock());
        ProductEntity updatedProductEntity = productRepository.save(foundProduct);
        return mapper.convertValue(updatedProductEntity, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Integer id) {
        ProductEntity foundProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        ProductDTO deletedProduct = mapper.convertValue(foundProduct, ProductDTO.class);
        productRepository.deleteById(id);
        return deletedProduct;
    }
}
