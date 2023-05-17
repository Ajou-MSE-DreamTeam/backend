package ajou.mse.dimensionguard.repository;

import ajou.mse.dimensionguard.domain.Room;
import ajou.mse.dimensionguard.domain.player.Player;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @EntityGraph(attributePaths = {"member", "room"})
    Optional<Player> findByMember_Id(Long memberId);

    @EntityGraph(attributePaths = {"member", "room"})
    Optional<Player> findByMember_IdAndRoom_Id(Long memberId, Long roomId);

    @EntityGraph(attributePaths = {"member", "room"})
    List<Player> findAllByRoom(Room room);
}
