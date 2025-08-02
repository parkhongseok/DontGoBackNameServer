package com.dontgoback.name_server.domain.Asset.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateAssetRequest {
    @NotNull
    private long asset;
}
