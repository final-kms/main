package com.ssg.kms.alarm.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostAlarmRepository extends JpaRepository<PostAlarm, Long> {
	
	@Query(value = "DELETE pa FROM post_alarm pa WHERE pa.post_id IN :postIds AND pa.user_id = :userId", nativeQuery = true)
	void deleteByPostIdInAndUserId(List<Long> postIds, Long userId);

	List<PostAlarm> findAllByUserId(Long id);

//	void deleteByPostInAndUser(List<Post> posts, User user);

}
