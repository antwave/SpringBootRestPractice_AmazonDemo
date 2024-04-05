package AmazonDemo.Product.commandhandlers;

import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import AmazonDemo.Command;
import AmazonDemo.Exceptions.ProductNotFoundException;
import AmazonDemo.Product.ProductRepository;
import AmazonDemo.Product.Model.Product;

@Service
public class DeleteProductCommandHandler implements Command<Integer, ResponseEntity> {
    @Autowired
    ProductRepository productRepository;

    @Override
    public ResponseEntity<ResponseEntity> execute(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException();
        }
        productRepository.delete(product.get());
        return ResponseEntity.ok().build();
    }
}
