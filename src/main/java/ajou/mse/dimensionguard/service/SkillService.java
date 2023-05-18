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
    public RedisSkillInfo saveToRedis(RedisSkillInfo redisSkillInfo) {
        return redisSkillInfoRepository.save(redisSkillInfo);
    }

    public void addSkill(Long roomId, SkillDto skill) {
        RedisSkillInfo redisSkillInfo;
        if (isSkillUsed(skill)) {
            redisSkillInfo = new RedisSkillInfo(roomId, skill.getNum(), skill.getPos(), false);
        } else {
            Optional<RedisSkillInfo> optSkillInfo = findOptById(roomId);
            redisSkillInfo = optSkillInfo.map(skillInfo -> new RedisSkillInfo(roomId, skill.getNum(), skill.getPos(), skillInfo.getIsHit()))
                    .orElseGet(() -> new RedisSkillInfo(roomId, skill.getNum(), skill.getPos(), false));
        }
        saveToRedis(redisSkillInfo);
    }

    public RedisSkillInfo findById(Long roomId) {
        return redisSkillInfoRepository.findById(roomId)
                .orElseGet(() -> saveToRedis(new RedisSkillInfo(roomId, 0, null, true)));   // Dummy data 저장. Dummy data에 의해 skill 적중 수가 늘어나지 않도록 true로 초기화
    }

    private Optional<RedisSkillInfo> findOptById(Long roomId) {
        return redisSkillInfoRepository.findById(roomId);
    }

    public SkillDto findDtoById(Long roomId) {
        Optional<RedisSkillInfo> optSkillInfo = findOptById(roomId);
        return optSkillInfo.map(SkillDto::from).orElse(null);
    }

    private static boolean isSkillUsed(SkillDto skill) {
        return skill.getNum() > 0;
    }
}
