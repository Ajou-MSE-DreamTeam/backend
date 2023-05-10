package ajou.mse.dimensionguard.dto.room.response;

import ajou.mse.dimensionguard.constant.room.RoomStatus;
import ajou.mse.dimensionguard.dto.player.response.PlayerCompactResponse;
import ajou.mse.dimensionguard.dto.room.RoomDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class RoomResponse {


    @Schema(description = "PK of room", example = "1")
    private Integer id;

    private RoomStatus status;

    @Schema(description = "<p>When the game started(ISO 8601 format)" +
            "<p><code>null</code> if the game has not started.",
            example = "2023-05-10T17:48:54.393181")
    private LocalDateTime gameStartedAt;

    @Schema(description = "Information about the players in the room")
    private List<PlayerCompactResponse> players;

    public static RoomResponse from(RoomDto dto) {
        return new RoomResponse(
                dto.getId(),
                dto.getStatus(),
                dto.getGameStartedAt(),
                dto.getPlayerDtos().stream()
                        .map(PlayerCompactResponse::from)
                        .toList()
        );
    }
}
