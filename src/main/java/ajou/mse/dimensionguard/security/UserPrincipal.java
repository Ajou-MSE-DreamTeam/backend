package ajou.mse.dimensionguard.security;

import ajou.mse.dimensionguard.constant.member.RoleType;
import ajou.mse.dimensionguard.dto.member.MemberDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserPrincipal implements UserDetails {

    private MemberDto memberDto;

    public static UserPrincipal of(MemberDto memberDto) {
        return new UserPrincipal(memberDto);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<RoleType> roleTypes = Set.of(RoleType.values());

        return roleTypes.stream()
                .map(RoleType::getName)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public Long getMemberId() {
        return memberDto.getId();
    }

    @Override
    public String getUsername() {
        return String.valueOf(memberDto.getId());
    }

    @Override
    public String getPassword() {
        return memberDto.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
