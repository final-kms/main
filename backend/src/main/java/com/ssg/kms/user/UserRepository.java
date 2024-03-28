package com.ssg.kms.user;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findOneWithAuthoritiesByUsername(String username);

	Optional<User> findOneWithAuthoritiesByEmail(String email);

	Set<User> findAllByEmailIn(Set<String> userEmail);

	Optional<User> findByUsername(String username);

	User findByEmail(String email);

	@Query(value = "SELECT u.email FROM user u WHERE u.email LIKE %:searchKeyword% AND u.id != :myId", nativeQuery = true)
	List<String> findAllByEmailContaining(String searchKeyword, Long myId);

	List<User> findAllByIdIn(List<Long> tableUserIds);

}
