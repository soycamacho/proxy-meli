package com.meli.proxy.proxy_persistence.application;

import com.meli.proxy.proxy_persistence.domain.ProxyRequest;
import com.meli.proxy.proxy_persistence.infrastructure.repository.ProxyRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProxyRequestService {
    private final ProxyRequestRepository repository;

    public ProxyRequestService(ProxyRequestRepository repository) {
        this.repository = repository;
    }

    public ProxyRequest create(ProxyRequest request) {
        return repository.save(request);
    }

    public List<ProxyRequest> getAll() {
        return repository.findAll();
    }

    public Optional<ProxyRequest> getById(String id) {
        return repository.findById(id);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public long countRequests() {
        return repository.count();
    }
}
