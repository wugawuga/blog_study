package com.wugablog.meta.config.auth;

import com.wugablog.meta.model.User;
import com.wugablog.meta.repository.UserRepository;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(
                () -> new UsernameNotFoundException("찾을 수가 없어요")
            );
        return new PrincipalDetail(user);
    }
}
