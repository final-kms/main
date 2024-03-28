package com.ssg.kms.star.wiki;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssg.kms.mapping.GetWikiMapping;
import com.ssg.kms.user.User;
import com.ssg.kms.wiki.Wiki;

public interface WikiStarRepository extends JpaRepository<WikiStar, Long> {

	Optional<WikiStar> findByWikiAndUser(Wiki wiki, User user);

	Set<WikiStar> findAllByWikiId(Long wikiId);

	void deleteAllByWikiId(Long id);

	void deleteAllByUserId(Long id);

	List<GetWikiMapping> findWikiAllByUserId(Long id);

}
