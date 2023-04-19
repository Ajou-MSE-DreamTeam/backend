package ajou.mse.dimensionguard.dto.room.response;

import ajou.mse.dimensionguard.constant.room.RoomStatus;
import ajou.mse.dimensionguard.dto.player.response.PlayerCompactResponse;
import ajou.mse.dimensionguard.dto.room.RoomDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RoomResponse {


    @Schema(description = "PK of room", example = "1")
    private Integer id;

    private RoomStatus status;

    private List<PlayerCompactResponse> players;

    public static RoomResponse of(Integer id, RoomStatus status, List<PlayerCompactResponse> players) {
        return new RoomResponse(id, status, players);
    }

    public static RoomResponse from(RoomDto dto) {
        return of(
                dto.getId(),
                dto.getStatus(),
                dto.getPlayerDtos().stream()
                        .map(PlayerCompactResponse::from)
                        .toList()
        );
    }
}
