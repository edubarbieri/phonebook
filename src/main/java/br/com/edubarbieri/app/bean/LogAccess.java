package br.com.edubarbieri.app.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="LOG_ACCESS")
public class LogAccess implements Serializable{
	private static final long serialVersionUID = -2092971729396957015L;
	
	@Id @GeneratedValue	@Column(name="LOG_ACCESS_ID")
	private Integer id;
	@ManyToOne
	@JoinColumn(name="USER_ID", referencedColumnName="USER_ID")
	private User user;
	@Temporal(TemporalType.TIMESTAMP) @Column(name="DWH_ACCESS")
	private Date dwhAccess;
	@Column(name="IP_ADDRESS", length=30)
	private String ipAddress;
	public LogAccess() {
	}
	
	
	public LogAccess(User user, String ipAddress) {
		super();
		this.user = user;
		this.ipAddress = ipAddress;
		this.dwhAccess = new Date();
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getDwhAccess() {
		return dwhAccess;
	}
	public void setDwhAccess(Date dwhAccess) {
		this.dwhAccess = dwhAccess;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
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
		LogAccess other = (LogAccess) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "LogAccess [id=" + id + ", user=" + user + ", dwhAccess="
				+ dwhAccess + ", ipAddress=" + ipAddress + "]";
	}
	
	
	
}
