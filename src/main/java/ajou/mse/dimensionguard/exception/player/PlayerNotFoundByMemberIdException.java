package ajou.mse.dimensionguard.exception.player;

import ajou.mse.dimensionguard.exception.common.NotFoundException;

public class PlayerNotFoundByMemberIdException extends NotFoundException {
    public PlayerNotFoundByMemberIdException(Integer memberId) {
        super("memberId=" + memberId);
    }
}
