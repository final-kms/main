package com.ssg.kms.log.search;

import java.util.Date;

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
@Table(name = "SEARCH_LOG")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SearchLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long search_log_id;
	
	@NotBlank
	private String content;
	
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    
    @ManyToOne
    private User user;
	
}
