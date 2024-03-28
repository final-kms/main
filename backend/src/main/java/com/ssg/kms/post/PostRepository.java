package com.ssg.kms.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findAll();

	List<Post> findAllByTitleContaining(String searchKeyword);

    @Query("SELECT title FROM Post")
    List<String> findAllTitles();

//    @Query("SELECT title FROM Post where replace(con_title,\" \",\"\")")
//    List<String> findAllByTitleContaining();

    
    
    
}
