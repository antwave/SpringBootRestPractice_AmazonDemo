package ProductControllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import AmazonDemo.AmazonDemoApplication;
import AmazonDemo.Exceptions.ProductNotValidException;
import AmazonDemo.Product.ProductRepository;
import AmazonDemo.Product.Model.Product;
import AmazonDemo.Product.commandhandlers.CreateProductCommandHandler;

@SpringBootTest(classes = AmazonDemoApplication.class)
public class CreateProductCommandHandlerTest {

    @InjectMocks
    private CreateProductCommandHandler createProductCommandHandler;

    @Mock
    private ProductRepository productRepository;

    // Given -> When -> Then

    // MethodName_StateUnderTest_ExpectedBehavior
    @Test
    public void createProductCommandHandler_validProduct_returnsSuccess() {
        // Given
        Product product = new Product(1, "Dark Chocolate", "Best Chocolate", 9.99, 10);
        // When
        ResponseEntity response = createProductCommandHandler.execute(product);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void createProductCommandHandler_invalidPrice_returnsInvalidPriceException() {
        // Given
        Product product = new Product(1, "Dark Chocolate", "Best Chocolate", -9.99, 10);
        // When / Then
        ProductNotValidException ex = assertThrows(ProductNotValidException.class,
                () -> createProductCommandHandler.execute(product));
        // Then.2
        assertEquals("Product price cannot be lower than 0", ex.getSimpleResponse().getMessage());
    }
}
