package ajou.mse.dimensionguard.service;

import ajou.mse.dimensionguard.dto.in_game.SkillDto;
import ajou.mse.dimensionguard.dto.redis.Skill;
import ajou.mse.dimensionguard.repository.redis.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SkillService {

    private final SkillRepository skillRepository;

    @Transactional
    public Skill saveToRedis(Skill skill) {
        return skillRepository.save(skill);
    }

    public void addSkill(Long roomId, SkillDto skillDto) {
        Skill skill;
        if (isSkillUsed(skillDto)) {
            skill = new Skill(roomId, skillDto.getNum(), skillDto.getPos(), false);
        } else {
            Optional<Skill> optSkillInfo = findOptById(roomId);
            skill = optSkillInfo.map(skillInfo -> new Skill(roomId, skillDto.getNum(), skillDto.getPos(), skillInfo.getIsHit()))
                    .orElseGet(() -> new Skill(roomId, skillDto.getNum(), skillDto.getPos(), false));
        }
        saveToRedis(skill);
    }

    public Skill findById(Long roomId) {
        return skillRepository.findById(roomId)
                .orElseGet(() -> saveToRedis(new Skill(roomId, 0, null, true)));   // Dummy data 저장. Dummy data에 의해 skill 적중 수가 늘어나지 않도록 true로 초기화
    }

    private Optional<Skill> findOptById(Long roomId) {
        return skillRepository.findById(roomId);
    }

    public SkillDto findDtoById(Long roomId) {
        Optional<Skill> optSkillInfo = findOptById(roomId);
        return optSkillInfo.map(SkillDto::from).orElse(null);
    }

    private static boolean isSkillUsed(SkillDto skill) {
        return skill.getNum() > 0;
    }
}
