package ajou.mse.dimensionguard.repository;

import ajou.mse.dimensionguard.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
