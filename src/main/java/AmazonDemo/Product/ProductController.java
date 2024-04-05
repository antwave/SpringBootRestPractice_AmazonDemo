package AmazonDemo.Product;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AmazonDemo.Product.Model.Product;
import AmazonDemo.Product.Model.ProductDTO;
import AmazonDemo.Product.Model.UpdateProductCommand;
import AmazonDemo.Product.commandhandlers.CreateProductCommandHandler;
import AmazonDemo.Product.commandhandlers.DeleteProductCommandHandler;
import AmazonDemo.Product.commandhandlers.UpdateProductCommandHandler;
import AmazonDemo.Product.queryhandlers.GetAllProductsQueryHandler;
import AmazonDemo.Product.queryhandlers.GetProductQueryHandler;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private GetAllProductsQueryHandler getAllProductsQueryHandler;

    @Autowired
    private GetProductQueryHandler getProductQueryHandler;

    @Autowired
    private CreateProductCommandHandler createProductCommandHandler;

    @Autowired
    private UpdateProductCommandHandler updateProductCommandHandler;

    @Autowired
    private DeleteProductCommandHandler deleteProductCommandHandler;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts() {
        return getAllProductsQueryHandler.execute(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") Integer id) {
        return getProductQueryHandler.execute(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> findProductsByDescription(
            @RequestParam(value = "description") String description) {
        return ResponseEntity.ok(productRepository.findByDescriptionContaining(description));
    }

    @GetMapping("/search/{maxPrice}")
    public ResponseEntity<List<Product>> findProductsByMaxPrice(@PathVariable("maxPrice") Double maxPrice) {
        return ResponseEntity.ok(productRepository.findProductByMaxPrice(maxPrice));

    }

    @PostMapping
    public ResponseEntity createProduct(@RequestBody Product product) {
        return createProductCommandHandler.execute(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateProduct(@PathVariable("id") Integer id, @RequestBody Product product) {
        UpdateProductCommand command = new UpdateProductCommand(id, product);
        return updateProductCommandHandler.execute(command);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") Integer id) {
        return deleteProductCommandHandler.execute(id);
    }
}
