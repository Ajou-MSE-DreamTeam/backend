package ajou.mse.dimensionguard.exception.room;

import ajou.mse.dimensionguard.exception.common.NotFoundException;

public class RoomNotFoundByMemberIdException extends NotFoundException {
    public RoomNotFoundByMemberIdException(Long memberId) {
        super("memberId=" + memberId);
    }
}
