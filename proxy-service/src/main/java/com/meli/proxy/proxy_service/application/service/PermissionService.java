package com.meli.proxy.proxy_service.application.service;

import com.meli.proxy.proxy_service.application.dto.PermissionDto;
import com.meli.proxy.proxy_service.domain.Permission;
import com.meli.proxy.proxy_service.infrastructure.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    private final PermissionRepository repository;

    @Autowired
    public PermissionService(PermissionRepository repository) {
        this.repository = repository;
    }

    private Permission convertToEntity(PermissionDto dto) {
        return new Permission(
            dto.getIp(),
            dto.getPath(),
            dto.getMethod(),
            dto.getAfter(),
            dto.getBefore(),
            dto.getLimit()
        );
    }

    private PermissionDto convertToDto(Permission permission) {
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

    public PermissionDto createPermission(PermissionDto dto) {
        Permission permission = convertToEntity(dto);
        Permission savedPermission = repository.save(permission);
        return convertToDto(savedPermission);
    }

    public List<PermissionDto> getAllPermissions() {
        return repository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public PermissionDto getPermissionById(String id) {
        return repository.findById(id)
                .map(this::convertToDto)
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
                    return convertToDto(updatedPermission);
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
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<PermissionDto> validation(String ip, String path, String method, Date date) {
        System.out.println(date);
        return repository.findByIpAndPathAndMethodAndDateWithLimit(ip, path, method, date).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public long countPermissions() {
        return repository.count();
    }
}
