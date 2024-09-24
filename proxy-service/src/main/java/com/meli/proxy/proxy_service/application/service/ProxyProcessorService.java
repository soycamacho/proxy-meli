package com.meli.proxy.proxy_service.application.service;

import com.meli.proxy.proxy_service.application.dto.RequestDto;
import com.meli.proxy.proxy_service.domain.Permission;
import com.meli.proxy.proxy_service.infrastructure.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProxyProcessorService {
    private final PermissionRepository repository;

    @Autowired
    public ProxyProcessorService(PermissionRepository repository) {
        this.repository = repository;
    }

    public Boolean validateRequest(RequestDto requestDto) {
        Long updated = repository.updateDecrementCounterByIpAndPathAndMethodAndDateWithLimit(
                requestDto.getIp(),
                requestDto.getPath(),
                requestDto.getMethod(),
                requestDto.getDate()
        );

        return updated > 0;
    }
}
