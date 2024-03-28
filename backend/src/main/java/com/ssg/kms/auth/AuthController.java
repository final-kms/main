package com.ssg.kms.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssg.kms.security.JwtFilter;
import com.ssg.kms.security.TokenDto;
import com.ssg.kms.user.LoginDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {


    	String jwt = authService.authorize(loginDto); //test2
    	HttpHeaders httpHeaders = new HttpHeaders();
    	// response header에 jwt token에 넣어줌
    	httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, jwt);

    	
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.OK);
    }
}
