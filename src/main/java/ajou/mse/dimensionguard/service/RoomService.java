package ajou.mse.dimensionguard.service;

import ajou.mse.dimensionguard.domain.Member;
import ajou.mse.dimensionguard.domain.Room;
import ajou.mse.dimensionguard.domain.player.Boss;
import ajou.mse.dimensionguard.dto.room.RoomDto;
import ajou.mse.dimensionguard.repository.PlayerRepository;
import ajou.mse.dimensionguard.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RoomService {

    private final MemberService memberService;
    private final RoomRepository roomRepository;
    private final PlayerRepository playerRepository;

    @Transactional
    public RoomDto createRoom(Integer loginMemberId) {
        Room room = roomRepository.save(Room.of());
        Member host = memberService.findEntityById(loginMemberId);
        playerRepository.save(Boss.of(host, room));
        return RoomDto.from(room);
    }
}
