package ajou.mse.dimensionguard.exception.player;

import ajou.mse.dimensionguard.exception.common.NotFoundException;

public class PlayerNotFoundByMemberAndRoomException extends NotFoundException {
    
    public PlayerNotFoundByMemberAndRoomException(Integer memberId, Integer roomId) {
        super("memberId=" + memberId + ", roomId=" + roomId);
    }
}
