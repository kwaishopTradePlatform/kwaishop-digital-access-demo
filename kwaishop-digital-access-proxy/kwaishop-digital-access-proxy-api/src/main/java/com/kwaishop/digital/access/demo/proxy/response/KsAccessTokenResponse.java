package com.kwaishop.digital.access.demo.proxy.response;

import java.util.List;

import lombok.Data;

@Data
public class KsAccessTokenResponse {
    private int result;
    private String error;
    private String error_msg;
    private String access_token;
    private String refresh_token;
    private String open_id;
    private long expires_in;
    private long refresh_token_expires_in;
    private List<String> scopes;

}