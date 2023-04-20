package ajou.mse.dimensionguard.dto.room.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class WaitingRoomListResponse {

    List<RoomCompactResponse> roomList;

    public static WaitingRoomListResponse of(List<RoomCompactResponse> roomList) {
        return new WaitingRoomListResponse(roomList);
    }
}
