package com.ssg.kms.follow;

import com.ssg.kms.post.Post;
import com.ssg.kms.user.User;

import jakarta.persistence.CascadeType;
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
@Table(name = "FOLLOW")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

	@ManyToOne //(cascade = CascadeType.ALL)    
	private User follower;

	@ManyToOne //(cascade = CascadeType.ALL)    
	private User followee;

}