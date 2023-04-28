package ajou.mse.dimensionguard.repository.room;

import ajou.mse.dimensionguard.domain.Room;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static ajou.mse.dimensionguard.domain.QRoom.room;
import static ajou.mse.dimensionguard.domain.player.QPlayer.player;

@RequiredArgsConstructor
public class RoomRepositoryQCustomImpl implements RoomRepositoryQCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Room> findByMemberId(Integer memberId) {
        return Optional.ofNullable(
                queryFactory.select(room)
                        .from(player)
                        .join(player.room, room)
                        .where(player.member.id.eq(memberId))
                        .fetchOne()
        );
    }
}
