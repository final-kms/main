package com.ssg.kms.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByRole(ERole roleUser);

}
