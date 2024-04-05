package AmazonDemo.Product.queryhandlers;

import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import AmazonDemo.Query;
import AmazonDemo.Exceptions.ProductNotFoundException;
import AmazonDemo.Product.ProductRepository;
import AmazonDemo.Product.Model.Product;
import AmazonDemo.Product.Model.ProductDTO;

@Service
public class GetProductQueryHandler implements Query<Integer, ProductDTO> {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity<ProductDTO> execute(Integer id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new ProductNotFoundException();
        }

        return ResponseEntity.ok(new ProductDTO(product.get()));
    }

}
