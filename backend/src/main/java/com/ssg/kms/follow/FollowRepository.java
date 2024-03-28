package com.ssg.kms.follow;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssg.kms.post.Post;
import com.ssg.kms.user.User;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	Optional<Follow> findByFollowerAndFollowee(User user, User foundUser);

	List<Follow> findAllByFollowerEmail(String email);

	List<Follow> findAllByFolloweeEmail(String email);

	void deleteAllByFollowerId(Long id);
	
	void deleteAllByFolloweeId(Long id);

	List<Follow> findAllByFolloweeId(Long id);

	List<Follow> findAllByFollowerId(Long id);

}
