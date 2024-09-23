package com.meli.proxy.proxy_service.application.dto;

import java.util.Date;

public class RequestDto {
    private String ip;
    private String path;
    private String method;
    private Date date;

    public RequestDto(RequestDto requestDto) {
        this.ip = requestDto.getIp();
        this.path = requestDto.getPath();
        this.method = requestDto.getMethod();
        date = requestDto.getDate();
    }

    public RequestDto(String ip, String path, String method) {
        this.ip = ip;
        this.path = path;
        this.method = method;
        date = new Date();
    }

    public String getIp() {
        return ip;
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    public Date getDate() {
        return date;
    }
}
