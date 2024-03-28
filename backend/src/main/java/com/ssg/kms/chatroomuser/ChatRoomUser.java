package com.ssg.kms.chatroomuser;

import com.ssg.kms.chatroom.ChatRoom;
import com.ssg.kms.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CHAT_ROOM_USER")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChatRoomUser {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

	@ManyToOne 
	private User user;

	@ManyToOne 
	private ChatRoom chatRoom;
}
