package ajou.mse.dimensionguard.exception.member;

import ajou.mse.dimensionguard.exception.common.ConflictException;

public class MemberNameDuplicateException extends ConflictException {

    public MemberNameDuplicateException(String name) {
        super("name=" + name);
    }
}
