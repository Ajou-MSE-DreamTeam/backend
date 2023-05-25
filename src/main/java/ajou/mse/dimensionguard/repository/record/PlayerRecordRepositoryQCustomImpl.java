package ajou.mse.dimensionguard.repository.record;

import ajou.mse.dimensionguard.constant.GameResult;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static ajou.mse.dimensionguard.domain.record.QPlayerRecord.playerRecord;
import static ajou.mse.dimensionguard.domain.record.QRecord.record;

@RequiredArgsConstructor
public class PlayerRecordRepositoryQCustomImpl implements PlayerRecordRepositoryQCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long countGamesWon(Long memberId, boolean isBoss) {
        BooleanExpression cond = playerRecord.member.id.eq(memberId);
        if (isBoss) {
            cond = cond.and(playerRecord.isBoss.eq(true).and(record.result.eq(GameResult.BOSS_WIN)));
        } else {
            cond = cond.and(playerRecord.isBoss.eq(false).and(record.result.eq(GameResult.HERO_WIN)));
        }

        return queryFactory.select(playerRecord.count())
                .from(playerRecord)
                .join(playerRecord.record, record)
                .where(cond)
                .fetchOne();
    }
}
