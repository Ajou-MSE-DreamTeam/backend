package ajou.mse.dimensionguard.exception.room;

import ajou.mse.dimensionguard.exception.common.NotFoundException;

public class RoomNotFoundByMemberIdException extends NotFoundException {
    public RoomNotFoundByMemberIdException(Integer memberId) {
        super("memberId=" + memberId);
    }
}
