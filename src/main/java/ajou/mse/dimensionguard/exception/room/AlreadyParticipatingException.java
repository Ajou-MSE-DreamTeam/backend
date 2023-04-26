package ajou.mse.dimensionguard.exception.room;

import ajou.mse.dimensionguard.exception.common.ConflictException;

public class AlreadyParticipatingException extends ConflictException {

    public AlreadyParticipatingException(Integer participatingRoomId) {
        super("참여중인 방의 id=" + participatingRoomId);
    }
}
