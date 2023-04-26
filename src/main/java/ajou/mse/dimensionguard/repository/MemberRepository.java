package ajou.mse.dimensionguard.repository;

import ajou.mse.dimensionguard.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    boolean existsByAccountId(String accountId);

    boolean existsByName(String name);

    Optional<Member> findByAccountId(String accountId);
}
