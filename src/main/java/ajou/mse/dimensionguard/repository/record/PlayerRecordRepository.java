package ajou.mse.dimensionguard.repository.record;

import ajou.mse.dimensionguard.domain.record.PlayerRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRecordRepository extends JpaRepository<PlayerRecord, Long> {
}
