
package AmazonDemo.Product.commandhandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import AmazonDemo.Command;
import AmazonDemo.Exceptions.ProductNotValidException;
import AmazonDemo.Product.ProductRepository;
import AmazonDemo.Product.Model.Product;
import io.micrometer.common.util.StringUtils;

@Service
public class CreateProductCommandHandler implements Command<Product, ResponseEntity> {
    @Autowired
    private ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(CreateProductCommandHandler.class);

    @Override
    public ResponseEntity execute(Product product) {
        logger.info("Creating product using " + getClass() + " with Product " + product.toString());
        validateProduct(product);
        productRepository.save(product);
        return ResponseEntity.ok().build();
    }

    private void validateProduct(Product product) {
        if (StringUtils.isBlank(product.getName())) {
            throw new ProductNotValidException("Product name cannot be empty", product);
        }

        if (StringUtils.isBlank(product.getDescription())) {
            throw new ProductNotValidException("Product description cannot be empty", product);
        }

        if (product.getPrice() <= 0.0) {
            throw new ProductNotValidException("Product price cannot be lower than 0", product);
        }

        if (product.getQuantity() <= 0) {
            throw new ProductNotValidException("PRoduct quantity cannot be lower than 0", product);
        }
    }
}
