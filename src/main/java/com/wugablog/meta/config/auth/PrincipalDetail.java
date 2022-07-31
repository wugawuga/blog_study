package com.wugablog.meta.config.auth;

import com.wugablog.meta.model.User;
import java.util.ArrayList;
import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class PrincipalDetail implements UserDetails {

    private User user;

    public PrincipalDetail(User user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정이 만료되지 않았는지 반환
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있지 않는지 반환
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 말료되지 않았는지 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화(사용가능)인지 반환
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(
            (GrantedAuthority) () -> "ROLE_" + user.getRole()
        );
        return collectors;
    }
}
