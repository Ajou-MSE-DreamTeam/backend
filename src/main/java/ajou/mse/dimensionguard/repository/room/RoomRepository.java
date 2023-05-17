package ajou.mse.dimensionguard.repository.room;

import ajou.mse.dimensionguard.constant.room.RoomStatus;
import ajou.mse.dimensionguard.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends
        JpaRepository<Room, Long>,
        RoomRepositoryQCustom {

    List<Room> findAllByStatus(RoomStatus status);
}
