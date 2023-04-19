package ajou.mse.dimensionguard.exception.room;

import ajou.mse.dimensionguard.exception.common.InternalServerException;

public class GameStartException extends InternalServerException {

    public GameStartException() {
        super();
    }
    public GameStartException(Throwable cause) {
        super(cause);
    }
}
