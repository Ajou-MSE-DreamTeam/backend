package ajou.mse.dimensionguard.service;

import ajou.mse.dimensionguard.domain.Room;
import ajou.mse.dimensionguard.domain.player.Player;
import ajou.mse.dimensionguard.exception.player.PlayerNotFoundByMemberAndRoomException;
import ajou.mse.dimensionguard.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Transactional
    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public Player findEntityByMemberIdAndRoomId(Integer memberId, Integer roomId) {
        return playerRepository.findByMember_IdAndRoom_Id(memberId, roomId)
                .orElseThrow(() -> new PlayerNotFoundByMemberAndRoomException(memberId, roomId));
    }

    public List<Player> findAllEntityByRoom(Room room) {
        return playerRepository.findAllByRoom(room);
    }

    public void repositoryFlush() {
        playerRepository.flush();
    }
}
