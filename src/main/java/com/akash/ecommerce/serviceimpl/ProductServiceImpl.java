package com.akash.ecommerce.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.akash.ecommerce.entity.Product;
import com.akash.ecommerce.repository.ProductRepository;
import com.akash.ecommerce.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService  {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAllProducts() {
        List<Product> products = productRepository.findAll();
        if(products.isEmpty()) {
            return null;
        }else {
            return products;
        }
    }

    @Override
    public Product findProductById(long productId) {
        Optional<Product> optional = productRepository.findById(productId);

        if(optional.isEmpty()) {
            return null;
        }else {
            Product product = optional.get();
            return product;
        }
    }

    @Override
    public Product updateProduct(long productId, Product updateProduct) {
        Optional<Product> optional = productRepository.findById(productId);

        if(optional.isEmpty()) {
            return null;
        }else {
            Product existingProduct = optional.get();
            updateProduct.setId(existingProduct.getId());
            return productRepository.save(updateProduct);
        }
    }

    @Override
    public Product deleteProduct(long productId) {
        Optional<Product> optional = productRepository.findById(productId);

        if(optional.isEmpty()) {
            return null;
        }else {
            Product product = optional.get();
            productRepository.delete(product);
            return product;
        }
    }

    @Override
    public List<Product> findByCategoryId(Long categoryId) {

    List<Product> products = productRepository.findByCategory_Id(categoryId);

    if (products.isEmpty()) {
        throw new RuntimeException("No products found for this category");
    }

    return products;
}
    public List<Product> findByIsLatestTrue()
    {
        List<Product> products= productRepository.findByIsLatestTrue();
        if(products.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No Product found");
        }else {
            return products;
        }
    }

    public void toggleLatestStatus(Long productId)
    {
        Optional<Product> product= productRepository.findById(productId);
        if(product.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No Product found with this "+productId);
        }else {
            Product prod= product.get();
            prod.setLatest(!prod.isLatest());
            productRepository.save(prod);
        }
    }


}