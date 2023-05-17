package ajou.mse.dimensionguard.exception.player;

import ajou.mse.dimensionguard.exception.common.NotFoundException;

public class PlayerNotFoundByMemberAndRoomException extends NotFoundException {
    
    public PlayerNotFoundByMemberAndRoomException(Long memberId, Long roomId) {
        super("memberId=" + memberId + ", roomId=" + roomId);
    }
}
