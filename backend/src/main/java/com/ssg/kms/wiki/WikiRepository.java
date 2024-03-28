package com.ssg.kms.wiki;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssg.kms.post.Post;

public interface WikiRepository extends JpaRepository<Wiki, Long> {

	void deleteAllByTableId(Long tableId);

	List<Wiki> findAllByCategoryName(String categoryName);

	List<Wiki> findAllByUserId(Long id);

	List<Wiki> findAllByTableIsPublicTrue();

	List<Wiki> findAllByTableId(Long tableId);

	List<Wiki> findAllByTableIdIn(List<Long> tableIds);

	// 알람
	@Query(value = "SELECT w.id FROM wiki w WHERE w.table_id = :tableId", nativeQuery = true)
	List<Long> findWikiIdAllByTableId(Long tableId);

	List<Wiki> findAllByTitleContaining(String searchKeyword);

}
