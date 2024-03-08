package com.ssg.kms.star.wiki;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssg.kms.user.User;
import com.ssg.kms.wiki.Wiki;

public interface WikiStarRepository extends JpaRepository<WikiStar, Long> {

	Optional<WikiStar> findByWikiAndUser(Wiki wiki, User user);

	Set<WikiStar> findAllByWikiId(Long wikiId);

	void deleteAllByWikiId(Long id);

}
