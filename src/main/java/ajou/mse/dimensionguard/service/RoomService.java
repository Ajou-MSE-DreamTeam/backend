package ajou.mse.dimensionguard.service;

import ajou.mse.dimensionguard.constant.room.RoomStatus;
import ajou.mse.dimensionguard.domain.Member;
import ajou.mse.dimensionguard.domain.Room;
import ajou.mse.dimensionguard.domain.player.Boss;
import ajou.mse.dimensionguard.domain.player.Hero;
import ajou.mse.dimensionguard.domain.player.Player;
import ajou.mse.dimensionguard.dto.room.RoomDto;
import ajou.mse.dimensionguard.exception.room.RoomIdNotFoundException;
import ajou.mse.dimensionguard.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RoomService {

    private final MemberService memberService;
    private final PlayerService playerService;
    private final RoomRepository roomRepository;

    @Transactional
    public RoomDto createRoom(Integer loginMemberId) {
        Room room = roomRepository.save(Room.of());
        Member host = memberService.findEntityById(loginMemberId);

        Player player = playerService.save(Boss.of(host, room));
        room.getPlayers().add(player);

        return RoomDto.from(room);
    }

    @Transactional
    public RoomDto join(Integer loginMemberId, Integer roomId) {
        Room room = this.findEntityById(roomId);
        Member member = memberService.findEntityById(loginMemberId);

        playerService.save(Hero.of(member, room));

        return RoomDto.from(room);  // players lazy loading
    }

    public Room findEntityById(Integer roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomIdNotFoundException(roomId));
    }

    public List<RoomDto> findAllByStatusReady() {
        return roomRepository.findAllByStatus(RoomStatus.READY).stream()
                .map(RoomDto::from)
                .toList();
    }

    @Transactional
    public boolean checkGameStarted(Integer roomId) {
        Room room = this.findEntityById(roomId);
        return room.getStatus() != RoomStatus.READY;
    }

    @Transactional
    public RoomDto gameStart(Integer loginMemberId, Integer roomId) {
        Room room = this.findEntityById(roomId);
        room.start();

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
}
