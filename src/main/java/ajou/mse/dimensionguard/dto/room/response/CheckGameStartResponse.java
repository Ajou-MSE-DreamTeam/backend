package ajou.mse.dimensionguard.dto.room.response;

import ajou.mse.dimensionguard.constant.room.RoomStatus;
import ajou.mse.dimensionguard.dto.player.response.PlayerCompactResponseWithReadyStatus;
import ajou.mse.dimensionguard.dto.room.RoomDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CheckGameStartResponse {

    @Schema(description = "게임 시작 여부", example = "false")
    private Boolean isStarted;

    private List<PlayerCompactResponseWithReadyStatus> players;

    public static CheckGameStartResponse from(RoomDto roomDto) {
        return new CheckGameStartResponse(
                roomDto.getStatus() != RoomStatus.READY,
                roomDto.getPlayerDtos().stream()
                        .map(PlayerCompactResponseWithReadyStatus::from)
                        .toList()
        );
    }
}
