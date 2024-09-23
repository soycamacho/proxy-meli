package com.meli.proxy.proxy_service.infrastructure.controller;

import com.meli.proxy.proxy_service.application.dto.PermissionDto;
import com.meli.proxy.proxy_service.application.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    private final PermissionService service;

    @Autowired
    public PermissionController(PermissionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PermissionDto> createPermission(@RequestBody PermissionDto dto) {
        PermissionDto createdPermission = service.createPermission(dto);
        return new ResponseEntity<>(createdPermission, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PermissionDto>> getAllPermissions() {
        List<PermissionDto> permissions = service.getAllPermissions();
        return new ResponseEntity<>(permissions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionDto> getPermissionById(@PathVariable String id) {
        PermissionDto permission = service.getPermissionById(id);
        if (permission != null) {
            return new ResponseEntity<>(permission, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermissionDto> updatePermission(@PathVariable String id, @RequestBody PermissionDto dto) {
        PermissionDto updatedPermission = service.updatePermission(id, dto);
        if (updatedPermission != null) {
            return new ResponseEntity<>(updatedPermission, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable String id) {
        boolean deleted = service.deletePermission(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ip/{ip}")
    public ResponseEntity<List<PermissionDto>> getPermissionsByIp(@PathVariable String ip) {
        List<PermissionDto> permissions = service.getPermissionsByIp(ip);
        return new ResponseEntity<>(permissions, HttpStatus.OK);
    }

    @GetMapping("/validations")
    public ResponseEntity<List<PermissionDto>> getValidation(
         @RequestParam(required = false) String ip,
         @RequestParam(required = false) String path,
         @RequestParam(required = false) String method,
         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date date
    ) {
        List<PermissionDto> permissions = service.validation(ip, path, method, date != null ? date : new Date());
        return new ResponseEntity<>(permissions, HttpStatus.OK);
    }

    @GetMapping("/stats/count")
    public ResponseEntity<Long> countPermissions() {
        long count = service.countPermissions();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
