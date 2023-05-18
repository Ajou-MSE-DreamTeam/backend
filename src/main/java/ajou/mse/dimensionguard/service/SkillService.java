package ajou.mse.dimensionguard.service;

import ajou.mse.dimensionguard.dto.in_game.SkillDto;
import ajou.mse.dimensionguard.dto.redis.RedisSkillInfo;
import ajou.mse.dimensionguard.repository.redis.RedisSkillInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SkillService {

    private final RedisSkillInfoRepository redisSkillInfoRepository;

    @Transactional
    public void addSkill(Long roomId, SkillDto skill) {
        RedisSkillInfo redisSkillInfo = new RedisSkillInfo(roomId, skill.getNum(), skill.getPos());
        redisSkillInfoRepository.save(redisSkillInfo);
    }

    public SkillDto getSkillUsed(Long roomId) {
        Optional<RedisSkillInfo> optSkillInfo = redisSkillInfoRepository.findById(roomId);

        if (optSkillInfo.isEmpty()) {
            return null;
        }

        RedisSkillInfo redisSkillInfo = optSkillInfo.get();
        if (redisSkillInfo.getNum() < 0) {
            return null;
        }

        return SkillDto.from(redisSkillInfo);
    }
}
