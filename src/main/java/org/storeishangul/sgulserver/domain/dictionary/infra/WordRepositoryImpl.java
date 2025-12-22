package org.storeishangul.sgulserver.domain.dictionary.infra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;
import org.storeishangul.sgulserver.domain.dictionary.domain.vo.OpenDictSearchResult;

@Repository
public class WordRepositoryImpl implements WordRepository{

    private final RestClient dictionaryApi;


    @Value("${urrimal.api.key}")
    private String URRIMAL_API_KEY;

    public WordRepositoryImpl(RestClient dictionaryApi) {
        this.dictionaryApi = dictionaryApi;
    }

    @Override
    public OpenDictSearchResult search(String word) {

        return dictionaryApi.get()
            .uri(uriBuilder -> uriBuilder
                .path("/api/search")
                .queryParam("key", URRIMAL_API_KEY)
                .queryParam("q", word)
                .queryParam("req_type", "json")
                .queryParam("method", "exact")
                .queryParam("pos", 1) // 1 == 명사
                .build()
            )
            .retrieve()
            .body(OpenDictSearchResult.class);
    }
}
