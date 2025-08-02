package com.dontgoback.name_server.domain.Asset;

import com.dontgoback.name_server.domain.Asset.dto.UpdateAssetRequest;
import com.dontgoback.name_server.domain.Asset.dto.UpdateAssetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AssetService {

    public UpdateAssetResponse updateAsset(UpdateAssetRequest request) {
        long asset = request.getAsset();

        Random random = new Random();
        // 난수를 사용하여 -5000 ~ 5000 사이의 값을 생성 > 추후 api로 입력받을 예정
        int maxAmount = 10;
        int minAmount = -10;
        int percent = random.nextInt(maxAmount - minAmount + 1) + minAmount;
        return new UpdateAssetResponse(asset*percent);
    }
}
