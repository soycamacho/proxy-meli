package com.meli.proxy.proxy_service.application.dto;

import java.util.Date;
import java.util.Set;

public class PermissionDto {
    private String id;
    private String ip;
    private String path;
    private Set<String> method;
    private Date after;
    private Date before;
    private Long limit;
    private Long count;

    public PermissionDto(String id, String ip, String path, Set<String> method, Date after, Date before, Long limit, Long count) {
        this.id = id;
        this.ip = ip;
        this.path = path;
        this.method = method;
        this.after = after;
        this.before = before;
        this.limit = limit;
        this.count = count;
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

    public Set<String> getMethod() {
        return method;
    }

    public void setMethod(Set<String> method) {
        this.method = method;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
