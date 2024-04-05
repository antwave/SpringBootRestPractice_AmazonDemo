package AmazonDemo.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import AmazonDemo.Product.Model.Product;
import AmazonDemo.Product.Model.ProductDTO;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.price < :maxPrice")
    List<Product> findProductByMaxPrice(@Param("maxPrice") double maxPrice);

    @Query("SELECT new AmazonDemo.Product.Model.ProductDTO(p.name, p.description, p.price) FROM Product p")
    List<ProductDTO> listAllProductDTOs();

    List<Product> findByDescriptionContaining(String description);
}
