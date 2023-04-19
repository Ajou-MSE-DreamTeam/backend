package ajou.mse.dimensionguard.repository;

import ajou.mse.dimensionguard.domain.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

    Optional<Player> findByMember_Id(Integer memberId);
}
