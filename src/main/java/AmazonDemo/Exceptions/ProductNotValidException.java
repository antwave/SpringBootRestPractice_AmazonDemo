package AmazonDemo.Exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import AmazonDemo.Product.Model.Product;

public class ProductNotValidException extends CustomBaseException {
    private static final Logger logger = LoggerFactory.getLogger(ProductNotValidException.class);

    public ProductNotValidException(String message, Product product) {
        super(HttpStatus.BAD_REQUEST, new SimpleResponse(message));
        logger.error("Product Not Valid Exception thrown when creating product: " + product.toString());
    }

}
