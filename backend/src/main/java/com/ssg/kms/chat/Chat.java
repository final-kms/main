package com.ssg.kms.chat;

import java.util.Date;

import com.ssg.kms.chatroom.ChatRoom;
import com.ssg.kms.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CHAT")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

	@NotBlank
	private String content;
	
	@Temporal(TemporalType.TIMESTAMP)
    private Date date;

	@ManyToOne 
	private User user;
	
	@ManyToOne 
	private ChatRoom chatRoom;
}
