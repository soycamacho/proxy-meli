
package com.meli.proxy.proxy_persistence.infrastructure.repository;

import com.meli.proxy.proxy_persistence.domain.ProxyRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProxyRequestRepository extends MongoRepository<ProxyRequest, String> {
    List<ProxyRequest> findByIp(String ip);
    List<ProxyRequest> findByMethod(String method);
}