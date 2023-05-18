package ajou.mse.dimensionguard.dto.redis;

import ajou.mse.dimensionguard.domain.player.Position;
import lombok.Getter;
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

    private Boolean isHit;

    private LocalDateTime createdAt;

    public RedisSkillInfo(Long roomId, Integer num, Position pos, Boolean isHit) {
        this.roomId = roomId;
        this.num = num;
        this.pos = pos;
        this.isHit = isHit;
        this.createdAt = LocalDateTime.now();
    }
}
