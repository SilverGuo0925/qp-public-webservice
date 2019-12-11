package qp.scs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import qp.scs.dto.request.LoginRequestDTO;
import qp.scs.dto.response.LoginResponseDTO;

import qp.scs.service.UserService;

@RestController
@RequestMapping(path = "/api")
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(path = "/login", method= RequestMethod.POST)
	  public LoginResponseDTO login(@Validated @RequestBody LoginRequestDTO request) {
	   return userService.logUserIn(request);
	  }
	

}
