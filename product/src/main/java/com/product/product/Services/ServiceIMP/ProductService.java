package com.product.product.Services.ServiceIMP;

import com.product.product.Dto.ProductDto;
import com.product.product.Entity.Product;
import com.product.product.Exception.DuplicateException;
import com.product.product.Exception.NotFoundException;
import com.product.product.Repository.ProductRepository;
import com.product.product.Services.ProductServices;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements ProductServices {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String addProduct(ProductDto productDto) {
        // Check if productId already exists
        if (productRepository.existsByProductId(productDto.getProductId())) {
            throw new DuplicateException("Product with ID '" + productDto.getProductId() + "' already exists.");
        }

        // Hibernate will automatically set createdAt and updatedAt
        Product product = modelMapper.map(productDto, Product.class);
        productRepository.save(product);
        return "Product added successfully with name : " + productDto.getProductName();
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.getAllProducts();
        return modelMapper.map(products, new TypeToken<List<ProductDto>>() {}.getType());
    }

    @Override
    public ProductDto getProductByProductId(String ProductId) {
        Product product = productRepository.getProductByProductId(ProductId);
        if (product == null) {
            throw new NotFoundException("Product not found with id : " + ProductId);
        }
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public String updateProduct(long id, ProductDto productDto) {
        Product existingProduct = productRepository.getProductById(id);
        if (existingProduct == null) {
            throw new NotFoundException("Product not found with id : " + id);
        }

        // Check if the new productId already belongs to a DIFFERENT product
        if (!existingProduct.getProductId().equals(productDto.getProductId()) &&
                productRepository.existsByProductId(productDto.getProductId())) {
            throw new DuplicateException("Product with ID '" + productDto.getProductId() + "' already exists.");
        }

        existingProduct.setProductId(productDto.getProductId());
        existingProduct.setProductName(productDto.getProductName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setForSale(productDto.isForSale());

        // Hibernate will automatically update updatedAt when you save
        productRepository.save(existingProduct);
        return "Product updated successfully with id : " + id;
    }

    @Override
    public String deleteProduct(long id) {

        Product product = productRepository.getProductById(id);
        if (product == null) {
            throw new NotFoundException("Product not found with id : " + id);
        }

        productRepository.deleteById(id);

        return "Product deleted successfully with id : " + id;
    }

}