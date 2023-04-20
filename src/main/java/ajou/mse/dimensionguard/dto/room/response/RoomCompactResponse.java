package ajou.mse.dimensionguard.dto.room.response;

import ajou.mse.dimensionguard.constant.room.RoomStatus;
import ajou.mse.dimensionguard.dto.room.RoomDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RoomCompactResponse {

    @Schema(description = "PK of room", example = "1")
    private Integer id;

    private RoomStatus status;

    private Integer numOfPlayers;

    public static RoomCompactResponse of(Integer id, RoomStatus status, Integer numOfPlayers) {
        return new RoomCompactResponse(id, status, numOfPlayers);
    }

    public static RoomCompactResponse from(RoomDto dto) {
        return of(
                dto.getId(),
                dto.getStatus(),
                dto.getPlayerDtos().size()
        );
    }
}
