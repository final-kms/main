package com.ssg.kms.chatroom;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.chatroomuser.ChatRoomUser;
import com.ssg.kms.chatroomuser.ChatRoomUserRepository;
import com.ssg.kms.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
	
	private final ChatRoomRepository chatRoomRepository;
	private final ChatRoomUserRepository chatRoomUserRepository;
	
    @Transactional
    public ChatRoom createChatRoom(ChatRoomDTO chatRoomDto, Optional<User> user) {
    	
    	ChatRoom chatRoom = ChatRoom.builder()
    			.name(chatRoomDto.getName())
    			.build();
    	
    	ChatRoomUser chatRoomUser = ChatRoomUser.builder()
    			.user(user.get())
    			.chatRoom(chatRoom)
    			.build();
    	
    	chatRoomRepository.save(chatRoom);
    	chatRoomUserRepository.save(chatRoomUser);
    	    	
		return chatRoom;
    }
    

    @Transactional(readOnly = true)
    public ChatRoom readChatRoom(Long chatRoomId, Optional<User> user) {
    	return chatRoomRepository.findById(chatRoomId).get();
    }
    
    @Transactional
    public ChatRoom updateChatRoom(Long chatRoomId, ChatRoomDTO chatRoomDto, Optional<User> user) {
    	
    	ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).get();
    	
    	chatRoom.setName(chatRoomDto.getName());

    	chatRoomRepository.save(chatRoom);
    	
    	return chatRoom;
    }

    @Transactional
    public void deleteChatRoom(Long chatRoomId, Optional<User> user) {

    	ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).get();
    	
    	chatRoomRepository.deleteById(chatRoomId);
    }
}
