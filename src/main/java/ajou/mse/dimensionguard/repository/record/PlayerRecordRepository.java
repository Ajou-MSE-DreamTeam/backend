package ajou.mse.dimensionguard.repository.record;

import ajou.mse.dimensionguard.domain.record.PlayerRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRecordRepository extends JpaRepository<PlayerRecord, Long>, PlayerRecordRepositoryQCustom {

    Long countByMember_Id(Long memberId);

    Long countByMember_IdAndIsBoss(Long memberId, boolean isBoss);
}
