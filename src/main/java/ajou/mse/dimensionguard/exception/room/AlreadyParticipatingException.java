package ajou.mse.dimensionguard.exception.room;

import ajou.mse.dimensionguard.exception.common.ConflictException;

public class AlreadyParticipatingException extends ConflictException {

    public AlreadyParticipatingException(Long participatingRoomId) {
        super("참여중인 방의 id=" + participatingRoomId);
    }
}
