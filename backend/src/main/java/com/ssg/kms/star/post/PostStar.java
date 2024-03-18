package com.ssg.kms.star.post;

import com.ssg.kms.post.Post;
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
@Table(name = "POST_STAR")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PostStar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

	@ManyToOne //(cascade = CascadeType.ALL)    
	private User user;

	@ManyToOne //(cascade = CascadeType.ALL)    
	private Post post;

}