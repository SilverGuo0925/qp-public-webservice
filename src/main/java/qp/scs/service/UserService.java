package qp.scs.service;

import org.springframework.stereotype.Service;

import qp.scs.dto.request.LoginRequestDTO;
import qp.scs.dto.response.LoginResponseDTO;
import qp.scs.model.User;
import qp.scs.security.TokenUser;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.joda.time.Period; 

import java.util.concurrent.atomic.AtomicLong;

import qp.scs.security.TokenHandler;

@Service
@Transactional
public class UserService extends BaseService {

	private static Integer expireTokenHrs;
	
	private static Integer expireTokenMins;
	
	private final AtomicLong counter = new AtomicLong();

	@Autowired
	private TokenHandler tokenHandler;
	
	@Value("${token.expiry.hours}")
	public void setExpireTokenHrs(Integer expireTokenHrs) {
		UserService.expireTokenHrs = expireTokenHrs;
	}
	
	@Value("${token.expiry.minutes}")
	public void setExpireTokenMins(Integer expireTokenMins) {
		UserService.expireTokenMins = expireTokenMins;
	}
	public LoginResponseDTO logUserIn(LoginRequestDTO request) {
		logger.info("Attempting login for user[login: " +  request.username + "]");
		
//		User u = getUserByUserName(request.username);
//
//		// Verify if the username is valid
//		if (u == null) {
//			logger.info("Login failed unable to find user with login of \'" +  request.username + "\'");
//			throwLoginFailedException();
//		}
//		
//		if(u != null){
//			Customer customer = u.getCustomerUser();
//			if(customer.getStatus().equals(Codes.Mappings.simpleStatuses.get(Codes.Statuses.SIMPLE_INACTIVE))){
//				throwCustomerInactiveException();
//			}
//		}
//		// Verify if the password is valid
//		boolean validPassword = password.checkPassword(request.password, u.getPassword());
//		if (validPassword == false) {
//			logger.info("Login failed password attempted of \'" +  request.password + "\' does not match with DB password");
//			throwLoginFailedException();
//		}

		User u = new User(counter.incrementAndGet(),request.username);
		
		return logUserIn(u);
	}

	/**
	 * Logs the specified user in
	 * 
	 * @param user
	 * @return
	 */
	public LoginResponseDTO logUserIn(User user) {
		// Generate the token based on the user
		Period period = new Period(expireTokenHrs, expireTokenMins, 0, 0);
		TokenUser tokenUser = new TokenUser(user, period);
		String token = tokenHandler.createTokenForUser(tokenUser);

		//saveSessionToken(tokenUser, token);

		logger.info("Login successful for user[name: " + user.getUsername());
		
		return new LoginResponseDTO(token,false);
	}
}
