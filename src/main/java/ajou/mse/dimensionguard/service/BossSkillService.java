package ajou.mse.dimensionguard.service;

import ajou.mse.dimensionguard.dto.redis.SkillInfo;
import ajou.mse.dimensionguard.repository.redis.SkillInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BossSkillService {

    private final SkillInfoRepository skillInfoRepository;

    @Transactional
    public void addSkill(Integer roomId, Integer skillId) {
        SkillInfo skillInfo = new SkillInfo(roomId, skillId);
        skillInfoRepository.save(skillInfo);
    }

    @Transactional
    public Integer getSkillUsed(Integer roomId, Integer numOfPlayers) {
        Optional<SkillInfo> optionalSkillInfo = skillInfoRepository.findById(roomId);

        if (optionalSkillInfo.isEmpty()) {
            System.out.println(11111);
            return null;
        }

        System.out.println(22222);
        SkillInfo skillInfo = optionalSkillInfo.get();
        skillInfo.increaseDeliveredCount();
        skillInfoRepository.save(skillInfo);

        SkillInfo updatedSkillInfo = skillInfoRepository.findById(roomId).orElseThrow();
        if (updatedSkillInfo.getDeliveredCount().equals(numOfPlayers)) {
            skillInfoRepository.deleteById(roomId);
        }

        return skillInfo.getSkillId();
    }

    @Transactional
    public void clear() {
        skillInfoRepository.deleteAll();
    }
}
