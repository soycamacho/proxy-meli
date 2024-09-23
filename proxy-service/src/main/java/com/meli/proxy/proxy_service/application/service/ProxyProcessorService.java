package com.meli.proxy.proxy_service.application.service;

import com.meli.proxy.proxy_service.application.dto.RequestDto;
import org.springframework.stereotype.Service;

@Service
public class ProxyProcessorService {
    public Boolean validateRequest(RequestDto requestDto) {
        return true;
    }
}
