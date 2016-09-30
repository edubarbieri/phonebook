package br.com.edubarbieri.app.mbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import br.com.edubarbieri.app.bean.LogAccess;
import br.com.edubarbieri.app.bean.User;
import br.com.edubarbieri.app.dao.GenericDAO;
import br.com.edubarbieri.app.dao.UserDAO;
import br.com.edubarbieri.app.util.JSFUtil;

@ManagedBean
@RequestScoped
public class LoginMBean {
	private static final Logger LOG = Logger.getLogger(LoginMBean.class);
	private User userToLogin;
	private UserDAO userDAO;

	public String login() {
		LOG.debug("Call login");
		String res = "";
		try {
			User user = getUserDAO().get(userToLogin.getUsername());
			if (user != null && DigestUtils.sha256Hex(userToLogin.getPassword()).equals(user.getPassword())) {
				user.setPassword("dummy");
				JSFUtil.setLoggedUser(user);
				saveLogAccess(user);
				res = "/default?faces-redirect=true";
			} else {
				JSFUtil.addErrorMessage("Usuário e(ou) senha inválido(s)!", null);
				res = "/login";
			}
		} catch (Exception e) {
			LOG.error("public String validateLogin() - Error", e);
			JSFUtil.addErrorMessage("Erro ao executar a operação!", e.getMessage());
		}
		return res;
	}

	private void saveLogAccess(User user) {
		try {
			String ipAddress = JSFUtil.getRequest().getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				ipAddress = JSFUtil.getRequest().getRemoteAddr();
			}
			LogAccess log = new LogAccess(user,ipAddress);
			new GenericDAO<LogAccess>(LogAccess.class).saveOrUpdate(log);
		} catch (Exception e) {
			LOG.error("Erro on saveLogAccess: " + e);
		}
	}

	public String logout() {
		LOG.debug("Call login");
		JSFUtil.invalidateSession();
		return "/login";
	}

	private UserDAO getUserDAO() {
		if (this.userDAO == null) {
			userDAO = new UserDAO();
		}
		return this.userDAO;
	}

	public User getUserToLogin() {
		if (userToLogin == null) {
			userToLogin = new User();
		}
		return userToLogin;
	}

	public void setUserToLogin(User userToLogin) {
		this.userToLogin = userToLogin;
	}

}
