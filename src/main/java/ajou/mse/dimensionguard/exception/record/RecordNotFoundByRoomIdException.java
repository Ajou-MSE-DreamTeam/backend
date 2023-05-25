package ajou.mse.dimensionguard.exception.record;

import ajou.mse.dimensionguard.exception.common.NotFoundException;

public class RecordNotFoundByRoomIdException extends NotFoundException {
    public RecordNotFoundByRoomIdException(Long roomId) {
        super("roomId=" + roomId);
    }
}
