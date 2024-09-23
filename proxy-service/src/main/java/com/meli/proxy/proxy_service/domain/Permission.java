package com.meli.proxy.proxy_service.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Document(collection = "permissions")
public class Permission {

    @Id
    private String id;
    private String ip;
    private String path;
    private Set<String> method;
    private Date after;
    private Date before;
    private Long limit;
    private Long count;

    public Permission() {}

    public Permission(String ip, String path, Set<String> method, Date after, Date before, Long limit) {
        this.ip = ip;
        this.path = path;
        this.method = method;
        this.after = after;
        this.before = before;
        this.limit = limit;
        this.count = limit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getAfter() {
        return after;
    }

    public void setAfter(Date after) {
        this.after = after;
    }

    public Date getBefore() {
        return before;
    }

    public void setBefore(Date before) {
        this.before = before;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Set<String> getMethod() {
        return method;
    }

    public void setMethod(Set<String> method) {
        this.method = method;
    }
}
