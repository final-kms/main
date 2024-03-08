package com.ssg.kms.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssg.kms.post.Post;

@Service
public class SearchService {
	
	@Autowired
	private SearchRepository searchRepository;
	
	public List<Post> postList(){
		return searchRepository.findAll();
	};
	
	public List<Post> postSearchList(String searchKeyword){
		return searchRepository.findByTitleContaining(searchKeyword);
	}

}
