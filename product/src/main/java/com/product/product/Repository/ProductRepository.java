package com.product.product.Repository;




import com.product.product.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    boolean existsByProductId(String productId);     // check for duplicate productId

    @Transactional
    @Query(value = "SELECT * FROM product WHERE id = ?1", nativeQuery = true)
    Product getProductById(long id);

    @Transactional
    @Query(value = "SELECT * FROM product WHERE product_id = ?1", nativeQuery = true)
    Product getProductByProductId(String productId);

    @Transactional
    @Query(value = "SELECT * FROM product", nativeQuery = true)
    List<Product> getAllProducts();


}