package ajou.mse.dimensionguard.dto.redis;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import static ajou.mse.dimensionguard.constant.ConstantUtil.IN_GAME_REQUEST_STATUS_TIMEOUT;

@Getter
@RedisHash(value = "requestStatus", timeToLive = IN_GAME_REQUEST_STATUS_TIMEOUT)
public class InGameRequestStatus {

    @Id
    private Long roomId;

    @Setter(AccessLevel.PRIVATE)
    private Integer count;

    public InGameRequestStatus(Long roomId) {
        this.roomId = roomId;
        this.count = 0;
    }

    public void increaseRequestCount() {
        int nextCount = this.getCount() + 1;
        this.setCount(nextCount);
    }

    public void initRequestCount() {
        this.setCount(0);
    }
}
