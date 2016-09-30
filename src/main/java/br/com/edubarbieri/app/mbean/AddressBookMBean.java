package br.com.edubarbieri.app.mbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import br.com.edubarbieri.app.bean.Address;
import br.com.edubarbieri.app.bean.AddressPhone;
import br.com.edubarbieri.app.bean.PersonType;
import br.com.edubarbieri.app.dao.AddressBookDAO;
import br.com.edubarbieri.app.util.JSFUtil;
@ManagedBean
@ViewScoped
public class AddressBookMBean implements Serializable{
	private static final long serialVersionUID = 2589907188256392896L;
	private static final Logger LOG = Logger.getLogger(AddressBookMBean.class);
	private Address addressEdit;
	private AddressBookDAO addressBookDAO;
	@ManagedProperty("#{msg}")
	private ResourceBundle msg;
	private String titlePopup = "";
	
	
	

	public AddressBookDAO getAddressBookDAO() {
		if(addressBookDAO == null){
			addressBookDAO =new AddressBookDAO();
		}
		return addressBookDAO;
	}
	
	public PersonType[] getPersonTypes(){
		return PersonType.values();
	}

	
	public void prepareCreate(){
		LOG.debug("Call prepareCreate");
		titlePopup = msg.getString("novo");
		addressEdit = new Address();
	}
	
	public void addAddressPhone(){
		LOG.debug("Call addAddressPhone");
		if(addressEdit.getAddressPhones() == null){
			addressEdit.setAddressPhones(new ArrayList<AddressPhone>());
		}
		AddressPhone addressPhone = new AddressPhone(); 
		addressPhone.setAddress(addressEdit);
		addressEdit.getAddressPhones().add(addressPhone);	
		RequestContext.getCurrentInstance().addCallbackParam("position", addressEdit.getAddressPhones().size() -1);
	}
	
	public void removeAddressPhone(AddressPhone addressPhoneToRemove){
		LOG.debug("Call removeAddressPhone");
		addressEdit.getAddressPhones().remove(addressPhoneToRemove);
	}
	
	
	public void save(){
		LOG.debug("Call save");
		try {
			getAddressBookDAO().saveOrUpdate(addressEdit);
			JSFUtil.addInfoMessage(msg.getString("sucess"), msg.getString("addressSaveSucess"));
		} catch (Exception e) {
			LOG.error("Erro on save: " + e);
			JSFUtil.addErrorMessage(msg.getString("error"), msg.getString("addressSaveErro") + e.toString());
			RequestContext.getCurrentInstance().addCallbackParam("error", true);
		}
	}
	public void deleteAddress(Integer addressId){
		LOG.debug("Call deleteAddress");
		try {
			getAddressBookDAO().delete(addressId);
			JSFUtil.addInfoMessage(msg.getString("sucess"), "Contato excluido com sucesso!");
		} catch (Exception e) {
			LOG.error("Erro on save: " + e);
			JSFUtil.addErrorMessage(msg.getString("error"), "Erro ao remover registro!");
		}
	}
	
	public void clearFiltro(){
		LOG.debug("Call clearFiltro");
		getAddressBookDAO().setFilter("");
	}
	

	public Address getAddressEdit() {
		if(addressEdit == null){
			addressEdit = new Address();
		}
		return addressEdit;
	}
	
	
	public void prepareEdit(Address addressToEdit){
		LOG.debug("Call prepareEdit");
		titlePopup = msg.getString("editar");
		addressEdit = addressToEdit;
	}


	public void setAddressEdit(Address addressEdit) {
		this.addressEdit = addressEdit;
	}

	public ResourceBundle getMsg() {
		return msg;
	}

	public void setMsg(ResourceBundle msg) {
		this.msg = msg;
	}

	public String getTitlePopup() {
		return titlePopup;
	}

	public void setTitlePopup(String titlePopup) {
		this.titlePopup = titlePopup;
	}
	
	
}
