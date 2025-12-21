package com.nafifsource.hsp.service_performance.batch;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Data
@Configuration
@NoArgsConstructor
@ConfigurationProperties("hsp")
public class HistoricServicePerformanceConfig {

    private Integer threadCount;
    private String url;
    private String usr;
    private String pwd;

    @Bean("webClientRockShore")
    public WebClient webclient() {
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configure -> configure.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                .build();

        ClientHttpConnector httpConnector = new ReactorClientHttpConnector(HttpClient.create(ConnectionProvider.newConnection()));

        return WebClient.builder()
                .clientConnector(httpConnector)
                .exchangeStrategies(exchangeStrategies)
                .baseUrl(url)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder()
                        .encodeToString((usr + ":" + pwd)
                                .getBytes(StandardCharsets.UTF_8)))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(httpConnector)
                .build();
    }
}
