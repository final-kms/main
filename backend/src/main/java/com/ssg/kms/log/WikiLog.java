package com.ssg.kms.log;

import java.util.Date;

import com.ssg.kms.wiki.Wiki;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@Table(name = "WIKI_LOG")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class WikiLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    
	@ManyToOne //(cascade = CascadeType.ALL)    
	private Wiki wiki;

}