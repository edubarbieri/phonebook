package br.com.edubarbieri.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER_APP")
public class User implements Serializable{
	private static final long serialVersionUID = -5667702378385634300L;
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	@Id	@GeneratedValue	@Column(name="USER_ID")
	private Integer id;
	@Column(name="USERNAME", nullable=false, unique=true, length=60)
	private String username;
	@Column(name="PASSWORD", nullable=false, length=65)
	private String password;
	@Column(name="NAME", nullable=false, length=60)
	private String name;
	@Column(name="EMAIL", nullable=false, length=100)
	private String email;	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFirstName(){
		if(name == null || "".equals(name)) return username;
			
		return name.substring(0, name.indexOf(" "));
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", name=" + name + ", email=" + email + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
