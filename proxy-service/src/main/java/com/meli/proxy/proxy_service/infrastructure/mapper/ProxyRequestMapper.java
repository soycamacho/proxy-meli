package com.meli.proxy.proxy_service.infrastructure.mapper;

import com.meli.proxy.proxy_service.application.dto.PermissionDto;
import com.meli.proxy.proxy_service.domain.Permission;
import org.springframework.stereotype.Component;

@Component
public class ProxyRequestMapper {
    public PermissionDto toDto(Permission permission) {
        return new PermissionDto(
                permission.getId(),
                permission.getIp(),
                permission.getPath(),
                permission.getMethod(),
                permission.getAfter(),
                permission.getBefore(),
                permission.getLimit(),
                permission.getCount()
        );
    }

    public Permission toEntity(PermissionDto dto) {
        return new Permission(
                dto.getIp(),
                dto.getPath(),
                dto.getMethod(),
                dto.getAfter(),
                dto.getBefore(),
                dto.getLimit()
        );
    }
}
