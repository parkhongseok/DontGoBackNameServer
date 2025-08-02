package com.dontgoback.name_server.domain.token.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PublicKeyResponse {
    private String publicKey;
}
