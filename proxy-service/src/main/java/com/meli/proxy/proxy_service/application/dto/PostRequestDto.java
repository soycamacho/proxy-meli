package com.meli.proxy.proxy_service.application.dto;

public class PostRequestDto extends RequestDto {
    private Long duration;
    private int status;
    public PostRequestDto(RequestDto requestDto, Long duration, int status) {
        super(requestDto);
        this.duration = duration;
        this.status = status;
    }

    public Long getDuration() {
        return duration;
    }

    public int getStatus() {
        return status;
    }
}
