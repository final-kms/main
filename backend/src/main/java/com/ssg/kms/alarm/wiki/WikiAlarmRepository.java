package com.ssg.kms.alarm.wiki;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssg.kms.alarm.post.PostAlarm;

public interface WikiAlarmRepository extends JpaRepository<WikiAlarm, Long> {
	
	@Query(value = "DELETE wa FROM wiki_alarm wa WHERE wa.wiki_id IN :wikiIds AND wa.user_id = :userId", nativeQuery = true)
	void deleteByWikiIdInAndUserId(List<Long> wikiIds, Long userId);

	List<WikiAlarm> findAllByUserId(Long id);

//	void deleteByPostInAndUser(List<Post> posts, User user);

}
