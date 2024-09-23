package com.meli.proxy.proxy_persistence.infrastructure.controller;

import com.meli.proxy.proxy_persistence.application.ProxyRequestService;
import com.meli.proxy.proxy_persistence.domain.ProxyRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProxyRequestControllerTest {

    @Mock
    private ProxyRequestService service;

    @InjectMocks
    private ProxyRequestController controller;

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
    void create_ShouldReturnCreatedProxyRequest() {
        when(service.create(any())).thenReturn(proxyRequest);

        ResponseEntity<ProxyRequest> response = controller.create(proxyRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(proxyRequest, response.getBody());
    }

    @Test
    void getAll_ShouldReturnListOfProxyRequests() {
        when(service.getAll()).thenReturn(List.of(proxyRequest));

        ResponseEntity<List<ProxyRequest>> response = controller.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getById_ShouldReturnProxyRequest() {
        when(service.getById(any())).thenReturn(Optional.of(proxyRequest));

        ResponseEntity<ProxyRequest> response = controller.getById("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(proxyRequest, response.getBody());
    }

    @Test
    void getById_ShouldReturnNotFound() {
        when(service.getById(any())).thenReturn(Optional.empty());

        ResponseEntity<ProxyRequest> response = controller.getById("1");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void delete_ShouldReturnNoContent() {
        ResponseEntity<Void> response = controller.delete("1");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).delete("1");
    }

    @Test
    void getStats_ShouldReturnCountOfRequests() {
        when(service.countRequests()).thenReturn(10L);

        ResponseEntity<Long> response = controller.getStats();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10L, response.getBody());
    }
}
