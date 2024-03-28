package com.ssg.kms.log.search;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ssg.kms.user.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchLogService {
	
	private final SearchLogRepository searchLogRepository;
	
	
	public SearchLog readSearchLog(Optional<User> user) {
		// userID 찾을 수 있게 만들기
		return searchLogRepository.findById(user.get().getId()).get();
	}

}
