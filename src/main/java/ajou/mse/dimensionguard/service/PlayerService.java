package ajou.mse.dimensionguard.service;

import ajou.mse.dimensionguard.domain.Room;
import ajou.mse.dimensionguard.domain.player.Player;
import ajou.mse.dimensionguard.exception.player.PlayerNotFoundByMemberAndRoomException;
import ajou.mse.dimensionguard.exception.player.PlayerNotFoundByMemberIdException;
import ajou.mse.dimensionguard.repository.PlayerRepository;
import ajou.mse.dimensionguard.repository.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public Optional<Player> findOptEntityByMemberId(Integer memberId) {
        return playerRepository.findByMember_Id(memberId);
    }

    public Player findEntityByMemberId(Integer memberId) {
        return this.findOptEntityByMemberId(memberId)
                .orElseThrow(() -> new PlayerNotFoundByMemberIdException(memberId));
    }

    public Player findEntityByMemberIdAndRoomId(Integer memberId, Integer roomId) {
        return playerRepository.findByMember_IdAndRoom_Id(memberId, roomId)
                .orElseThrow(() -> new PlayerNotFoundByMemberAndRoomException(memberId, roomId));
    }

    public List<Player> findAllEntityByRoom(Room room) {
        return playerRepository.findAllByRoom(room);
    }

    @Transactional
    public void delete(Player player) {
        playerRepository.delete(player);
    }

    @Transactional
    public void deleteAll(Iterable<? extends Player> players) {
        playerRepository.deleteAll(players);
    }

    @Transactional
    public void exit(Integer loginMemberId) {
        Player player = this.findEntityByMemberId(loginMemberId);
        Room room = player.getRoom();

        // 방을 나가려는 유저가 방의 호스트라면 방도 함께 삭제한다
        if (room.getCreatedBy().equals(loginMemberId)) {
            this.deleteAll(room.getPlayers());
            roomRepository.delete(room);
        } else {
            this.delete(player);
        }
    }
}
