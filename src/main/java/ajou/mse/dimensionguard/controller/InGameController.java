package ajou.mse.dimensionguard.controller;

import ajou.mse.dimensionguard.constant.ConstantUtil;
import ajou.mse.dimensionguard.dto.in_game.request.PlayerInGameRequest;
import ajou.mse.dimensionguard.dto.in_game.response.InGameResponse;
import ajou.mse.dimensionguard.security.UserPrincipal;
import ajou.mse.dimensionguard.service.GameSyncService;
import ajou.mse.dimensionguard.service.InGameService;
import ajou.mse.dimensionguard.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ajou.mse.dimensionguard.constant.ConstantUtil.SLEEP_MILLIS_BEFORE_REQ_COUNT_RESET;

@Tag(name = "인게임 관련")
@RequiredArgsConstructor
@RequestMapping("/api/in-game")
@RestController
public class InGameController {

    private final RoomService roomService;
    private final InGameService inGameService;
    private final GameSyncService gameSyncService;

    @Operation(
            summary = "인게임 데이터 갱신",
            description = "",
            security = @SecurityRequirement(name = "access-token")
    )
    @PostMapping
    public InGameResponse update(
            @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody PlayerInGameRequest inGameRequest
    ) throws InterruptedException {
        Integer loginMemberId = userPrincipal.getMemberId();

        // 내 정보 update
        inGameService.updateInGameData(loginMemberId, inGameRequest);

        // sync 맞추기
        Integer roomId = roomService.findEntityByMemberId(loginMemberId).getId();
        gameSyncService.increaseRequestCount(roomId);
        gameSyncService.waitUntilEveryoneRequest(roomId);
        if (inGameRequest.getIsBoss()) {
            Thread.sleep(SLEEP_MILLIS_BEFORE_REQ_COUNT_RESET);
            gameSyncService.initRequestCount(roomId);
        }

        // 새롭게 갱신된 모든 player들의 정보 조회
        return inGameService.getInGameData(roomId);
    }
}
