package com.ssg.kms.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.chat.Chat;
import com.ssg.kms.chat.ChatRepository;
import com.ssg.kms.chatroomuser.ChatRoomUserRepository;
import com.ssg.kms.follow.FollowRepository;
import com.ssg.kms.like.post.PostLikeRepository;
import com.ssg.kms.like.wiki.WikiLikeRepository;
import com.ssg.kms.post.Post;
import com.ssg.kms.post.PostRepository;
import com.ssg.kms.reply.Reply;
import com.ssg.kms.reply.ReplyRepository;
import com.ssg.kms.role.ERole;
import com.ssg.kms.role.Role;
import com.ssg.kms.role.RoleRepository;
import com.ssg.kms.security.SecurityUtil;
import com.ssg.kms.star.post.PostStarRepository;
import com.ssg.kms.star.wiki.WikiStarRepository;
import com.ssg.kms.tableuser.TableUserRepository;
import com.ssg.kms.wiki.Wiki;
import com.ssg.kms.wiki.WikiRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;    
    private final TableUserRepository tableUserRepository;
    private final FollowRepository followRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostStarRepository postStarRepository;
    private final WikiLikeRepository wikiLikeRepository;
    private final WikiStarRepository wikiStarRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final PostRepository postRepository;
    private final WikiRepository wikiRepository;
    private final ReplyRepository replyRepository;
    private final ChatRepository chatRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public Boolean delete(Optional<User> user) {

    	List<Post> posts = postRepository.findAllByUserId(user.get().getId());
    	for(Post post : posts) {
    		post.setUser(null);
    	}

    	List<Wiki> wikis = wikiRepository.findAllByUserId(user.get().getId());
    	for(Wiki wiki : wikis) {
    		wiki.setUser(null);
    	}
    	
    	List<Reply> replys = replyRepository.findAllByUserId(user.get().getId());
    	for(Reply reply : replys) {
    		reply.setUser(null);
    	}

    	List<Chat> chats = chatRepository.findAllByUserId(user.get().getId());
    	for(Chat chat : chats) {
    		chat.setUser(null);
    	}

    	userRoleRepository.deleteAllByUserId(user.get().getId());
    	tableUserRepository.deleteAllByUserId(user.get().getId());
    	followRepository.deleteAllByFollowerId(user.get().getId());
    	followRepository.deleteAllByFolloweeId(user.get().getId());
    	postLikeRepository.deleteAllByUserId(user.get().getId());
    	postStarRepository.deleteAllByUserId(user.get().getId());
    	wikiLikeRepository.deleteAllByUserId(user.get().getId());
    	wikiStarRepository.deleteAllByUserId(user.get().getId());
    	chatRoomUserRepository.deleteAllByUserId(user.get().getId());
    	
        userRepository.delete(user.get());
        
		return true;
    }
    
    @Transactional(readOnly = true)
    public List<String> getInfo(Optional<User> user) {
    	List<String> info = new ArrayList<>();
    	info.add(user.get().getUsername());
    	info.add(user.get().getEmail());

    	return info;
    }
    
    @Transactional(readOnly = true)
    public List<String> searchEmails(String searchKeyword, Optional<User> user) {
    	List<String> userEmails = userRepository.findAllByEmailContaining(searchKeyword, user.get().getId());

    	return userEmails;
    }

	// USER INFO
    //////////////////////////////////////////////////////////////////////    
    @Transactional
    public String updateUsername(UsernameDTO usernameDto, Optional<User> user) {
    	user.get().setUsername(usernameDto.getUsername());
    	userRepository.save(user.get());
    	return usernameDto.getUsername();
    }
    
    @Transactional
    public String updateEmail(EmailDTO emailDto, Optional<User> user) {
    	user.get().setEmail(emailDto.getEmail());
    	userRepository.save(user.get());
    	return emailDto.getEmail();
    }
    
    @Transactional
    public Boolean updatePassword(PasswordDTO passwordDto, Optional<User> user) {
    	// passwordEncoder.encode(userDto.getPassword())
    	user.get().setPassword(passwordEncoder.encode(passwordDto.getPassword()));
    	userRepository.save(user.get());
    	return true;
    }
    //////////////////////////////////////////////////////////////////////
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
