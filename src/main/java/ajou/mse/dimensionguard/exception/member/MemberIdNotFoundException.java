package ajou.mse.dimensionguard.exception.member;

import ajou.mse.dimensionguard.exception.common.NotFoundException;

public class MemberIdNotFoundException extends NotFoundException {

    public MemberIdNotFoundException(int memberId) {
        super("memberId=" + memberId);
    }
}
