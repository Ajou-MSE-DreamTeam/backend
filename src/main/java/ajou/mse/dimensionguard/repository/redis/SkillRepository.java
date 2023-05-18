package ajou.mse.dimensionguard.repository.redis;

import ajou.mse.dimensionguard.dto.redis.Skill;
import org.springframework.data.repository.CrudRepository;

public interface SkillRepository extends CrudRepository<Skill, Long> {
}
