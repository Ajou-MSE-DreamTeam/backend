package ajou.mse.dimensionguard.constant;

import ajou.mse.dimensionguard.dto.redis.InGameRequestStatus;
import ajou.mse.dimensionguard.dto.redis.Skill;
import ajou.mse.dimensionguard.service.GameSyncService;

public class ConstantUtil {

    private static final int MINUTE = 60;
    private static final int HOUR = 60 * MINUTE;

    /**
     * Player 관련
     */
    public static final int BOSS_DEFAULT_HP = 100;
    public static final int BOSS_DEFAULT_ENERGY = 100;
    public static final int HERO_DEFAULT_HP = 100;
    public static final int HERO_DEFAULT_ENERGY = 100;

    /**
     * 보스 스킬 특정 상태값
     */
    public static final int SKILL_UNUSED = 0;
    public static final int SKILL_USING = -1;

    /**
     * Game 관련
     */
    public static final int NUM_OF_MAPS = 3;

    /**
     * <p>Sync 관련
     * <p>{@link GameSyncService}
     */
    // Wait until everyone is ready. 최대 15sec.
    public static final int WAITING_COUNT_FOR_READY = 30;
    public static final int SLEEP_MILLIS_FOR_READY = 500;

    // Wait until everyone request. 최대 0.5sec.
    public static final int WAITING_COUNT_FOR_REQUEST = 25;
    public static final int SLEEP_MILLIS_FOR_REQUEST = 20;

    public static final int SLEEP_MILLIS_BEFORE_REQ_COUNT_RESET = 50;   // Must be greater than SLEEP_MILLIS_FOR_REQUEST

    /**
     * <p>Redis time to live
     * <p>{@link InGameRequestStatus}, {@link Skill}
     */
    public static final int IN_GAME_REQUEST_STATUS_TIMEOUT = 2;     // Must be greater than the time(sec) to wait for all requests(WAITING_COUNT_FOR_REQUEST * SLEEP_MILLIS_FOR_REQUEST)
    public static final int SKILL_INFO_TIMEOUT = HOUR;    // Must be greater than the time(sec) to wait for all requests
}
