package ajou.mse.dimensionguard.constant;

import ajou.mse.dimensionguard.dto.redis.InGameRequestStatus;
import ajou.mse.dimensionguard.dto.redis.RedisSkillInfo;
import ajou.mse.dimensionguard.service.GameSyncService;

public class ConstantUtil {

    /**
     * <p>Sync 관련
     * <p>{@link GameSyncService}
     */
    // Wait until everyone is ready. 최대 15sec.
    public static final int WAITING_COUNT_FOR_READY = 30;
    public static final int SLEEP_MILLIS_FOR_READY = 500;

    // Wait until everyone request. 최대 0.7sec.
    public static final int WAITING_COUNT_FOR_REQUEST = 24;
    public static final int SLEEP_MILLIS_FOR_REQUEST = 25;

    public static final int SLEEP_MILLIS_BEFORE_REQ_COUNT_RESET = 50;

    /**
     * <p>Redis time to live
     * <p>{@link InGameRequestStatus}, {@link RedisSkillInfo}
     */
    public static final int IN_GAME_REQUEST_STATUS_TIMEOUT = 2;
    public static final int SKILL_INFO_TIMEOUT = 2;
}
