package com.ssg.kms.tag;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

	Optional<Tag> findByName(String tagName);

}
