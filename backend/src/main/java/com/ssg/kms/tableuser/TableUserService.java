package com.ssg.kms.tableuser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.chatroomuser.ChatRoomUser;
import com.ssg.kms.mapping.GetTableMapping;
import com.ssg.kms.mapping.GetUserMapping;
import com.ssg.kms.table.TableRepository;
import com.ssg.kms.table.Tables;
import com.ssg.kms.user.User;
import com.ssg.kms.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TableUserService {
    private final TableUserRepository tableUserRepository;
    private final TableRepository tableRepository;    
    private final UserRepository userRepository;
    
    @Transactional
    public Boolean createTableUser(Long tableId, TableUserDTO tableUserDto, Optional<User> user) {
 
   
    	Tables table = tableRepository.findById(tableId).get();
    	    	
    	List<TableUser> tableUsers = new ArrayList<>();

    	if(!tableUserRepository.findByUserIdAndTableId(user.get().getId(), tableId).getIsAdmin()) {
    		return false;
    	}
    	
    	for(String email : tableUserDto.getEmail()) {
    		
    		User foundUser = userRepository.findByEmail(email);

    		if(foundUser != null && tableUserRepository.findByTableIdAndUserId(tableId, foundUser.getId()).isEmpty()) {
    			TableUser tableUser = TableUser.builder()
    	    			.user(foundUser)
    	    			.table(table)
    	    			.accept(false)
    	    			.isAdmin(false)
    	    			.build();
	    		
    			tableUsers.add(tableUser);
    		}
    	}
    	tableUserRepository.saveAll(tableUsers);

    	return true;
    }
    
    @Transactional(readOnly = true)
    public TableUser readTableUser(Long tableUserId, Optional<User> user) {
    	return tableUserRepository.findById(tableUserId).get();
    }
    
    @Transactional(readOnly = true)
    public List<GetTableMapping> readAllTableUser(Optional<User> user) {
    	
    	return tableUserRepository.findAllByUserId(user.get().getId());
    	
    }
    
    @Transactional(readOnly = true)
    public List<TableUser> readAllInvite(Optional<User> user) {
    	
    	return tableUserRepository.findByUserIdAndAcceptFalse(user.get().getId());
    	
    }

    
    // 모든 테이블 유저
    // readTableUsersByTable
    @Transactional(readOnly = true)
    public List<GetUserMapping> readTableUsersByTable(Long tableId, Optional<User> user) {
    	
    	return tableUserRepository.findAllUsersByTableId(tableId);
    	
    }
    
    // 승인
    @Transactional
    public TableUser updateTableUser(Long tableUserId, Optional<User> user) {
    	
    	TableUser tableUser = tableUserRepository.findById(tableUserId).get();
    	
    	tableUser.setAccept(true);
    	
		return tableUserRepository.save(tableUser);
    }

    // 거절
    @Transactional
    public Boolean deleteTableUser(Long tableUserId, Optional<User> user) {
    	tableUserRepository.deleteById(tableUserId);    	    	
    	return true;
    }
    
    // 권한 추가
    @Transactional
    public Boolean addAdmin(Long tableId, EmailDTO emailDto, Optional<User> user) {
    	TableUser tableUser = tableUserRepository.findTop1ByTableIdOrderByIdAsc(tableId);
    	if(tableUser.getUser().getId() == user.get().getId()) {
    		TableUser admin = tableUserRepository.findByUserEmail(emailDto.getEmail());
    		admin.setIsAdmin(true);
    		tableUserRepository.save(admin);
    		return true;
    	} else {
    		return false;
    	}    	
    }
    
    // 권한 제거
    @Transactional
    public Boolean removeAdmin(Long tableId, EmailDTO emailDto, Optional<User> user) {
    	TableUser tableUser = tableUserRepository.findTop1ByTableIdOrderByIdAsc(tableId);
    	if(tableUser.getUser().getId() == user.get().getId()) {
    		TableUser admin = tableUserRepository.findByUserEmail(emailDto.getEmail());
    		admin.setIsAdmin(false);
    		tableUserRepository.save(admin);
    		return true;
    	} else {
    		return false;
    	}    	
    }
    
}
