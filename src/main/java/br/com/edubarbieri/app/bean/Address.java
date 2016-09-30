package br.com.edubarbieri.app.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
@Entity
@Table(name="ADDRESS")
public class Address implements Serializable{
	private static final long serialVersionUID = -492842211944451395L;
	@Id @GeneratedValue @Column(name="ADDRESS_ID")
	private Integer id;
	
	@Column(name="NAME", length=40, nullable=false)
	private String name;
	
	@Enumerated(EnumType.STRING) @Column(name="PERSON_TYPE", length=10)
	private PersonType personType;
	
	@Column(name="ADDRESS", length=70)
	private String address;
	
	@Column(name="CITY", length=30)
	private String city;
	
	@Column(name="UF", length=2)
	private String uf;
	
	@Column(name="CEP", length=10)
	private String cep;	
	
	@Column(name="SITE", length=30)
	private String site;	
	
	@Column(name="PHONE_ONE", length=15)
	private String phoneOne;	
	
	@Column(name="PHONE_TWO", length=15)
	private String phoneTwo;	
	
	@Lob
	@Column(name="OBSERVATION")
	private String observation;
	
	@OneToMany(mappedBy="address", cascade=CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval=true)
	@Fetch(FetchMode.SELECT)
	private List<AddressPhone> addressPhones;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public PersonType getPersonType() {
		return personType;
	}
	public void setPersonType(PersonType personType) {
		this.personType = personType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
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
	public String getObservation() {
		return observation;
	}
	public void setObservation(String observation) {
		this.observation = observation;
	}
	public List<AddressPhone> getAddressPhones() {
		return addressPhones;
	}
	public void setAddressPhones(List<AddressPhone> addressPhones) {
		this.addressPhones = addressPhones;
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
		Address other = (Address) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Address [id=" + id + ", name=" + name + ", personType="
				+ personType + ", address=" + address + ", city=" + city
				+ ", cep=" + cep + ", addressPhones=" + addressPhones
				+ ", observation=" + observation + "]";
	}
	
	
}
