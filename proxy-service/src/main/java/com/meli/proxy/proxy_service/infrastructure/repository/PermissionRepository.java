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

    @Query("{ '$and': [ " +
            " { '$or': [ { 'ip': ?0 }, { 'ip': null } ] }, " +
            " { '$or': [ { 'path': ?1 }, { 'path': null } ] }, " +
            " { '$or': [ { 'method': ?2 }, { 'method': null }, { 'method': { $size: 0 } } ] }, " +
            " { '$or': [ { 'after': { $lte: ?3 } }, { 'after': null } ] }, " +
            " { '$or': [ { 'before': { $gte: ?3 } }, { 'before': null } ] }, " +
            " { '$or': [ { 'limit': { $gt: 0 } }, { 'limit': null } ] } " +
            "] }")
    List<Permission> findByIpAndPath(String ip, String path, String method, Date date);
}
