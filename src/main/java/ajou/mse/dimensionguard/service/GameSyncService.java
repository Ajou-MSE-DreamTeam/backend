package ajou.mse.dimensionguard.service;

import ajou.mse.dimensionguard.domain.Room;
import ajou.mse.dimensionguard.domain.player.Player;
import ajou.mse.dimensionguard.exception.room.EveryoneNotReadyException;
import ajou.mse.dimensionguard.exception.room.GameStartException;
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

    public void syncUntilDeliveredToEveryone(Integer roomId) {
        Room room = roomService.findEntityById(roomId);

        int count = 0;
        try {
            while (!checkDeliveredToEveryone(room)) {
                if (count++ >= 25) throw new EveryoneNotReadyException();
                Thread.sleep(400);
            }
        } catch (InterruptedException ex) {
            throw new GameStartException(ex);
        }
    }

    private boolean checkDeliveredToEveryone(Room room) {
        entityManager.clear();
        List<Player> players = playerService.findAllEntityByRoom(room);
        players.forEach(player -> {
            System.out.println("player[" + player.getMember().getAccountId() + "].ready = " + player.getIsReady());
        });
        return players.stream().allMatch(Player::getIsReady);
    }
}
