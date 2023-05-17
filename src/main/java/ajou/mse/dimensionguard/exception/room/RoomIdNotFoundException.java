package ajou.mse.dimensionguard.exception.room;

import ajou.mse.dimensionguard.exception.common.NotFoundException;

public class RoomIdNotFoundException extends NotFoundException {
    public RoomIdNotFoundException(Long roomId) {
        super("roomId=" + roomId);
    }
}
