package com.hoyoung.traffic.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TrafficLog {
    String statusCode;
    String remoteAddr;
    String httpMethod;
    String serverResp;

    public String print() {
        return String.format("Request Http Method: %s, user ip: %s, statusCode: %s, Server Response: %s",
                statusCode, remoteAddr, httpMethod, serverResp);
    }
}
