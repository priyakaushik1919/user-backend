package com.learning.service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.learning.Response.LoginResponse;
import com.learning.dto.LoginDto;
import com.learning.dto.UserDto;
import com.learning.entity.User;
import com.learning.repo.UserRepository;
import com.learning.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	

	@Override
	public String addUser(UserDto userDto) {
		User user = new User(
				userDto.getId(), 
				userDto.getUsername(),
				userDto.getEmail(),
				this.passwordEncoder.encode(userDto.getPassword()));
		
		userRepository.save(user);
		
		return user.getUsername();
	}




    @Override
    public LoginResponse loginUser(LoginDto loginDTO) {
        String msg = "";
        User user1 = userRepository.findByEmail(loginDTO.getEmail());
        if (user1 != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = user1.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<User> user = userRepository.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
                if (user.isPresent()) {
                    return new LoginResponse("Login Success", true);
                } else {
                    return new LoginResponse("Login Failed", false);
                }
            } else {
                return new LoginResponse("password Not Match", false);
            }
        }else {
            return new LoginResponse("Email not exits", false);
        }
    }

}
