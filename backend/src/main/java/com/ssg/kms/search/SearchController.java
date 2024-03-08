package com.ssg.kms.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssg.kms.post.Post;

@RestController
@RequestMapping("/api/search")
public class SearchController {

	@Autowired
	SearchService searchService;
	
	@GetMapping("/post")
    public ResponseEntity<List<Post>> searchPage(String searchKeyword) {
		
		List<Post> list = null;
		
		if(searchKeyword == null) {
			list = searchService.postList();
		}else {
			list = searchService.postSearchList(searchKeyword);
		}
		
        return ResponseEntity.ok(list);
    }
	
}
