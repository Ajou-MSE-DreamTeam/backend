package ajou.mse.dimensionguard.dto.member.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AccountIdExistenceResponse {

    private Boolean isExist;

    public static AccountIdExistenceResponse of(Boolean isExist) {
        return new AccountIdExistenceResponse(isExist);
    }
}
