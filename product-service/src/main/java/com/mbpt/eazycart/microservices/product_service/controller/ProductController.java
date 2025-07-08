package com.mbpt.eazycart.microservices.product_service.controller;

import com.mbpt.eazycart.microservices.product_service.dto.ProductDTO;
import com.mbpt.eazycart.microservices.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        if(Objects.isNull(productDTO))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<ProductDTO>> findAllProduct(){
        List<ProductDTO> allProducts = productService.findAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(allProducts);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findProductById(@PathVariable Integer id) {
        ProductDTO foundProduct = productService.findProductById(id);
        if(Objects.isNull(foundProduct))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.status(HttpStatus.OK).body(foundProduct);
    }

    @PutMapping
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) {
        if(Objects.isNull(productDTO) || Objects.isNull(productDTO.getId()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        ProductDTO updatedProduct = productService.updateProduct(productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Integer id) {
        ProductDTO deletedProduct = productService.deleteProduct(id);
        if(Objects.isNull(deletedProduct))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.status(HttpStatus.OK).body(deletedProduct);
    }
}
