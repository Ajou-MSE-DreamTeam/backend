package ajou.mse.dimensionguard.service;

import ajou.mse.dimensionguard.domain.Room;
import ajou.mse.dimensionguard.domain.player.Player;
import ajou.mse.dimensionguard.exception.room.EveryoneNotReadyException;
import ajou.mse.dimensionguard.exception.room.GameStartException;
import ajou.mse.dimensionguard.service.PlayerService;
import ajou.mse.dimensionguard.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
@Service
public class GameSyncService {

    private final RoomService roomService;
    private final PlayerService playerService;
    private final EntityManager entityManager;

    public void waitUntilEveryoneIsReady(Integer roomId) {
        Room room = roomService.findEntityById(roomId);

        int count = 0;
        try {
            while (!checkEveryoneIsReady(room)) {
                if (++count > 30) {
                    throw new EveryoneNotReadyException();
                }
                Thread.sleep(500);
            }
        } catch (InterruptedException ex) {
            throw new GameStartException(ex);
        }
    }

    private boolean checkEveryoneIsReady(Room room) {
        entityManager.clear();
        List<Player> players = playerService.findAllEntityByRoom(room);
        return players.stream().allMatch(Player::getIsReady);
    }
}
