package com.ssg.kms.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    @Transactional
    // 로그인시에 DB에서 유저정보와 권한정보를 가져와서 해당 정보를 기반으로 userdetails.User 객체를 생성해 리턴
    public UserDetails loadUserByUsername(final String username) {

        return userRepository.findOneWithAuthoritiesByUsername(username)
                .map(user -> createUser(username, user))
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(String username, User user) {

    	List<UserRole> userRoles = userRoleRepository.findAllByUser(user);
    	
        List<GrantedAuthority> grantedAuthorities = userRoles.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().getRole().name()))  //    .getRole().name()))
                .collect(Collectors.toList());

    	
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                grantedAuthorities);
    }
}
