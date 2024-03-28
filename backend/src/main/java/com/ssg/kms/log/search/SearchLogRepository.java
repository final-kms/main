package com.ssg.kms.log.search;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssg.kms.user.User;

public interface SearchLogRepository extends JpaRepository<SearchLog, Long>{

//	Optional<User> findByUserId(Long userId);

}
