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
import ajou.mse.dimensionguard.exception.room.RoomNotFoundByMemberIdException;
import ajou.mse.dimensionguard.repository.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public RoomDto createRoom(Long loginMemberId) {
        validateAlreadyParticipating(loginMemberId);

        Room room = roomRepository.save(Room.of());
        Member host = memberService.findEntityById(loginMemberId);

        Player player = playerService.save(Boss.of(host, room));
        room.getPlayers().add(player);

        return RoomDto.from(room);
    }

    @Transactional
    public RoomDto join(Long loginMemberId, Long roomId) {
        validateAlreadyParticipating(loginMemberId);

        Room room = this.findEntityById(roomId);
        Member member = memberService.findEntityById(loginMemberId);

        playerService.save(Hero.of(member, room));

        return RoomDto.from(room);  // players lazy loading
    }

    public Room findEntityById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomIdNotFoundException(roomId));
    }

    public Room findEntityByMemberId(Long memberId) {
        return roomRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RoomNotFoundByMemberIdException(memberId));
    }

    public RoomDto findDtoById(Long roomId) {
        return RoomDto.from(findEntityById(roomId));
    }

    public List<RoomDto> findAllByStatusReady() {
        return roomRepository.findAllByStatus(RoomStatus.READY).stream()
                .map(RoomDto::from)
                .toList();
    }

    public CheckGameStartResponse checkGameStarted(Long roomId) {
        RoomDto roomDto = this.findDtoById(roomId);
        return CheckGameStartResponse.from(roomDto);
    }

    @Transactional
    public void ready(Long loginMemberId, Long roomId) {
        Room room = this.findEntityById(roomId);
        if (room.getStatus() == RoomStatus.READY) {
            room.start();
        }

        Player player = playerService.findByMemberIdAndRoomId(loginMemberId, roomId);
        player.setReady();
    }

    @Transactional
    public void init(Long roomId) {
        Room room = this.findEntityById(roomId);
        room.init();
        room.getPlayers().forEach(Player::setNotReady);
    }

    @Transactional
    public void setGameStartedAt(Long roomId, LocalDateTime startedAt) {
        Room room = findEntityById(roomId);
        if (room.getGameStartedAt() == null) {
            room.setGameStartedAt(startedAt);
        }
    }

    private void validateAlreadyParticipating(Long loginMemberId) {
        Optional<Player> optionalPlayer = playerService.findOptByMemberId(loginMemberId);
        if (optionalPlayer.isPresent()) {
            throw new AlreadyParticipatingException(optionalPlayer.get().getRoom().getId());
        }
    }
}
