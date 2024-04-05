package AmazonDemo.CatFact;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import AmazonDemo.Query;
import AmazonDemo.CatFactEntity.CatFactEntity;
import AmazonDemo.CatFactEntity.CatFactRepository;
import AmazonDemo.Exceptions.CatFactDownException;

@Service
public class CatFactQueryHandler implements Query<Void, CatFactDTO> {

    private final String CAT_FACT_URL = "https://catfact.ninja/fact";
    private final RestTemplate restTemplate;
    private final CatFactRepository catFactRepository;

    public CatFactQueryHandler(RestTemplate restTemplate, CatFactRepository catFactRepository) {
        this.restTemplate = restTemplate;
        this.catFactRepository = catFactRepository;
    }

    @Override
    public ResponseEntity<CatFactDTO> execute(Void input) {
        CatFact catFact = getCatFact();
        CatFactDTO catFactDTO = new CatFactDTO(catFact.getFact());
        return ResponseEntity.ok(catFactDTO);

    }

    private CatFact getCatFact() {
        try {
            CatFact catFact = restTemplate.getForObject(CAT_FACT_URL, CatFact.class);
            catFactRepository.save(new CatFactEntity(catFact));
            return catFact;
        } catch (Exception e) {
            throw new CatFactDownException();
        }
    }
}