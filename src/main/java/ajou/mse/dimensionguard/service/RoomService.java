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
import ajou.mse.dimensionguard.exception.room.NoBossException;
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
        Member host = memberService.findById(loginMemberId);

        Player player = playerService.save(Boss.of(host, room));
        room.getPlayers().add(player);

        return RoomDto.from(room);
    }

    @Transactional
    public RoomDto join(Long loginMemberId, Long roomId) {
        validateAlreadyParticipating(loginMemberId);

        Room room = this.findById(roomId);
        Member member = memberService.findById(loginMemberId);

        playerService.save(Hero.of(member, room));

        return RoomDto.from(room);  // players lazy loading
    }

    public Room findById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomIdNotFoundException(roomId));
    }

    public Room findByMemberId(Long memberId) {
        return roomRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RoomNotFoundByMemberIdException(memberId));
    }

    public RoomDto findDtoById(Long roomId) {
        return RoomDto.from(findById(roomId));
    }

    public List<RoomDto> findAllDtosByStatusReady() {
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
        Room room = this.findById(roomId);
        if (room.getStatus() == RoomStatus.READY) {
            room.start();
        }

        Player player = playerService.findByMemberIdAndRoomId(loginMemberId, roomId);
        player.setReady();
    }

    @Transactional
    public void init(Long roomId) {
        Room room = this.findById(roomId);
        room.init();
        room.getPlayers().forEach(Player::setNotReady);
    }

    @Transactional
    public void gameStart(Long roomId) {
        Room room = findById(roomId);
        if (room.getGameStartedAt() == null) {  // 게임 시작 시각은 한 번만 초기화 되어야 함
            room.setGameStartedAt(LocalDateTime.now());
        }

        Boss boss = getBossFromRoom(room);
        boss.initHp(room.getPlayers().size() - 1);
    }

    @Transactional
    public void deleteWithPlayers(Long roomId) {
        Room room = findById(roomId);
        playerService.deleteAll(room.getPlayers());
        roomRepository.delete(room);
    }

    private void validateAlreadyParticipating(Long loginMemberId) {
        Optional<Player> optionalPlayer = playerService.findOptByMemberId(loginMemberId);
        if (optionalPlayer.isPresent()) {
            throw new AlreadyParticipatingException(optionalPlayer.get().getRoom().getId());
        }
    }

    private Boss getBossFromRoom(Room room) {
        return room.getPlayers().stream()
                .filter(player -> player instanceof Boss)
                .map(Boss.class::cast)
                .findFirst()
                .orElseThrow(NoBossException::new);
    }
}
