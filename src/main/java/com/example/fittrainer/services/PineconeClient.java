package com.example.fittrainer.services;

import com.example.fittrainer.dtos.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PineconeClient {


    @Value("${pinecone.api.key}")
    private String pineconeKey;

    public void upsert(UpsertRequestDTO requestDTO) {
        System.out.println("Upserting vectors to Pinecone...");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Api-Key", pineconeKey);

        HttpEntity<UpsertRequestDTO> requestEntity = new HttpEntity<>(requestDTO, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "https://workout-5a09f7f.svc.us-west1-gcp-free.pinecone.io/vectors/upsert",
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        System.out.println(responseEntity.getBody());
    }

    public QueryResponseDTO query(QueryRequestDTO requestDTO) {
        System.out.println("Querying Pinecone...");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Api-Key", pineconeKey);

        HttpEntity<QueryRequestDTO> requestEntity = new HttpEntity<>(requestDTO, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<QueryResponseDTO> responseEntity = restTemplate.exchange(
                "https://workout-5a09f7f.svc.us-west1-gcp-free.pinecone.io/query",
                HttpMethod.POST,
                requestEntity,
                QueryResponseDTO.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            QueryResponseDTO queryResponseDTO = responseEntity.getBody();
            System.out.println(queryResponseDTO);
            return queryResponseDTO;
        } else {
            System.out.println("Query failed with status: " + responseEntity.getStatusCode());
            return null;
        }
    }

}
