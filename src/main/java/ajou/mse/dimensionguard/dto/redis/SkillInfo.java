package ajou.mse.dimensionguard.dto.redis;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

import static ajou.mse.dimensionguard.constant.ConstantUtil.SKILL_INFO_TIMEOUT;

@Getter
@RedisHash(value = "skill", timeToLive = SKILL_INFO_TIMEOUT)
public class SkillInfo {

    @Id
    private Integer roomId;

    private Integer skillId;

    @Setter(AccessLevel.PRIVATE)
    private Integer deliveredCount;

    private LocalDateTime createdAt;

    public SkillInfo(Integer roomId, Integer skillId) {
        this.roomId = roomId;
        this.skillId = skillId;
        this.deliveredCount = 0;
        this.createdAt = LocalDateTime.now();
    }

    public void increaseDeliveredCount() {
        int nextDeliveredCount = this.getDeliveredCount() + 1;
        this.setDeliveredCount(nextDeliveredCount);
    }
}
