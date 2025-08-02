package com.dontgoback.name_server.domain.token;

import com.dontgoback.name_server.config.auth.ClientAuthProperties;
import com.dontgoback.name_server.config.jwt.TokenProvider;
import com.dontgoback.name_server.domain.token.dto.TokenRequest;
import com.dontgoback.name_server.domain.token.dto.TokenResponse;
import com.dontgoback.name_server.global.responseDto.ResData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ApiV1TokenController {
    private final TokenProvider tokenProvider;
    private final ClientAuthProperties clientAuthProperties;

    @PostMapping("/token")
    public ResponseEntity<ResData<TokenResponse>> issueToken(@Valid @RequestBody TokenRequest request) {
        String expectedSecret = clientAuthProperties.getClients().get(request.getClientId());

        if (!request.getClientSecret().equals(expectedSecret)) {
            log.warn("Token 요청 실패: clientId={}, 이유=Invalid secret", request.getClientId());
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ResData.of("F",
                                    "invalid_client : Client secret does not match.",
                                    null));
        }
        String token = tokenProvider.generateToken(request.getClientId());

        return ResponseEntity.ok(ResData.of(
                "S",
                "Success",
                new TokenResponse(token)
        ));
    }


}
