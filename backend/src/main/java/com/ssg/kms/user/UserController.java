package com.ssg.kms.user;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssg.kms.auth.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
//@CrossOrigin(origins="*")
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
	private final UserService userService;
	private final AuthService authService;
	
	@PostMapping("/signup")
	public ResponseEntity<User> signup(@Valid @RequestBody UserDto userDto) {
		return ResponseEntity.ok(userService.signup(userDto));
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto) {
		
		String jwtToken = authService.authorize(loginDto);
        // 서버에서 쿠키 설정
//        Cookie cookie = new Cookie("jwtToken", jwtToken.substring(7));
//        cookie.setMaxAge(60 * 60 * 24); // 쿠키의 유효기간 설정
//        cookie.setPath("/");
//        response.addCookie(cookie);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", "jwtToken=" + jwtToken.substring(7) + "; Path=/; Max-Age=3600"); //3600초

		return ResponseEntity.ok().headers(headers).body(loginDto.getUsername());
	}


	@PostMapping("/testtest")
	public ResponseEntity<User> test(@Valid @RequestBody UserDto userDto) {
		System.out.println("test");
		return ResponseEntity.ok(userService.test(userDto));
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	public ResponseEntity<User> getMyUserInfo() {
		return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
	}

	@GetMapping("/user/{username}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public ResponseEntity<User> getUserInfo(@PathVariable String username) {
		return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
	}
}
