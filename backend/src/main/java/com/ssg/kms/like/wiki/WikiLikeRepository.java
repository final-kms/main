package com.ssg.kms.like.wiki;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssg.kms.user.User;
import com.ssg.kms.wiki.Wiki;

public interface WikiLikeRepository extends JpaRepository<WikiLike, Long> {

	Optional<WikiLike> findByWikiAndUser(Wiki wiki, User user);

	Set<WikiLike> findAllByWikiId(Long wikiId);

}