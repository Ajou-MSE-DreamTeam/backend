package ajou.mse.dimensionguard.exception.player;

import ajou.mse.dimensionguard.exception.common.NotFoundException;

public class PlayerByMemberIdNotFoundException extends NotFoundException {
    public PlayerByMemberIdNotFoundException(Integer memberId) {
        super("memberId=" + memberId);
    }
}
