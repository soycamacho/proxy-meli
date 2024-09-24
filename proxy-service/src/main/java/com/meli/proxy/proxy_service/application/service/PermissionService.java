package com.meli.proxy.proxy_service.application.service;

import com.meli.proxy.proxy_service.application.dto.PermissionDto;
import com.meli.proxy.proxy_service.domain.Permission;
import com.meli.proxy.proxy_service.infrastructure.mapper.ProxyRequestMapper;
import com.meli.proxy.proxy_service.infrastructure.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    private final PermissionRepository repository;
    private final ProxyRequestMapper mapper;

    @Autowired
    public PermissionService(PermissionRepository repository, ProxyRequestMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    public PermissionDto createPermission(PermissionDto dto) {
        Permission permission = mapper.toEntity(dto);
        Permission savedPermission = repository.save(permission);
        return mapper.toDto(savedPermission);
    }

    public List<PermissionDto> getAllPermissions() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public PermissionDto getPermissionById(String id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElse(null);
    }

    public PermissionDto updatePermission(String id, PermissionDto dto) {
        return repository.findById(id)
                .map(existingPermission -> {
                    existingPermission.setIp(dto.getIp());
                    existingPermission.setPath(dto.getPath());
                    existingPermission.setMethod(dto.getMethod());
                    existingPermission.setAfter(dto.getAfter());
                    existingPermission.setBefore(dto.getBefore());
                    existingPermission.setLimit(dto.getLimit());
                    Permission updatedPermission = repository.save(existingPermission);
                    return mapper.toDto(updatedPermission);
                })
                .orElse(null);
    }

    public boolean deletePermission(String id) {
        return repository.findById(id)
                .map(permission -> {
                    repository.delete(permission);
                    return true;
                })
                .orElse(false);
    }

    public List<PermissionDto> getPermissionsByIp(String ip) {
        return repository.findByIp(ip).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PermissionDto> validation(String ip, String path, String method, Date date) {
        return repository.findByIpAndPathAndMethodAndDateWithLimit(ip, path, method, date).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public long countPermissions() {
        return repository.count();
    }
}
