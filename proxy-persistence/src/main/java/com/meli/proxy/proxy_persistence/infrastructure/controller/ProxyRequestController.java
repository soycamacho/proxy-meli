package com.meli.proxy.proxy_persistence.infrastructure.controller;


import com.meli.proxy.proxy_persistence.application.ProxyRequestService;
import com.meli.proxy.proxy_persistence.domain.ProxyRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proxy-requests")
public class ProxyRequestController {
    private final ProxyRequestService service;

    public ProxyRequestController(ProxyRequestService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProxyRequest> create(@RequestBody ProxyRequest request) {
        ProxyRequest createdRequest = service.create(request);
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProxyRequest>> getAll() {
        List<ProxyRequest> requests = service.getAll();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProxyRequest> getById(@PathVariable String id) {
        return service.getById(id)
                .map(request -> new ResponseEntity<>(request, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/counts")
    public ResponseEntity<Long> getStats() {
        long count = service.countRequests();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
