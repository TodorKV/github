package com.atipera.github.external.config;

import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.httpclient.ApacheHttpClient;

@Configuration
@Import(FeignClientsConfiguration.class)
public class GithubClientConfig {

    public static final String VND_GITHUB_JSON = "application/vnd.github+json";
    public static final String X_GITHUB_API_VERSION = "2022-11-28";

    @Bean
    public Feign.Builder githubClientFeignBuilder(
            Encoder encoder, Decoder decoder, ObjectMapper ojbectMapper) {
        return Feign.builder()
                .client(new ApacheHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .contract(new SpringMvcContract());
    }
}
