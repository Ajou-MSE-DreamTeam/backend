package ajou.mse.dimensionguard.repository.room;

import ajou.mse.dimensionguard.domain.Room;

import java.util.Optional;

public interface RoomRepositoryQCustom {

    /**
     * memberId에 해당하는 회원이 참여중인 room을 조회한다.
     *
     * @param memberId PK of member
     * @return 조회된 room
     */
    Optional<Room> findByMemberId(Long memberId);
}
