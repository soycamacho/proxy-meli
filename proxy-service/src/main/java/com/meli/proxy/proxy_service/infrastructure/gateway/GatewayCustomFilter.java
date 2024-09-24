package com.meli.proxy.proxy_service.infrastructure.gateway;

import com.meli.proxy.proxy_service.application.dto.PostRequestDto;
import com.meli.proxy.proxy_service.application.dto.RequestDto;
import com.meli.proxy.proxy_service.application.service.ProxyProcessorService;
import com.meli.proxy.proxy_service.infrastructure.client.ProxyPersistenceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Component
public class GatewayCustomFilter extends AbstractGatewayFilterFactory<Object> {
    private static final Logger logger = LoggerFactory.getLogger(GatewayCustomFilter.class);

    @Value("${application.proxy.base-path}")
    private String proxyBasePath;

    @Value("${application.proxy.rejection-status}")
    private int proxyRejectionStatus;

    private final ProxyPersistenceClient proxyPersistenceClient;

    private final ProxyProcessorService proxyProcessor;

    @Autowired
    public GatewayCustomFilter(@Lazy ProxyPersistenceClient proxyPersistenceClient, ProxyProcessorService proxyProcessor) {
        this.proxyPersistenceClient = proxyPersistenceClient;
        this.proxyProcessor = proxyProcessor; }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            RequestDto requestDto = getRequestDto(exchange.getRequest());
            if (!proxyProcessor.validateRequest(requestDto)) {
                exchange.getResponse().setStatusCode(HttpStatus.valueOf(proxyRejectionStatus));
                return exchange.getResponse().setComplete();
            }

            long startTime = System.currentTimeMillis();
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                postRequest(requestDto, exchange, startTime);
            }));
        };
    }

    protected void postRequest(RequestDto requestDto, ServerWebExchange exchange, long startTime) {
        long duration = System.currentTimeMillis() - startTime;
        CompletableFuture.supplyAsync(() -> {
            int statusCode = -1;
            if (exchange.getResponse().getStatusCode() != null) {
                statusCode = exchange.getResponse().getStatusCode().value();
            }
            PostRequestDto postRequestDto = new PostRequestDto(requestDto, duration, statusCode);
            proxyPersistenceClient.saveRequest(postRequestDto);
            return postRequestDto;
        }).thenAccept(postRequestDto -> {
            logger.info("[" + postRequestDto.getIp() + "] Execution " + postRequestDto.getPath() + " ["+ postRequestDto.getStatus() +"] time: " + postRequestDto.getDuration() + " ms");
        });
    }

    private RequestDto getRequestDto(ServerHttpRequest request) {
        return new RequestDto(
            request.getRemoteAddress().getAddress().getHostAddress(),
            request.getURI().getPath().toString().replaceFirst(proxyBasePath, ""),
            request.getMethod().name()
        );
    }

}