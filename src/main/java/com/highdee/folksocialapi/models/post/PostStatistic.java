package com.highdee.folksocialapi.models.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name="post_statistics")
public class PostStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(columnDefinition = "json", nullable = false)
    private String data;

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public Map<String, Object> getDataAsMap() {
        try {
            return objectMapper.readValue(this.data, Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON to Map", e);
        }
    }


    public void setDataFromMap(Map<String, Object> dataMap) {
        try {
            this.data = objectMapper.writeValueAsString(dataMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting Map to JSON", e);
        }
    }
}
