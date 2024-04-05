package AmazonDemo.Headers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import AmazonDemo.Product.Model.Product;

@RestController
public class HeaderController {

    @GetMapping(value = "/header", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Product> getProduct() {
        Product product = new Product();
        product.setName("myProduct");
        product.setId(1);
        product.setDescription("cool product");
        return ResponseEntity.ok(product);
    }

}
