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
    private Integer id;

    private RoomStatus status;

    @Schema(description = "호스트(방장)의 닉네임", example = "홍길동")
    private String hostNickname;

    @Schema(description = "룸에 참가중인 인원수", example = "3")
    private Integer numOfPlayers;

    public static RoomCompactResponse from(RoomDto dto, String hostNickname) {
        return new RoomCompactResponse(
                dto.getId(),
                dto.getStatus(),
                hostNickname,
                dto.getPlayerDtos().size()
        );
    }
}
