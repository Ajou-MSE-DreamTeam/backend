package ajou.mse.dimensionguard.exception.player;

import ajou.mse.dimensionguard.exception.common.NotFoundException;

public class PlayerByMemberAndRoomNotFoundException extends NotFoundException {
    
    public PlayerByMemberAndRoomNotFoundException(Integer memberId, Integer roomId) {
        super("memberId=" + memberId + ", roomId=" + roomId);
    }
}
