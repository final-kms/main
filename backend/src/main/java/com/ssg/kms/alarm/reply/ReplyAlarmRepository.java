package com.ssg.kms.alarm.reply;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssg.kms.alarm.post.PostAlarm;

public interface ReplyAlarmRepository extends JpaRepository<ReplyAlarm, Long> {
	
	@Query(value = "DELETE ra FROM reply_alarm ra JOIN reply r ON ra.reply_id = r.id WHERE r.post_id IN :postIds AND ra.user_id = :userId", nativeQuery = true)
	void deleteByPostIdInAndUserId(List<Long> postIds, Long userId);

	List<ReplyAlarm> findAllByUserId(Long id);

//	void deleteByPostInAndUser(List<Post> posts, User user);

}
