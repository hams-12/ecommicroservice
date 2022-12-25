package org.sid.inventoryservice.web;

import org.sid.inventoryservice.dto.ProductRequestDTO;
import org.sid.inventoryservice.entities.Category;
import org.sid.inventoryservice.entities.Product;
import org.sid.inventoryservice.repositories.CategoryRepository;
import org.sid.inventoryservice.repositories.ProductRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class ProductGraphQLController {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductGraphQLController(ProductRepository productRepository,CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @QueryMapping
    public List<Product> productList(){
        return productRepository.findAll();
    }
    @QueryMapping
    public Product productById(@Argument String id){
        return productRepository.findById(id).orElseThrow(()->new RuntimeException(String.format("Product %s not found",id)));
    }

    @MutationMapping
    public Product saveProduct(@Argument ProductRequestDTO product){

        //Tout ce grand travail qu'on est entrai d'effectuer ici doit être fait normalement dans le service
        //Et le fait de prendre les données du dto et de les transferer vers l'objet entité, c'est le travail normalement d'un mapper

        Product productToSave = new Product();
        //On va ajouter le orElse(null) à la fin pour laisser la possibilité d'ajouter un produit sans categorie
        Category category = categoryRepository.findById(product.categoryId()).orElse(null);
        productToSave.setId(UUID.randomUUID().toString());
        productToSave.setName(product.name());
        productToSave.setPrice(product.price());
        productToSave.setQuantity(product.quantity());
        productToSave.setCategory(category);

        //product.setId(UUID.randomUUID().toString());
        return productRepository.save(productToSave);
    }
    @MutationMapping
    public Product updateProduct(@Argument String id, @Argument ProductRequestDTO product){

        Product productToUpdate = productRepository.findById(id).orElse(null);
        //Product productToUpdate = new Product();

        Category category = categoryRepository.findById(product.categoryId()).orElse(null);
        productToUpdate.setId(id);
        productToUpdate.setName(product.name());
        productToUpdate.setPrice(product.price());
        productToUpdate.setQuantity(product.quantity());
        productToUpdate.setCategory(category);

        return productRepository.save(productToUpdate);
    }
    @MutationMapping
    public void removeProduct(@Argument String id){
        productRepository.deleteById(id);
    }
}
