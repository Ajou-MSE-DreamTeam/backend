package ajou.mse.dimensionguard.repository.redis;

import ajou.mse.dimensionguard.dto.redis.InGameRequestStatus;
import org.springframework.data.repository.CrudRepository;

public interface InGameRequestStatusRepository extends CrudRepository<InGameRequestStatus, Long> {
}
