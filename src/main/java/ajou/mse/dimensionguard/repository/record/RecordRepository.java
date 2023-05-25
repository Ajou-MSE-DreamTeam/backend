package ajou.mse.dimensionguard.repository.record;

import ajou.mse.dimensionguard.domain.record.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {

    Optional<Record> findByRoomId(Long roomId);
}
