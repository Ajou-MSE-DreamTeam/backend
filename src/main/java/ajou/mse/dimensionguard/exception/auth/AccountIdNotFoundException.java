package ajou.mse.dimensionguard.exception.auth;

import ajou.mse.dimensionguard.exception.common.NotFoundException;

public class AccountIdNotFoundException extends NotFoundException {

    public AccountIdNotFoundException(String accountId) {
        super("accountId=" + accountId);
    }
}
