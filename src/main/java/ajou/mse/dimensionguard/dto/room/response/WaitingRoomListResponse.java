package ajou.mse.dimensionguard.dto.room.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class WaitingRoomListResponse {

    List<RoomCompactResponse> roomList;
}
