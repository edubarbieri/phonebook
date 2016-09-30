package br.com.edubarbieri.app.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ADDRESS_PHONE")
public class AddressPhone implements Serializable{
	private static final long serialVersionUID = 2715295416020890216L;
	@Id @GeneratedValue	@Column(name="ADDRESS_PHONE_ID")
	private Integer id;
	@ManyToOne
	@JoinColumn(name="ADDRESS_ID", referencedColumnName="ADDRESS_ID")
	private Address address;
	
	@Column(name="NAME", length=40)
	private String name;
	
	@Column(name="FUNCTION", length=40)
	private String function;
	
	@Column(name="PHONE_ONE", length=15)
	private String phoneOne;	
	
	@Column(name="PHONE_TWO", length=15)
	private String phoneTwo;	
	
	@Column(name="EMAIL", length=30)
	private String email;
	

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getPhoneOne() {
		return phoneOne;
	}
	public void setPhoneOne(String phoneOne) {
		this.phoneOne = phoneOne;
	}
	public String getPhoneTwo() {
		return phoneTwo;
	}
	public void setPhoneTwo(String phoneTwo) {
		this.phoneTwo = phoneTwo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
		AddressPhone other = (AddressPhone) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "AddressPhone [id=" + id + ", address=" + address + ", name="
				+ name + ", function=" + function + ", phoneOne=" + phoneOne
				+ ", phoneTwo=" + phoneTwo + ", email=" + email + "]";
	}
	
	
	
	
}
