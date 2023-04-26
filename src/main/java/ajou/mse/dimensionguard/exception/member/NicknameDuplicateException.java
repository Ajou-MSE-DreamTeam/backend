package ajou.mse.dimensionguard.exception.member;

import ajou.mse.dimensionguard.exception.common.ConflictException;

public class NicknameDuplicateException extends ConflictException {

    public NicknameDuplicateException(String nickname) {
        super("nickname=" + nickname);
    }
}
