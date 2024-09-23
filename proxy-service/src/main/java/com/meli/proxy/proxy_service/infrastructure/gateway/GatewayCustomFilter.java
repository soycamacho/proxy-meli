package com.meli.proxy.proxy_service.infrastructure.gateway;

import com.meli.proxy.proxy_service.application.dto.PostRequestDto;
import com.meli.proxy.proxy_service.application.dto.RequestDto;
import com.meli.proxy.proxy_service.application.service.ProxyProcessorService;
import com.meli.proxy.proxy_service.infrastructure.client.ProxyPersistenceFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GatewayCustomFilter extends AbstractGatewayFilterFactory<Object> {
    private static final Logger logger = LoggerFactory.getLogger(GatewayCustomFilter.class);

    @Autowired
    private ProxyProcessorService proxyProcessor;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            RequestDto requestDto = getRequestDto(exchange.getRequest());
            if (!proxyProcessor.validateRequest(requestDto)) {
                exchange.getResponse().setStatusCode(HttpStatus.LOCKED);
                return exchange.getResponse().setComplete();
            }

            long startTime = System.currentTimeMillis();
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                long duration = System.currentTimeMillis() - startTime;
                postRequest(requestDto, exchange, duration);
            }));
        };
    }

    @Async
    protected void postRequest(RequestDto requestDto, ServerWebExchange exchange, long duration) {
        int statusCode = -1;
        if (exchange.getResponse().getStatusCode() != null) {
            statusCode = exchange.getResponse().getStatusCode().value();
        }
        PostRequestDto postRequestDto = new PostRequestDto(requestDto, duration, statusCode);
        logger.info("[" + postRequestDto.getIp() + "] Execution " + postRequestDto.getPath() + " ["+ postRequestDto.getStatus() +"] time: " + postRequestDto.getDuration() + " ms");
    }

    private RequestDto getRequestDto(ServerHttpRequest request) {
        return new RequestDto(
            request.getRemoteAddress().getAddress().getHostAddress(),
            request.getURI().getPath(),
            request.getMethod().name()
        );
    }

}