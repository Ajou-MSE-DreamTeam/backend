package ajou.mse.dimensionguard.repository;

import ajou.mse.dimensionguard.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
