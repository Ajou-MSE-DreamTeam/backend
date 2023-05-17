package ajou.mse.dimensionguard.service;

import ajou.mse.dimensionguard.domain.Room;
import ajou.mse.dimensionguard.domain.player.Player;
import ajou.mse.dimensionguard.dto.redis.InGameRequestStatus;
import ajou.mse.dimensionguard.exception.room.EveryoneNotReadyException;
import ajou.mse.dimensionguard.exception.room.GameStartException;
import ajou.mse.dimensionguard.repository.redis.InGameRequestStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static ajou.mse.dimensionguard.constant.ConstantUtil.*;

@RequiredArgsConstructor
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
@Service
public class GameSyncService {

    private final RoomService roomService;
    private final PlayerService playerService;
    private final InGameRequestStatusRepository inGameRequestStatusRepository;
    private final EntityManager entityManager;

    @Transactional
    public void increaseRequestCount(Long roomId) {
        InGameRequestStatus inGameRequestStatus = findInGameReqStatus(roomId);
        inGameRequestStatus.increaseRequestCount();
        inGameRequestStatusRepository.save(inGameRequestStatus);
    }

    @Transactional
    public void initRequestCount(Long roomId) {
        InGameRequestStatus inGameRequestStatus = findInGameReqStatus(roomId);
        if (inGameRequestStatus.getCount() != 0) {
            inGameRequestStatus.initRequestCount();
            inGameRequestStatusRepository.save(inGameRequestStatus);
        }
    }

    public void waitUntilEveryoneIsReady(Long roomId) {
        Room room = roomService.findEntityById(roomId);

        int count = 0;
        try {
            while (!checkEveryoneIsReady(room)) {
                if (++count > WAITING_COUNT_FOR_READY) {
                    throw new EveryoneNotReadyException();
                }
                Thread.sleep(SLEEP_MILLIS_FOR_READY);
            }
        } catch (InterruptedException ex) {
            throw new GameStartException(ex);
        }
    }

    public void waitUntilEveryoneRequest(Long roomId) {
        Room room = roomService.findEntityById(roomId);
        int numOfPlayers = room.getPlayers().size();

        int threadLoopCount = 0;
        try {
            while (!isEveryoneRequest(roomId, numOfPlayers)) {
                if (++threadLoopCount > WAITING_COUNT_FOR_REQUEST) return;
                Thread.sleep(SLEEP_MILLIS_FOR_REQUEST);
            }
        } catch (InterruptedException ignored) {
        }
    }

    private InGameRequestStatus findInGameReqStatus(Long roomId) {
        return inGameRequestStatusRepository.findById(roomId)
                .orElseGet(() -> inGameRequestStatusRepository.save(new InGameRequestStatus(roomId)));
    }

    private boolean checkEveryoneIsReady(Room room) {
        entityManager.clear();
        List<Player> players = playerService.findAllByRoom(room);
        return players.stream().allMatch(Player::getIsReady);
    }

    private boolean isEveryoneRequest(Long roomId, Integer numOfPlayers) {
        return this.findInGameReqStatus(roomId).getCount().equals(numOfPlayers);
    }
}
