package com.ssg.kms.like.reply;

import com.ssg.kms.reply.Reply;
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
@Table(name = "REPLY_LIKE")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReplyLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

	@ManyToOne //(cascade = CascadeType.ALL)    
	private User user;

	@ManyToOne //(cascade = CascadeType.ALL)    
	private Reply reply;

}