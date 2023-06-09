package ajou.mse.dimensionguard.controller;

import ajou.mse.dimensionguard.constant.GameResult;
import ajou.mse.dimensionguard.dto.in_game.request.PlayerInGameRequest;
import ajou.mse.dimensionguard.dto.in_game.response.InGameResponse;
import ajou.mse.dimensionguard.dto.room.RoomDto;
import ajou.mse.dimensionguard.security.UserPrincipal;
import ajou.mse.dimensionguard.service.GameSyncService;
import ajou.mse.dimensionguard.service.InGameService;
import ajou.mse.dimensionguard.service.RecordService;
import ajou.mse.dimensionguard.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static ajou.mse.dimensionguard.constant.ConstantUtil.SLEEP_MILLIS_BEFORE_REQ_COUNT_RESET;

@Tag(name = "In-game related")
@RequiredArgsConstructor
@RequestMapping("/api/in-game")
@RestController
public class InGameController {

    private final RoomService roomService;
    private final InGameService inGameService;
    private final GameSyncService gameSyncService;
    private final RecordService recordService;

    @Operation(
            summary = "Update in-game data",
            description = "<p>Update in-game data." +
                    "<p>If you are a boss, deliver the skill you used to the players." +
                    "<p>If you are a hero, update your hp, energy, position, damage dealt, and motion data on server." +
                    "You also deal damage to boss equal to your damage dealt." +
                    "<p>In response, return the newly updated data of all players.",
            security = @SecurityRequirement(name = "access-token")
    )
    @PostMapping
    public InGameResponse update(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody PlayerInGameRequest inGameRequest
    ) throws InterruptedException {
        Long loginMemberId = userPrincipal.getMemberId();

        // 내 정보 update
        inGameService.updateInGameData(loginMemberId, inGameRequest);

        // sync 맞추기
        Long roomId = roomService.findByMemberId(loginMemberId).getId();
        gameSyncService.increaseRequestCount(roomId);
        gameSyncService.waitUntilEveryoneRequest(roomId);
        if (inGameRequest.getIsBoss()) {
            Thread.sleep(SLEEP_MILLIS_BEFORE_REQ_COUNT_RESET);
            gameSyncService.initRequestCount(roomId);
        }

        // 새롭게 갱신된 모든 player들의 정보 조회
        InGameResponse inGameData = inGameService.getInGameData(roomId);

        // 게임이 종료되었는지 확인
        GameResult gameResult = inGameService.isGameEnded(roomId);
        if (gameResult != GameResult.NOT_END) {
            RoomDto roomDto = roomService.findDtoById(roomId);
            // Host만 record를 생성하고 room을 삭제하도록 하여 중복 생성되는 문제 상황을 방지한다.
            if (roomDto.getCreatedBy().equals(userPrincipal.getMemberId())) {
                recordService.record(roomDto, gameResult);
                roomService.deleteWithPlayers(roomId);
            }
        }

        return inGameData;
    }
}
