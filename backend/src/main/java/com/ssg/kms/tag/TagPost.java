package com.ssg.kms.tag;

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
@Table(name = "TAG_POST")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TagPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

	@ManyToOne //(cascade = CascadeType.ALL)    
	private Tag tag;

	@ManyToOne //(cascade = CascadeType.ALL)    
	private Post post;

}