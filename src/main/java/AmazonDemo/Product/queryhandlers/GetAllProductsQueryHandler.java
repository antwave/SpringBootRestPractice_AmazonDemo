package AmazonDemo.Product.queryhandlers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import AmazonDemo.Query;
import AmazonDemo.Product.ProductRepository;
import AmazonDemo.Product.Model.ProductDTO;

@Service
public class GetAllProductsQueryHandler implements Query<Void, List<ProductDTO>> {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity<List<ProductDTO>> execute(Void input) {
        List<ProductDTO> productDTOs = productRepository.listAllProductDTOs();
        return ResponseEntity.ok(productDTOs);
    }
}
