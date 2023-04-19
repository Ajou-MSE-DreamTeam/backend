package ajou.mse.dimensionguard.service;

import ajou.mse.dimensionguard.domain.player.Player;
import ajou.mse.dimensionguard.exception.player.PlayerByMemberIdNotFoundException;
import ajou.mse.dimensionguard.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Transactional
    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public Player findEntityByMemberId(Integer memberId) {
        return playerRepository.findByMember_Id(memberId)
                .orElseThrow(() -> new PlayerByMemberIdNotFoundException(memberId));
    }
}
