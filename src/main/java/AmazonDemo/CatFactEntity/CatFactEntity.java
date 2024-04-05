package AmazonDemo.CatFactEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

import AmazonDemo.CatFact.CatFact;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cat_facts")
@AllArgsConstructor
@NoArgsConstructor
public class CatFactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "catfactJSON")
    private String catFactJSON;

    public CatFactEntity(CatFact catFact) {
        this.catFactJSON = converToJSON(catFact);
    }

    private String converToJSON(CatFact catFact) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(catFact);
        } catch (Exception e) {
            throw new RuntimeException("JSON Parse error");
        }
    }

    public CatFact convertToCatFact() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(catFactJSON, CatFact.class);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing CatFact for deserialization");
        }
    }
}
