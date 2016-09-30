package br.com.edubarbieri.app.util;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.edubarbieri.app.bean.User;

public class JSFUtil implements Serializable{
	private static final long serialVersionUID = -9119215801036824404L;

	public static void addInfoMessage(String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
	}

	public static void addWarnMessage(String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail));
	}

	public static void addErrorMessage(String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
	}

	// public static void addErrorMessage(ServiceException se) {
	// FacesContext.getCurrentInstance().addMessage(null,
	// new FacesMessage(FacesMessage.SEVERITY_ERROR, se.getSummary(),
	// se.getDetail()));
	// }
	//
	// public static void addFataMessage(String summary, String detail) {
	// FacesContext.getCurrentInstance().addMessage(null,
	// new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, detail));
	// }
	//
	// public static void addFataMessage(ServiceException se) {
	// FacesContext.getCurrentInstance().addMessage(null,
	// new FacesMessage(FacesMessage.SEVERITY_FATAL, se.getSummary(),
	// se.getDetail()));
	// }
	public static User getLoggedUser() {
		return (User) getSessionAtributte(Constantes.LOGGED_USER_KEY);
	}

	public static void setLoggedUser(User user) {
		setSessionAtributte(Constantes.LOGGED_USER_KEY, user);
	}

	public static void invalidateSession() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		request.getSession().invalidate();
	}

	public static Object getSessionAtributte(String name) {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return request.getSession(true).getAttribute(name);
	}

	public static void setSessionAtributte(String name, Object value) {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		request.getSession(true).setAttribute(name, value);
	}

	public static <T> T findBean(String beanName) {
		FacesContext context = FacesContext.getCurrentInstance();
		return (T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
	}

	public static HttpServletRequest getRequest() {
		Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request instanceof HttpServletRequest) {
			return (HttpServletRequest) request;
		} else {
			return null;
		}
	}
}
