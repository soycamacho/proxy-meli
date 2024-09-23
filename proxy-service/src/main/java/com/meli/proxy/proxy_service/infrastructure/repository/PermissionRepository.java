package com.meli.proxy.proxy_service.infrastructure.repository;

import com.meli.proxy.proxy_service.domain.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PermissionRepository extends MongoRepository<Permission, String> {

    List<Permission> findByIp(String ip);
}
