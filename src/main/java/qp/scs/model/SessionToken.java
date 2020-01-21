package qp.scs.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SessionToken extends qp.scs.model.Entity {

	

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	public Long id;

	@Column(nullable = false)
	public String token;
	
	@ManyToOne
	@JoinColumn(name = "session_user", referencedColumnName = "id")
	public User sessionUser;
	
	@Column(nullable = false)
	private LocalDateTime expires;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getSessionUser() {
		return sessionUser;
	}

	public void setSessionUser(User sessionUser) {
		this.sessionUser = sessionUser;
	}

	public LocalDateTime getExpires() {
		return expires;
	}

	public void setExpires(LocalDateTime expires) {
		this.expires = expires;
	}
	
//	public SessionToken(Long id, String username) {
//		this.id=id;
//		this.username=username;
//	}
	public SessionToken() {
		
	}

}
