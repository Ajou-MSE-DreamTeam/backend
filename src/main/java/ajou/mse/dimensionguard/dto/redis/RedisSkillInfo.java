package ajou.mse.dimensionguard.dto.redis;

import ajou.mse.dimensionguard.domain.player.Position;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

import static ajou.mse.dimensionguard.constant.ConstantUtil.SKILL_INFO_TIMEOUT;

@Getter
@RedisHash(value = "skill", timeToLive = SKILL_INFO_TIMEOUT)
public class RedisSkillInfo {

    @Id
    private Long roomId;

    private Integer num;

    private Position pos;

    @Setter(AccessLevel.PRIVATE)
    private Integer deliveredCount;

    private LocalDateTime createdAt;

    public RedisSkillInfo(Long roomId, Integer num, Position pos) {
        this.roomId = roomId;
        this.num = num;
        this.pos = pos;
        this.deliveredCount = 0;
        this.createdAt = LocalDateTime.now();
    }

    public void increaseDeliveredCount() {
        int nextDeliveredCount = this.getDeliveredCount() + 1;
        this.setDeliveredCount(nextDeliveredCount);
    }
}
