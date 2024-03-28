package com.ssg.kms.star.wiki;

import com.ssg.kms.user.User;
import com.ssg.kms.wiki.Wiki;

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
@Table(name = "WIKI_STAR")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class WikiStar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

	@ManyToOne //(cascade = CascadeType.ALL)    
	private User user;

	@ManyToOne //(cascade = CascadeType.ALL)    
	private Wiki wiki;

}