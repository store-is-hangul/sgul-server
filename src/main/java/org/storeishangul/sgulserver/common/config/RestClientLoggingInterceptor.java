package org.storeishangul.sgulserver.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RestClientLoggingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution) throws IOException {

        long startTime = System.currentTimeMillis();

        try {
            // Log request
            logRequest(request);

            // Execute the request
            ClientHttpResponse response = execution.execute(request, body);

            // Calculate duration
            long duration = System.currentTimeMillis() - startTime;

            // Wrap response to allow body to be read multiple times
            BufferedClientHttpResponse bufferedResponse = new BufferedClientHttpResponse(response);

            // Log response
            logResponse(bufferedResponse, duration);

            return bufferedResponse;

        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            log.error("<<<<< DICTIONARY API ERROR: duration: {}ms, error: {}", duration, e.getMessage());
            throw e;
        }
    }

    private void logRequest(HttpRequest request) {
        String uri = request.getURI().toString();
        String maskedUri = maskSensitiveData(uri);
        log.info(">>>>> DICTIONARY API REQUEST: [{}] {}", request.getMethod(), maskedUri);
    }

    private void logResponse(BufferedClientHttpResponse response, long duration) throws IOException {
        String responseBody = response.getBodyAsString();
        String bodySummary = summarizeResponseBody(responseBody);

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("<<<<< DICTIONARY API RESPONSE: [{}] duration: {}ms, body: {}",
                    response.getStatusCode(), duration, bodySummary);
        } else {
            log.error("<<<<< DICTIONARY API ERROR: [{}] duration: {}ms, body: {}",
                    response.getStatusCode(), duration, bodySummary);
        }
    }

    private String maskSensitiveData(String uri) {
        // Mask the API key parameter
        return uri.replaceAll("([?&]key=)[^&]*", "$1***MASKED***");
    }

    private String summarizeResponseBody(String body) {
        if (body == null || body.isEmpty()) {
            return "EMPTY";
        }

        if (body.length() > 1000) {
            return body.substring(0, 1000) + "... [TRUNCATED]";
        }

        return body;
    }

    /**
     * Wrapper class to allow reading the response body multiple times
     */
    private static class BufferedClientHttpResponse implements ClientHttpResponse {
        private final ClientHttpResponse response;
        private byte[] body;

        public BufferedClientHttpResponse(ClientHttpResponse response) throws IOException {
            this.response = response;
            // Read the body once and cache it
            InputStream bodyStream = response.getBody();
            this.body = bodyStream.readAllBytes();
        }

        public String getBodyAsString() {
            return new String(body, StandardCharsets.UTF_8);
        }

        @Override
        public InputStream getBody() throws IOException {
            return new ByteArrayInputStream(body);
        }

        @Override
        public org.springframework.http.HttpHeaders getHeaders() {
            return response.getHeaders();
        }

        @Override
        public org.springframework.http.HttpStatusCode getStatusCode() throws IOException {
            return response.getStatusCode();
        }

        @Override
        public String getStatusText() throws IOException {
            return response.getStatusText();
        }

        @Override
        public void close() {
            response.close();
        }
    }
}