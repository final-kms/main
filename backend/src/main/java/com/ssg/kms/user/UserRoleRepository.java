package com.ssg.kms.user;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

//	UserRole findByUser(User user);

	List<UserRole> findAllByUser(User user);

	void deleteAllByUserId(Long id);

}
