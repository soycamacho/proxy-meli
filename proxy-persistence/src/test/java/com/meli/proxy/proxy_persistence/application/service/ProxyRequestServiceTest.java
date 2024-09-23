package com.meli.proxy.proxy_persistence.application.service;

import com.meli.proxy.proxy_persistence.application.ProxyRequestService;
import com.meli.proxy.proxy_persistence.domain.ProxyRequest;
import com.meli.proxy.proxy_persistence.infrastructure.repository.ProxyRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProxyRequestServiceTest {

    @Mock
    private ProxyRequestRepository repository;

    @InjectMocks
    private ProxyRequestService service;

    private ProxyRequest proxyRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        proxyRequest = new ProxyRequest();
        proxyRequest.setIp("192.168.1.1");
        proxyRequest.setPath("/api/test");
        proxyRequest.setMethod("GET");
        proxyRequest.setDate(new Date());
        proxyRequest.setDuration(100L);
        proxyRequest.setStatus(200);
    }

    @Test
    void create_ShouldSaveProxyRequest() {
        when(repository.save(any(ProxyRequest.class))).thenReturn(proxyRequest);

        ProxyRequest createdRequest = service.create(proxyRequest);
        assertNotNull(createdRequest);
        assertEquals("192.168.1.1", createdRequest.getIp());
        verify(repository, times(1)).save(proxyRequest);
    }

    @Test
    void getAll_ShouldReturnListOfProxyRequests() {
        when(repository.findAll()).thenReturn(List.of(proxyRequest));

        List<ProxyRequest> requests = service.getAll();
        assertEquals(1, requests.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void getById_ShouldReturnProxyRequest() {
        when(repository.findById(any(String.class))).thenReturn(Optional.of(proxyRequest));

        Optional<ProxyRequest> foundRequest = service.getById("1");
        assertTrue(foundRequest.isPresent());
        assertEquals("192.168.1.1", foundRequest.get().getIp());
    }

    @Test
    void delete_ShouldCallRepositoryDelete() {
        service.delete("1");
        verify(repository, times(1)).deleteById("1");
    }

}
