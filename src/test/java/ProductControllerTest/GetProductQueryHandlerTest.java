package ProductControllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import AmazonDemo.AmazonDemoApplication;
import AmazonDemo.Exceptions.ProductNotFoundException;
import AmazonDemo.Product.ProductRepository;
import AmazonDemo.Product.Model.Product;
import AmazonDemo.Product.Model.ProductDTO;
import AmazonDemo.Product.queryhandlers.GetProductQueryHandler;

@SpringBootTest(classes = AmazonDemoApplication.class)
public class GetProductQueryHandlerTest {

    @InjectMocks
    private GetProductQueryHandler getProductQueryHandler;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void getProductQueryHandler_validId_returnsProductDTO() {
        Product product = new Product(1, "Dark Chocolate", "Best Chocolate", 9.99, 10);

        ProductDTO expectedDTO = new ProductDTO(product);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        ResponseEntity<ProductDTO> response = getProductQueryHandler.execute(product.getId());
        assertEquals(expectedDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getProductQueryHandler_invalidId_throwsProductNotFoundException() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        ProductNotFoundException ex = assertThrows(ProductNotFoundException.class,
                () -> getProductQueryHandler.execute(1));
        assertEquals("Product Not Found", ex.getSimpleResponse().getMessage());
    }
}
