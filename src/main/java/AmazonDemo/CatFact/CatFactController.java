package AmazonDemo.CatFact;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AmazonDemo.CatFactEntity.CatFactEntity;
import AmazonDemo.CatFactEntity.CatFactRepository;

@RestController
@RequestMapping("/catfact")
public class CatFactController {

    @Autowired
    private CatFactQueryHandler catFactQueryHandler;

    @Autowired
    private CatFactRepository catFactRepository;

    @GetMapping
    public ResponseEntity<CatFactDTO> getCatFact() {
        return catFactQueryHandler.execute(null);
    }

    @GetMapping("/local")
    public ResponseEntity<List<CatFact>> getSavedCatFacts() {
        return ResponseEntity.ok(catFactRepository
                .findAll()
                .stream()
                .map(CatFactEntity::convertToCatFact)
                .collect(Collectors.toList()));
    }
}
