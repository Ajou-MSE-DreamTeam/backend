package ajou.mse.dimensionguard.dto.room.response;

import ajou.mse.dimensionguard.constant.room.RoomStatus;
import ajou.mse.dimensionguard.dto.room.RoomDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RoomCompactResponse {

    @Schema(description = "PK of room", example = "1")
    private Long id;

    private RoomStatus status;

    @Schema(description = "Nickname of host", example = "Woogie")
    private String hostName;

    @Schema(description = "Number of players in the room", example = "3")
    private Integer numOfPlayers;

    public static RoomCompactResponse from(RoomDto dto, String hostName) {
        return new RoomCompactResponse(
                dto.getId(),
                dto.getStatus(),
                hostName,
                dto.getPlayerDtos().size()
        );
    }
}
