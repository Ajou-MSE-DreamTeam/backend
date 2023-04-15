package ajou.mse.dimensionguard.repository;

import ajou.mse.dimensionguard.domain.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Integer> {
}
