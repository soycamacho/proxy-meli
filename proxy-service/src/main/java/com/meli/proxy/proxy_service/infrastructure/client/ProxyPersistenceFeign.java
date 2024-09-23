package com.meli.proxy.proxy_service.infrastructure.client;

import com.meli.proxy.proxy_service.application.dto.PostRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "proxy-persistence", url = "${feign.proxy-persistence.url}")
public interface ProxyPersistenceFeign {
    @PostMapping("/requests")
    void saveRequest(@RequestBody PostRequestDto postRequestDto);
}
