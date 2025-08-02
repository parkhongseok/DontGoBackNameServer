package com.dontgoback.name_server.config.auth;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "auth.clients")
@Getter
public class ClientAuthProperties {
    private Map<String, String> clients; // clientId -> clientSecret
}