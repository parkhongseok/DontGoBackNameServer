package com.dontgoback.name_server.domain.Asset;

import com.dontgoback.name_server.domain.Asset.dto.UpdateAssetRequest;
import com.dontgoback.name_server.domain.Asset.dto.UpdateAssetResponse;
import com.dontgoback.name_server.global.responseDto.ResData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class ApiV1AssetController {
    private final AssetService assetService;

    @PostMapping("/update-asset")
    public ResponseEntity<ResData<UpdateAssetResponse>> updateAsset(@Valid  @RequestBody UpdateAssetRequest request) {
        try {
            UpdateAssetResponse response = assetService.updateAsset(request);
            return ResponseEntity.ok(ResData.of("S", "Success", response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(ResData.of("F", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResData.of("F", "Unexpected error occurred."));
        }
    }
}
