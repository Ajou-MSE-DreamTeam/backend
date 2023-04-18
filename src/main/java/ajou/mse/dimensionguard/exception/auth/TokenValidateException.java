package ajou.mse.dimensionguard.exception.auth;

import ajou.mse.dimensionguard.exception.common.UnauthorizedException;

public class TokenValidateException extends UnauthorizedException {

    public TokenValidateException() {
        super();
    }

    public TokenValidateException(String optionalMessage) {
        super(optionalMessage);
    }

    public TokenValidateException(Throwable cause) {
        super(cause);
    }
}
