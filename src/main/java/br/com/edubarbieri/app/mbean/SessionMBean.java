package br.com.edubarbieri.app.mbean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.edubarbieri.app.bean.User;
import br.com.edubarbieri.app.util.JSFUtil;

@ManagedBean
@SessionScoped
public class SessionMBean implements Serializable{
	private static final long serialVersionUID = 5837240553474332903L;

	public User getUser(){
		return JSFUtil.getLoggedUser();
	}
}
