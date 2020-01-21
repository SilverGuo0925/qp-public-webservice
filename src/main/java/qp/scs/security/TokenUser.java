package qp.scs.security;

import org.joda.time.DateTime;
import org.joda.time.ReadablePeriod;
import qp.scs.model.User;


public class TokenUser {

	public Long id;
	
	public String username;
	
	public Long expires;
	
	public TokenUser() {
		
	}
	
	public TokenUser(User user, ReadablePeriod period) {
	
		id=user.getId();
		username=user.getUsername();
		expires= new DateTime().plus(period).toDate().getTime();
	}
	public TokenUser(User user, DateTime ref, ReadablePeriod period) {
		id = user.getId();
		username = user.getUsername();
		expires = ref.plus(period).toDate().getTime();
	}
}
