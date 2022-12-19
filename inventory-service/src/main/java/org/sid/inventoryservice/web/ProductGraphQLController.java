package org.sid.inventoryservice.web;

import org.sid.inventoryservice.entities.Product;
import org.sid.inventoryservice.repositories.ProductRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProductGraphQLController {
    private ProductRepository productRepository;

    public ProductGraphQLController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryMapping
    public List<Product> productList(){
        return productRepository.findAll();
    }
    @QueryMapping
    public Product productById(@Argument String id){
        return productRepository.findById(id).orElseThrow(()->new RuntimeException(String.format("Product %s not found",id)));
    }
}
