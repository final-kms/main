package com.ssg.kms.user;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findOneWithAuthoritiesByUsername(String username);

	Optional<User> findOneWithAuthoritiesByEmail(String email);

	Set<User> findAllByEmailIn(Set<String> userEmail);

	Optional<User> findByUsername(String username);

}
