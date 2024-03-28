package com.ssg.kms.chatroomuser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.chatroom.ChatRoom;
import com.ssg.kms.chatroom.ChatRoomRepository;
import com.ssg.kms.user.User;
import com.ssg.kms.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomUserService {
	
	private final ChatRoomRepository chatRoomRepository;
	private final ChatRoomUserRepository chatRoomUserRepository;
	private final UserRepository userRepository;
	
    @Transactional
    public List<ChatRoomUser> createChatRoomUser(Long chatRoomId, ChatRoomUserDTO chatRoomUserDto, Optional<User> user) {
    	
    	ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).get();
    	
    	List<ChatRoomUser> chatRoomUsers = new ArrayList<>();
    	
    	for(String email : chatRoomUserDto.getEmail()) {
    		
    		User foundUser = userRepository.findByEmail(email);

    		if(foundUser != null && chatRoomUserRepository.findByChatRoomIdAndUserId(chatRoomId, foundUser.getId()).isEmpty()) {
	    		ChatRoomUser chatRoomUser = ChatRoomUser.builder()
	        			.user(foundUser)
	        			.chatRoom(chatRoom)
	        			.build();
	    		
	    		chatRoomUsers.add(chatRoomUser);
    		}
    	}
    	
    	chatRoomUserRepository.saveAll(chatRoomUsers);
    	    	
    	    	
		return chatRoomUsers;
    }
    

    @Transactional(readOnly = true)
    public List<ChatRoomUser> readChatRoomUser(Long chatRoomId, Optional<User> user) {
    	return chatRoomUserRepository.findAllByChatRoomId(chatRoomId);
    }
    
    @Transactional(readOnly = true)
    public List<ChatRoomUser> readAllChatRoom(Optional<User> user) {
    	
    	return chatRoomUserRepository.findAllByUserId(user.get().getId());
    }
    
    @Transactional
    public void deleteChatRoomUser(Long chatRoomUserId, Optional<User> user) {
    	
    	chatRoomUserRepository.deleteById(chatRoomUserId);

    }
}
