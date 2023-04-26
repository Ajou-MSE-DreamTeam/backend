package ajou.mse.dimensionguard.service;

import ajou.mse.dimensionguard.constant.room.RoomStatus;
import ajou.mse.dimensionguard.domain.Member;
import ajou.mse.dimensionguard.domain.Room;
import ajou.mse.dimensionguard.domain.player.Boss;
import ajou.mse.dimensionguard.domain.player.Hero;
import ajou.mse.dimensionguard.domain.player.Player;
import ajou.mse.dimensionguard.dto.room.RoomDto;
import ajou.mse.dimensionguard.dto.room.response.CheckGameStartResponse;
import ajou.mse.dimensionguard.exception.room.AlreadyParticipatingException;
import ajou.mse.dimensionguard.exception.room.RoomIdNotFoundException;
import ajou.mse.dimensionguard.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RoomService {

    private final MemberService memberService;
    private final PlayerService playerService;
    private final RoomRepository roomRepository;

    @Transactional
    public RoomDto createRoom(Integer loginMemberId) {
        validateAlreadyParticipating(loginMemberId);

        Room room = roomRepository.save(Room.of());
        Member host = memberService.findEntityById(loginMemberId);

        Player player = playerService.save(Boss.of(host, room));
        room.getPlayers().add(player);

        return RoomDto.from(room);
    }

    @Transactional
    public RoomDto join(Integer loginMemberId, Integer roomId) {
        validateAlreadyParticipating(loginMemberId);

        Room room = this.findEntityById(roomId);
        Member member = memberService.findEntityById(loginMemberId);

        playerService.save(Hero.of(member, room));

        return RoomDto.from(room);  // players lazy loading
    }

    public Room findEntityById(Integer roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomIdNotFoundException(roomId));
    }

    private RoomDto findDtoById(Integer roomId) {
        return RoomDto.from(findEntityById(roomId));
    }

    public List<RoomDto> findAllByStatusReady() {
        return roomRepository.findAllByStatus(RoomStatus.READY).stream()
                .map(RoomDto::from)
                .toList();
    }

    @Transactional
    public CheckGameStartResponse checkGameStarted(Integer roomId) {
        RoomDto roomDto = this.findDtoById(roomId);
        return CheckGameStartResponse.from(roomDto);
    }

    @Transactional
    public RoomDto ready(Integer loginMemberId, Integer roomId) {
        Room room = this.findEntityById(roomId);
        if (room.getStatus() == RoomStatus.READY) {
            room.start();
        }

        Player player = playerService.findEntityByMemberIdAndRoomId(loginMemberId, roomId);
        player.setReady();

        return RoomDto.from(room);
    }

    @Transactional
    public void init(Integer roomId) {
        Room room = this.findEntityById(roomId);
        room.init();
        room.getPlayers().forEach(Player::setNotReady);
    }

    @Transactional
    public void exit(Integer loginMemberId, Integer roomId) {
        Room room = this.findEntityById(roomId);

        // 방을 나가려는 유저가 방의 호스트라면 방을 해산한다.
        if (room.getCreatedBy().equals(loginMemberId)) {
            playerService.deleteAll(room.getPlayers());
            roomRepository.delete(room);
        } else {
            Player player = playerService.findEntityByMemberIdAndRoomId(loginMemberId, roomId);
            playerService.delete(player);
        }
    }

    private void validateAlreadyParticipating(Integer loginMemberId) {
        Optional<Player> optionalPlayer = playerService.findOptEntityByMemberId(loginMemberId);
        if (optionalPlayer.isPresent()) {
            throw new AlreadyParticipatingException(optionalPlayer.get().getRoom().getId());
        }
    }
}
