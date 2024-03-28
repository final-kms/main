package com.ssg.kms.like.post;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssg.kms.mapping.GetPostMapping;
import com.ssg.kms.post.Post;
import com.ssg.kms.user.User;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

	Optional<PostLike> findByPostAndUser(Post post, User user);

	Set<PostLike> findAllByPostId(Long postId);

	void deleteAllByPostId(Long id);

	void deleteAllByUserId(Long id);

	List<GetPostMapping> findPostAllByUserId(Long id);

}
