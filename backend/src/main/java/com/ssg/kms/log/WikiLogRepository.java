package com.ssg.kms.log;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WikiLogRepository extends JpaRepository<WikiLog, Long> {

	Set<WikiLog> findAllByWikiId(Long wikiId);

	void deleteAllByWikiId(Long wikiId);

}
