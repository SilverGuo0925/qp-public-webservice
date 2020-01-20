package qp.scs.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	public Long id;

	public String username;
	
	public String password;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User(Long id, String username) {
		this.id=id;
		this.username=username;
	}
	public User() {
		
	}

}
