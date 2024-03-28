package com.ssg.kms.tableuser;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssg.kms.mapping.GetTableMapping;
import com.ssg.kms.mapping.GetUserMapping;
import com.ssg.kms.user.User;

public interface TableUserRepository extends JpaRepository<TableUser, Long> {

	
	List<GetTableMapping> findAllByUserId(Long userId);

	void deleteAllByUserId(Long id);

	List<GetUserMapping> findAllUsersByTableId(Long tableId);

	Optional<User> findByUserId(Long id);

	List<TableUser> findByTableIdAndUserId(Long tableId, Long UserId);
	
	List<TableUser> findByUserIdAndAcceptFalse(Long userId);

	TableUser findByTableId(Long tableId);

	TableUser findByUserEmail(String email);

	TableUser findTop1ByTableIdOrderByIdAsc(Long tableId);

	TableUser findByUserIdAndTableId(Long id, Long tableId);
	
	@Query(value = "SELECT tu.table_id FROM table_user tu WHERE tu.user_id = :userId", nativeQuery = true)
	List<Long> findTableIdAllByUserId(Long userId);
	
	// 알람
	@Query(value = "SELECT tu.user_id FROM table_user tu WHERE tu.table_id = :tableId", nativeQuery = true)
	List<Long> findAllUserAllByTableId(Long tableId);


	
}