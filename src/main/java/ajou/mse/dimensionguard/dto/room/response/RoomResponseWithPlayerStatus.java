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
public class RoomResponseWithPlayerStatus {

    @Schema(description = "PK of room", example = "1")
    private Long id;

    private RoomStatus status;

    @Schema(description = "Id of selected map")
    private Integer mapId;

    private List<PlayerCompactResponseWithReadyStatus> players;

    public static RoomResponseWithPlayerStatus from(RoomDto dto) {
        return new RoomResponseWithPlayerStatus(
                dto.getId(),
                dto.getStatus(),
                dto.getMapId(),
                dto.getPlayerDtos().stream()
                        .map(PlayerCompactResponseWithReadyStatus::from)
                        .toList()
        );
    }
}
