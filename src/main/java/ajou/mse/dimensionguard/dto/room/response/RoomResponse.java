package ajou.mse.dimensionguard.dto.room.response;

import ajou.mse.dimensionguard.constant.room.RoomStatus;
import ajou.mse.dimensionguard.dto.room.RoomDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RoomResponse {


    @Schema(description = "PK of room", example = "1")
    private Integer id;

    private RoomStatus status;

    public static RoomResponse of(Integer id, RoomStatus status) {
        return new RoomResponse(id, status);
    }

    public static RoomResponse from(RoomDto dto) {
        return of(dto.getId(), dto.getStatus());
    }
}
