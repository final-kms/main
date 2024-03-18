package com.ssg.kms.user;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.role.ERole;
import com.ssg.kms.role.Role;
import com.ssg.kms.role.RoleRepository;
import com.ssg.kms.security.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public User test(UserDto userDto) {
    	User user = userRepository.findByUsername(userDto.getUsername()).orElse(null);
    	
        userRepository.delete(user);
        
//    	Set<UserRole> userRoles = user.getUserRoles();
//        for (UserRole userRole : userRoles) {
//            Role role = userRole.getRole();
//            ERole roleName = role.getRole();
//            System.out.println("사용자 " + user.getUsername() + "의 역할: " + roleName);
//        }
		return user;
    }
    @Transactional
    public User signup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
        
        if (userRepository.findOneWithAuthoritiesByEmail(userDto.getEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 이메일입니다.");
        }

        // 가입되어 있지 않은 회원이면,
        // 권한 정보 만들고
        Role authority = Role.builder()
                .role(ERole.ROLE_USER)
                .id(roleRepository.findByRole(ERole.ROLE_USER).getId())
                .build();

        Role authority2 = Role.builder()
                .role(ERole.ROLE_ADMIN)
                .id(roleRepository.findByRole(ERole.ROLE_ADMIN).getId())
                .build();

        // 유저 정보를 만들어서 save
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .build();
        
        UserRole userRole = UserRole.builder()
        		.user(user)
        		.role(authority)
        		.build();

        UserRole userRole2 = UserRole.builder()
        		.user(user)
        		.role(authority2)
        		.build();

//        user.getUserRoles().add(userRole);        
//        userRole.setUser(user);
        userRepository.save(user);
        userRoleRepository.save(userRole);
        userRoleRepository.save(userRole2);
        
        return user;
    }

    // 유저,권한 정보를 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String username) {
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }

    // 현재 securityContext에 저장된 username의 정보만 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }
    
    @Transactional
    public User updateUser(UserDto userDto, User user) {
    	user.setEmail(userDto.getEmail());
    	user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }
    
    @Transactional
    public void deleteUser(User user) {
    	userRepository.delete(user);
    }
    
}
