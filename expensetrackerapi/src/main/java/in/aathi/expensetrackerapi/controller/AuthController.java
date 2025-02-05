package in.aathi.expensetrackerapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.aathi.expensetrackerapi.entity.AuthModel;
import in.aathi.expensetrackerapi.entity.JwtResponse;
import in.aathi.expensetrackerapi.entity.User;
import in.aathi.expensetrackerapi.entity.UserModel;
import in.aathi.expensetrackerapi.security.CustomUserDetailsService;
import in.aathi.expensetrackerapi.service.UserService;
import in.aathi.expensetrackerapi.util.jwtTokenUtil;
import jakarta.validation.Valid;

@RestController
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomUserDetailsService userDetailService;
	
	@Autowired
	private jwtTokenUtil jwtTokenUtil;
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody AuthModel authModel) throws Exception {
		
		authenticate(authModel.getEmail(), authModel.getPassword());
		final UserDetails userDetails = userDetailService.loadUserByUsername(authModel.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);
		
		//SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseEntity<JwtResponse>(new JwtResponse(token),HttpStatus.OK);
		
	}
	
	private void authenticate(String email, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
		} catch (DisabledException e) {
			throw new Exception("User disabled");
		} catch (BadCredentialsException e) {
			throw new Exception("Bad Credentials");
		}
		
	}

	@PostMapping("/register")
	public ResponseEntity<User> save(@Valid @RequestBody UserModel user){
		return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED);
	}
}
