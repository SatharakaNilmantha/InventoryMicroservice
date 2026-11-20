package com.product.product.Services;


import com.product.product.Dto.ProductDto;

import java.util.List;

public interface ProductServices {
    String addProduct(ProductDto productDto);
    List<ProductDto> getAllProducts();
    ProductDto getProductByProductId(String productId);
    String updateProduct(long id, ProductDto productDto);
    String deleteProduct(long id);
}