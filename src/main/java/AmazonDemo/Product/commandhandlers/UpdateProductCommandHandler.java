package AmazonDemo.Product.commandhandlers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import AmazonDemo.Command;
import AmazonDemo.Exceptions.ProductNotFoundException;
import AmazonDemo.Product.ProductRepository;
import AmazonDemo.Product.Model.Product;
import AmazonDemo.Product.Model.UpdateProductCommand;

@Service
public class UpdateProductCommandHandler implements Command<UpdateProductCommand, ResponseEntity> {
    @Autowired
    ProductRepository productRepository;

    @Override
    public ResponseEntity<ResponseEntity> execute(UpdateProductCommand command) {
        Optional<Product> optionalProduct = productRepository.findById(command.getId());

        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException();
        }

        Product product = command.getProduct();
        product.setId(command.getId());
        productRepository.save(product);
        return ResponseEntity.ok().build();
    }

}
