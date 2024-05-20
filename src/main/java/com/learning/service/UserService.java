package com.learning.service;

import com.learning.Response.LoginResponse;
import com.learning.dto.LoginDto;
import com.learning.dto.UserDto;

public interface UserService {

	String addUser(UserDto userDto);

	LoginResponse loginUser(LoginDto loginDTO);

}
