package org.storeishangul.sgulserver.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
public class RestClientConfig {

    @Bean(name = "dictionaryApi")
    public RestClient dictionaryApi(RestClient.Builder builder) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(List.of(
            MediaType.APPLICATION_JSON,
            MediaType.parseMediaType("text/json")
        ));

        return builder
            .baseUrl("https://opendict.korean.go.kr")
            .messageConverters(converters -> {
                converters.clear();
                converters.add(converter);
            })
            .build();
    }
}
