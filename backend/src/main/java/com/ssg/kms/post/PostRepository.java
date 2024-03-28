package com.ssg.kms.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findAll();

	List<Post> findAllByTitleContaining(String searchKeyword);

	void deleteAllByTableId(Long tableId);

	List<Post> findAllByUserId(Long id);

	List<Post> findAllByTableIsPublicTrue();

	List<Post> findAllByTableId(Long tableId);

	List<Post> findAllByTableIdIn(List<Long> tableIds);
	
	
	// 알람
	@Query(value = "SELECT p.id FROM post p WHERE p.table_id = :tableId", nativeQuery = true)
	List<Long> findPostIdAllByTableId(Long tableId);

	@Query(value = "SELECT p.id FROM post p WHERE p.user_id = :userId", nativeQuery = true)	
	List<Long> findIdAllByUserId(Long userId);
}
