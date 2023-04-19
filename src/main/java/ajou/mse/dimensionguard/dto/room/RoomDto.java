package ajou.mse.dimensionguard.dto.room;

import ajou.mse.dimensionguard.constant.room.RoomStatus;
import ajou.mse.dimensionguard.domain.Room;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RoomDto {

    private Integer id;
    private RoomStatus status;

    public static RoomDto of(Integer id, RoomStatus status) {
        return new RoomDto(id, status);
    }

    public static RoomDto from(Room room) {
        return of(room.getId(), room.getStatus());
    }
}
