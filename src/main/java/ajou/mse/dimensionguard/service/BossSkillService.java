package ajou.mse.dimensionguard.service;

import ajou.mse.dimensionguard.dto.in_game.request.SkillDto;
import ajou.mse.dimensionguard.dto.redis.RedisSkillInfo;
import ajou.mse.dimensionguard.repository.redis.RedisSkillInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BossSkillService {

    private final RedisSkillInfoRepository redisSkillInfoRepository;

    @Transactional
    public void addSkill(Integer roomId, SkillDto skill) {
        RedisSkillInfo redisSkillInfo = new RedisSkillInfo(roomId, skill.getNum(), skill.getPos());
        redisSkillInfoRepository.save(redisSkillInfo);
    }

    @Transactional
    public SkillDto getSkillUsed(Integer roomId, Integer numOfPlayers) {
        Optional<RedisSkillInfo> optionalSkillInfo = redisSkillInfoRepository.findById(roomId);

        if (optionalSkillInfo.isEmpty()) {
            System.out.println(11111);
            return null;
        }

        System.out.println(22222);
        RedisSkillInfo redisSkillInfo = optionalSkillInfo.get();
        redisSkillInfo.increaseDeliveredCount();
        redisSkillInfoRepository.save(redisSkillInfo);

        RedisSkillInfo updatedRedisSkillInfo = redisSkillInfoRepository.findById(roomId).orElseThrow();
        if (updatedRedisSkillInfo.getDeliveredCount().equals(numOfPlayers)) {
            redisSkillInfoRepository.deleteById(roomId);
        }

        return SkillDto.from(redisSkillInfo);
    }

    @Transactional
    public void clear() {
        redisSkillInfoRepository.deleteAll();
    }
}
